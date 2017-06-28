package com.getto.friends2.userlist;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;

import com.getto.friends2.*;
import com.getto.friends2.User;
import com.getto.friends2.model_retrofit.Result;
import com.getto.friends2.model_retrofit.Users;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Getto on 06.07.2016.
 */

public class UsersPresenter implements CallBackUser{

    private UserListView userListView;
    private static UsersApi usersApi;
    private int page = 1;

    public UsersPresenter(UserListView userListView) {
        this.userListView = userListView;
    }

    public void onCreate(){
        usersApi = RetrofitClient.getApi();
        usersApi.getUsers(page, 10).enqueue(this);
        onLoad();
    }

    public void nextPage(){
        usersApi.getUsers(page++, 10).enqueue(this);
        onLoad();
    }

    public void addToDataBase(Context context ,Result result, UserDao userDao){
        String imagename = result.getPicture().getMedium().substring(result.getPicture().getMedium().lastIndexOf("/") + 1);

        imageDownload(context, result.getPicture().getMedium(), imagename);

        User user = new User();
        user.setEmail(result.getEmail());
        user.setImage(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getPath() + "/" + imagename);
        user.setName(result.getName().getFirst() + " " + result.getName().getLast());
        user.setPhone(result.getPhone());
        user.setFriends(true);
        userDao.insert(user);
    }
    public void imageDownload(Context ctx, String url, String path){
        Picasso.with(ctx)
                .load(url)
                .into(getTarget(path));
    }

    //target to save
    private Target getTarget(final String url){

        Target target = new Target(){
            @Override
            public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {

                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {

                        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getPath() + "/" + url);
                        String path = file.getPath();
                        Log.d("PATH", path);
                        Bitmap b = BitmapFactory.decodeFile(path);

                        if(b == null)
                        {

                            Log.d("IMAGE-EXIST", "NOT EXIST");
                            Log.d("THREAD-SAVE_SD", file.getAbsolutePath());
                            try {
                                file.createNewFile();
                                FileOutputStream ostream = new FileOutputStream(file);
                                bitmap.compress(Bitmap.CompressFormat.JPEG, 80, ostream);
                                ostream.flush();
                                ostream.close();
                            } catch (IOException e){
                                Log.e("IOException", e.getLocalizedMessage());
                            }

                        }
                        else {
                            Log.d("IMAGE-EXIST", "EXIST");
                        }
                    }
                });

                t.start();
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
            }
        };
        return target;
    }

    public void onRefresh(){
    }

    @Override
    public void onLoad() {
        userListView.swipeRefresh();
    }

    @Override
    public void onResponse(@NonNull Call<Users> call, @NonNull Response<Users> response) {

        if (response.isSuccessful()) {
            Log.d("Retrofit", "Response: " + response.code() + ", " + response.body().getResults().get(0).getName().getFirst());
            userListView.addItems(response.body());
            userListView.swipeDismiss();
        }
    }

    @Override
    public void onFailure(Call<Users> call, Throwable t) {
        userListView.ErrorMessageFromConnect();
        userListView.swipeDismiss();
    }

}
