package com.getto.friends2.userlist;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import com.getto.friends2.main.Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Getto on 06.07.2016.
 */
public class UsersPresenter {
    private Model model;
    private UserListView userListView;

    public UsersPresenter(Model model, UserListView userListView) {
        this.model = model;
        this.userListView = userListView;
    }
    public void onCreate(){
        new JSONAsyncTask().execute("http://api.randomuser.me/?results=10");
    }
    public void onRefresh(){
        model.delFromListUsers();
        userListView.onRefreshSwipe();
        new JSONAsyncTask().execute("http://api.randomuser.me/?results=10");
    }

    class JSONAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (!userListView.isRefreshingSwipe())
            userListView.onShowProgress();
            else userListView.Swipe();
        }

        @Override
        protected String doInBackground(String... urls) {
            BufferedReader reader = null;
            String resultJson = "";
            try {
                //------------------>>
                URL url = new URL(urls[0]);
                URLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.connect();
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }
                resultJson = buffer.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return resultJson;
        }

        protected void onPostExecute(String result) {
            if (!userListView.isRefreshingSwipe())
            userListView.onCompleteProgress();
             else userListView.SwipeComplete();
            if (result != null) {
                try {
                    JSONObject jsono = new JSONObject(result);
                    JSONArray jarray = jsono.getJSONArray("results");
                    for (int i = 0; i < jarray.length(); i++) {
                        JSONObject object = jarray.getJSONObject(i);
                        JSONObject user = object.getJSONObject("name");
                        JSONObject photo = object.getJSONObject("picture");
                        User users = new User();
                        users.setImage(photo.getString("medium"));
                        users.setName(user.getString("first") + " " + user.getString("last"));
                        users.setEmail(object.getString("email"));
                        users.setPhone(object.getString("phone"));
                        model.addFromListUsers(users);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
               userListView.onRefreshData();
            } else
               userListView.ErrorMessageFromConnect();
        }
    }
}
