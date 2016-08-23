package com.getto.friends2.details;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.getto.friends2.R;
import com.getto.friends2.main.Model;

import java.io.ByteArrayOutputStream;
import java.io.IOException;


/**
 * Created by Getto on 13.03.2016.
 */
public class ActivityDetails extends Activity implements DetailsView{

    private Model model;
    private DetailsPresenter presenter;
    private EditText editName, editPhone, editEmail;
    private ImageView imageFriend;
    static final int GALLERY_REQUEST = 1;
    private Bitmap bitmap = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_details);
        editName = (EditText)findViewById(R.id.edit_name);
        editEmail = (EditText)findViewById(R.id.edit_email);
        editPhone = (EditText) findViewById(R.id.edit_phone);
        imageFriend = (ImageView) findViewById(R.id.image_details);
        model = new Model(this);
        presenter = new DetailsPresenter(model, this);
        presenter.onCreate();
    }
    public void onClickPortreit(View view){
        presenter.onLaunchGallery();
    }

    @Override
    public void launchGallery() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
    }

    @Override
    public void onCreateView(Cursor cursor) {
        cursor.moveToFirst();
        editName.setText(cursor.getString(cursor.getColumnIndex("name")));
        editEmail.setText(cursor.getString(cursor.getColumnIndex("email")));
        editPhone.setText(cursor.getString(cursor.getColumnIndex("phone")));
        byte[] bb = cursor.getBlob(cursor.getColumnIndex("image"));
        if (bb != null){ bitmap = BitmapFactory.decodeByteArray(bb, 0, bb.length);
            imageFriend.setImageBitmap(bitmap);}
        else imageFriend.setImageResource(R.drawable.q);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
       // Bitmap bitmap = null;
        switch (requestCode) {
            case GALLERY_REQUEST:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = data.getData();
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    imageFriend.setImageBitmap(bitmap);

    }
        }
    }

    @Override
    public void onApplyChanges() {
        if (bitmap != null || editName.getText() != null || editEmail.getText() != null || editPhone.getText() != null) {
            model.EditImage(getBytesFromBitmap(bitmap), getID());
            model.EditName(editName.getText().toString(), getID());
            model.EditEmail(editEmail.getText().toString(), getID());
            model.EditPhone(editPhone.getText().toString(), getID());
            Toast.makeText(this, "Данные успешно изменены", Toast.LENGTH_SHORT).show();
        } else Toast.makeText(this, "Некоторые данные пустые. Заполните их!", Toast.LENGTH_LONG).show();
    }

    public void onApply(View v){
       presenter.ApplyChanges();
    }


    @Override
    public long getID() {
        return  getIntent().getExtras().getLong("item");
    }

    public byte[] getBytesFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        return stream.toByteArray();
    }
}
