package com.getto.friends2.details;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import com.getto.friends2.App;
import com.getto.friends2.DaoSession;
import com.getto.friends2.R;
import com.getto.friends2.User;
import com.getto.friends2.UserDao;
import com.squareup.picasso.Picasso;

import org.greenrobot.greendao.query.Query;

import java.io.File;
import java.io.IOException;

/**
 * Created by Getto on 13.03.2016.
 */
public class ActivityDetails extends AppCompatActivity implements DetailsView{

    private DetailsPresenter presenter;
    private EditText editName, editPhone, editEmail;
    private ImageView imageFriend;
    static final int GALLERY_REQUEST = 1;
    private UserDao userDao;
    private User user;
    private  Uri selectedImage;
    private Long id_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_details);
        initViews();
        presenter = new DetailsPresenter(this);
        presenter.onCreate();
    }

    public void initViews(){
        id_user = getIntent().getLongExtra("user", 0);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Info");
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editName = (EditText)findViewById(R.id.edit_name);
        editEmail = (EditText)findViewById(R.id.edit_email);
        editPhone = (EditText) findViewById(R.id.edit_phone);
        imageFriend = (ImageView) findViewById(R.id.image_details);
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

    public void onClickPortrait(View view){
        presenter.onLaunchGallery();
    }

    @Override
    public void launchGallery() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
    }

    @Override
    public void onCreateView() {
        DaoSession daoSession = ((App) getApplication()).getDaoSession();
        userDao = daoSession.getUserDao();
        user = userDao.load(id_user);
        editName.setText(user.getName());
        editEmail.setText(user.getEmail());
        editPhone.setText(user.getPhone());
        File file = new File(user.getImage());
        Picasso.with(this).load(file).into(imageFriend);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap = null;
        switch (requestCode) {
            case GALLERY_REQUEST:
                if (resultCode == RESULT_OK) {
                    selectedImage = data.getData();
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    imageFriend.setImageBitmap(bitmap);
                }
        }
    }


    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }


    public void onApply(View view) {
        user.setName(editName.getText().toString());
        user.setEmail(editEmail.getText().toString());
        user.setPhone(editPhone.getText().toString());
        if (selectedImage != null)
        user.setImage(getRealPathFromURI(selectedImage));
        Log.d("User", "ID = " + userDao.getKey(user) + "Name = " + user.getName() + ", email = " + user.getEmail());
        presenter.updateUser(userDao, user);
    }
}
