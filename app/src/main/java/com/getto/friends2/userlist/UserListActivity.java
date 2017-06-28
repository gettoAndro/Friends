package com.getto.friends2.userlist;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import com.getto.friends2.App;
import com.getto.friends2.DaoSession;
import com.getto.friends2.R;
import com.getto.friends2.UserDao;
import com.getto.friends2.model_retrofit.Result;
import com.getto.friends2.model_retrofit.Users;

/**
 * Created by Getto on 20.03.2016.
 */
public class UserListActivity extends AppCompatActivity implements UserListView, SwipeRefreshLayout.OnRefreshListener {

    private UserAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private static final int CTM_ADD_ID = 201;
    private UsersPresenter presenter;
    private UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_users);
        initViews();
        presenter = new UsersPresenter(this);
        presenter.onCreate();
        DaoSession daoSession = ((App) getApplication()).getDaoSession();
        userDao = daoSession.getUserDao();
    }

    void initViews(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle("Users");
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setEnabled(false);
        swipeRefreshLayout.setColorSchemeResources(R.color.primary_dark, R.color.primary, R.color.accent);
        ListView listUsers = (ListView) findViewById(R.id.list_users);
        adapter = new UserAdapter(getApplicationContext(), R.layout.item_users);
        listUsers.setAdapter(adapter);
        registerForContextMenu(listUsers);
        listUsers.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if ((firstVisibleItem + visibleItemCount) >= totalItemCount && totalItemCount!=0) {
                    presenter.nextPage();
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(contextMenu, view, menuInfo);
        contextMenu.add(0, CTM_ADD_ID, 0, "Add to friends");
    }

    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == CTM_ADD_ID) {
            AdapterView.AdapterContextMenuInfo adapterContextMenuInfo = (AdapterView.AdapterContextMenuInfo) item
                    .getMenuInfo();
            Result result = adapter.getUser(adapterContextMenuInfo.position);
            result.setFriend(true);
            presenter.addToDataBase(getApplicationContext() ,result, userDao);
            adapter.notifyDataSetChanged();

    }
        return super.onContextItemSelected(item);
    }


    @Override
    public void onRefresh() {
      presenter.onRefresh();
    }


    @Override
    public void addItems(Users users) {
        adapter.setUsers(users);
    }

    @Override
    public void swipeRefresh() {
        swipeRefreshLayout.setRefreshing(true);
    }


    @Override
    public void swipeDismiss() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void ErrorMessageFromConnect() {
        Toast.makeText(getApplicationContext(), "Unable to fetch data from server", Toast.LENGTH_LONG).show();
    }

}




