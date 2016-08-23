package com.getto.friends2.userlist;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import com.getto.friends2.R;
import com.getto.friends2.main.Model;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import java.io.IOException;
import java.util.concurrent.ExecutionException;


/**
 * Created by Getto on 20.03.2016.
 */
public class UserListActivity extends AppCompatActivity implements UserListView, SwipeRefreshLayout.OnRefreshListener {

    UserAdapter adapter;
    SwipeRefreshLayout swipeRefreshLayout;
    private static final int CTM_ADD_ID = 201;
    ProgressDialog dialog;
    UsersPresenter presenter;
    Model model;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_users);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(this);
        listView = (ListView) findViewById(R.id.list);
        model = new Model(this);
        presenter = new UsersPresenter(model, this);
        presenter.onCreate();
        registerForContextMenu(listView);
        adapter = new UserAdapter(UserListActivity.this, R.layout.item_users, model.getUsersList());
        listView.setAdapter(adapter);
    }



    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(contextMenu, view, menuInfo);
        contextMenu.add(0, CTM_ADD_ID, 0, "Add to friends");
    }

    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == CTM_ADD_ID) {
            AdapterView.AdapterContextMenuInfo adapterContextMenuInfo = (AdapterView.AdapterContextMenuInfo) item
                    .getMenuInfo();

            model.Open();
            User user = model.getUsersList().get(adapterContextMenuInfo.position);
            byte[] bytes = null;
            try {
            bytes = new ImageAsyncTask().execute(user.getImage()).get();
            } catch (InterruptedException e){e.printStackTrace();}
            catch (ExecutionException e){e.printStackTrace();}
            if (bytes != null){
                if (!user.isFriends()){
              model.addRec(bytes, user.getName(), user.getEmail(), user.getPhone());
            user.setFriends(true);
            adapter.notifyDataSetChanged();} else Toast.makeText(UserListActivity.this, "Пользователь уже добавлен в друзья", Toast.LENGTH_LONG).show();
            }
         else Toast.makeText(UserListActivity.this, "Не удалось добавить пользователя в друзья, проверьте интернет подключение", Toast.LENGTH_LONG).show();
                model.Close();
        }
        return super.onContextItemSelected(item);
    }


    @Override
    public void onRefresh() {
      presenter.onRefresh();
    }

    @Override
    public void onRefreshSwipe() {
        adapter.notifyDataSetChanged();
    }
    @Override
    public void Swipe() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public boolean isRefreshingSwipe() {
        if (swipeRefreshLayout.isRefreshing()) return true;
       else return false;
    }

    @Override
    public void SwipeComplete() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onShowProgress() {
        dialog = new ProgressDialog(UserListActivity.this);
        dialog.setMessage("Loading, please wait");
        dialog.setTitle("Connecting server");
        dialog.show();
        dialog.setCancelable(false);
    }

    @Override
    public void onCompleteProgress() {
     dialog.dismiss();
    }

    @Override
    public void onRefreshData() {
     adapter.notifyDataSetChanged();
    }

    @Override
    public void ErrorMessageFromConnect() {
        Toast.makeText(getApplicationContext(), "Unable to fetch data from server", Toast.LENGTH_LONG).show();
    }

    class ImageAsyncTask extends AsyncTask<String, Void, byte[]> {
            @Override
            protected byte[] doInBackground(String... params) {
                byte[] dataImage = null;
                try {
                    DefaultHttpClient mHttpClient = new DefaultHttpClient();
                    HttpGet mHttpGet = new HttpGet(params[0]);
                    HttpResponse mHttpResponse = mHttpClient.execute(mHttpGet);
                    if (mHttpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                        HttpEntity entity = mHttpResponse.getEntity();
                        if (entity != null) {
                            dataImage = EntityUtils.toByteArray(entity);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return dataImage;
            }
            @Override
            protected void onPostExecute(byte[] result) {
                super.onPostExecute(result);
            }
        }
}




