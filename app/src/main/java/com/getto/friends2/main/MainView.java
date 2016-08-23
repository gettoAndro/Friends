package com.getto.friends2.main;

import android.database.Cursor;

/**
 * Created by Getto on 06.07.2016.
 */
public interface MainView {
    void onCreateView(Cursor cursor);
    void FindFriends();
    void updateAdapter();
    void showInfoFriend(long item);
}
