package com.getto.friends2.main;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import com.getto.friends2.userlist.User;

import org.apache.http.util.ByteArrayBuffer;

import java.sql.Blob;
import java.util.ArrayList;

/**
 * Created by Getto on 10.03.2016.
 */
public class Model {

    public static final String DB_FRIENDS = "friends.db";
    public static final String TABLE_NAME = "people";
    public static final String ID = "_id";
    public static final String IMAGE = "image";
    public static final String NAME = "name";
    public static final String EMAIL = "email";
    public static final String PHONE = "phone";
    public static final String FRIEND = "friend";

    private SQLiteDatabase mDb;
    private DbHelper mDbHelper;
    private final Context mCtx;
    private ArrayList<User> usersList;

    public Model(Context context){
        mCtx = context;
    }



    public void Open(){
        mDbHelper = new DbHelper(mCtx, DB_FRIENDS, null, 1);
        mDb = mDbHelper.getWritableDatabase();
    }
    public void Close(){
        if (mDbHelper != null){
            mDbHelper.close();
        }
    }
    public Cursor getAllData(){
        return mDb.query(TABLE_NAME, null, null, null, null, null, null);
    }

    public void addFromListUsers(User user){
        usersList.add(user);
    }

    public void delFromListUsers(){
        usersList.clear();
    }
    public ArrayList<User> getUsersList() {
        if (usersList == null) usersList = new ArrayList<>();
       return usersList;
    }

    public void addRec(byte[] image, String name, String email, String phone){
        ContentValues values = new ContentValues();
        values.put(IMAGE, image);
        values.put(NAME, name);
        values.put(EMAIL, email);
        values.put(PHONE, phone);
        mDb.insert(TABLE_NAME, null, values);
    }
    public void EditImage(byte[] newImage, long id){
        ContentValues values = new ContentValues();
        values.put(IMAGE, newImage);
        mDb.update(TABLE_NAME, values, ID + " = " + "'" + id + "'", null);
    }

    public void EditName(String newName, long id){
        ContentValues values = new ContentValues();
        values.put(NAME, newName);
        mDb.update(TABLE_NAME, values, ID + " = " + "'" + id + "'", null);
    }



    public Cursor SelectItem(long item){
        return mDb.rawQuery("Select * From "+ TABLE_NAME + " Where _id = " + "'"+item+"'", null);
    }

    public void EditPhone (String newPhone, long id){
        ContentValues values = new ContentValues();
        values.put(PHONE, newPhone);
        mDb.update(TABLE_NAME, values, ID + " = " + id, null);
    }

    public void EditEmail(String newEmail, long id){
        ContentValues values = new ContentValues();
        values.put(EMAIL, newEmail);
        mDb.update(TABLE_NAME, values, ID + " = " + id, null);
    }

    public void delRec (long id){
        mDb.delete(TABLE_NAME, ID + " = " + id, null);
    }


    private class DbHelper extends SQLiteOpenHelper implements BaseColumns {

        public DbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("Create table " + TABLE_NAME + "(" + ID + " integer primary key autoincrement, " + IMAGE + " BLOB, " +
                    NAME + " varchar(40) not null, " + EMAIL + " varchar(120), " + PHONE + " varchar(12)," + FRIEND + "bool);");

//            ContentValues values = new ContentValues();
//            values.put(IMAGE, R.drawable.frost);
//            values.put(NAME, "FROST");
//            values.put(EMAIL, "olegpalyanica97@mail.ru");
//            values.put(PHONE, "0982532534");
//            db.insert(TABLE_NAME, null, values);
//
//
//            values.put(IMAGE, R.drawable.getto);
//            values.put(NAME, "Getto");
//            values.put(EMAIL, "ffgpadfsica97@mail.ru");
//            values.put(PHONE, "0982532435");
//            db.insert(TABLE_NAME, null, values);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        }
    }


}
