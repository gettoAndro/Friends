package com.getto.friends2.main;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import com.getto.friends2.R;
import com.getto.friends2.details.ActivityDetails;
import com.getto.friends2.userlist.UserListActivity;

/**
 * Created by Getto on 06.07.2016.
 */
public class MainActivity extends AppCompatActivity implements MainView , AdapterView.OnItemClickListener{
    MainPresenter presenter;
    GridView gridView;
    ImageView imagePlus;
    FriendsAdapter adapter;
    private static final int CTM_Delete_ID = 201;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gridView = (GridView) findViewById(R.id.gridView);
        gridView.setOnItemClickListener(this);
        imagePlus = (ImageView) findViewById(R.id.img_plus);
        Model model = new Model(this);
        presenter = new MainPresenter(this, model);
        presenter.onCreate();
        registerForContextMenu(gridView);
    }
    @Override
    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(contextMenu, view, menuInfo);
        contextMenu.add(0, CTM_Delete_ID, 0, "Delete friend");
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == CTM_Delete_ID) {
            AdapterView.AdapterContextMenuInfo adapterContextMenuInfo = (AdapterView.AdapterContextMenuInfo) item
                    .getMenuInfo();
            presenter.delFriend(adapterContextMenuInfo.id);
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        presenter.launchActivityDetails(parent.getItemIdAtPosition(position));
    }

    @Override
    public void onCreateView(Cursor cursor) {
        adapter = new FriendsAdapter(this, R.layout.item_friends, cursor, 0);
        gridView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    public void updateAdapter() {
        adapter.notifyDataSetChanged();
    }

    public void OnClickFindFriends(View view){
     presenter.OnClickFindFriend();
    }

    @Override
    public void FindFriends() {
        Intent intent = new Intent(this, UserListActivity.class);
        startActivity(intent);
    }



    @Override
    public void showInfoFriend(long item) {
        Intent intent = new Intent(this, ActivityDetails.class);
        intent.putExtra("item", item);
        startActivity(intent);
    }
}
