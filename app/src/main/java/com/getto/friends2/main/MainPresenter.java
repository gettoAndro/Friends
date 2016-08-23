package com.getto.friends2.main;

import android.database.Cursor;

import com.getto.friends2.R;

/**
 * Created by Getto on 06.07.2016.
 */
public class MainPresenter {
    private MainView mainView;
    private Model model;
    private Cursor cursor;

    MainPresenter(MainView mainView, Model model){
        this.mainView = mainView;
        this.model = model;
    }

    public void onCreate(){
        model.Open();
        cursor = model.getAllData();
       mainView.onCreateView(cursor);
    }

    public void OnClickFindFriend(){
        mainView.FindFriends();
    }

    public void onResume(){
        cursor.requery();
        mainView.updateAdapter();
    }

    public void delFriend(long id){
        model.delRec(id);
        cursor.requery();
        mainView.updateAdapter();
    }
    public void launchActivityDetails(long item){
        mainView.showInfoFriend(item);
    }

}
