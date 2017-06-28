package com.getto.friends2.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import com.getto.friends2.App;
import com.getto.friends2.DaoSession;
import com.getto.friends2.R;
import com.getto.friends2.User;
import com.getto.friends2.UserDao;
import com.getto.friends2.details.ActivityDetails;
import com.getto.friends2.userlist.UserListActivity;
import org.greenrobot.greendao.query.Query;

import java.util.List;

/**
 * Created by Getto on 06.07.2016.
 */
public class MainActivity extends AppCompatActivity implements MainView{
    private MainPresenter presenter;
    private FriendsAdapterRec friendsAdapterRec;
    private UserDao userDao;
    private Query<User> userQuery;
    private RecyclerView friendRec;

    @Override
    protected void onPostResume() {
        super.onPostResume();
        presenter.updateList();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        Model model = new Model(this);
        presenter = new MainPresenter(this, model);
        presenter.onCreate();
        presenter.updateList();
    }

    void initViews(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        friendRec = (RecyclerView) findViewById(R.id.list_friends);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(friendRec.getContext(),
                linearLayoutManager.getOrientation());
        friendRec.addItemDecoration(dividerItemDecoration);
        friendRec.setHasFixedSize(true);
        friendRec.setLayoutManager(linearLayoutManager);
        friendsAdapterRec = new FriendsAdapterRec(this, this);
        friendRec.setAdapter(friendsAdapterRec);
        registerForContextMenu(friendRec);
    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int position = -1;
        try {
            position = ((FriendsAdapterRec)friendRec.getAdapter()).getPosition();
        } catch (Exception e) {
            Log.d("ContextMenu", e.getLocalizedMessage(), e);
            return super.onContextItemSelected(item);
        }
        switch (item.getItemId()) {
            case R.id.ctx_item_del:
                // do your stuff
                User user = friendsAdapterRec.getFriend(position);
                presenter.delFriend(user, userDao);
                presenter.updateList();
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onCreateView() {
        DaoSession daoSession = ((App) getApplication()).getDaoSession();
        userDao = daoSession.getUserDao();

        // query all notes, sorted a-z by their text
        userQuery = userDao.queryBuilder().orderAsc(UserDao.Properties.Name).build();
    }


    @Override
    public void updateAdapter() {

        List<User> notes = userQuery.list();
        friendsAdapterRec.setFriend(notes);

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
    public void showInfoFriend(int position) {
        User user = friendsAdapterRec.getFriend(position);
        Intent intent = new Intent(this, ActivityDetails.class);
        intent.putExtra("user", user.getId_user());
        startActivity(intent);
    }
}
