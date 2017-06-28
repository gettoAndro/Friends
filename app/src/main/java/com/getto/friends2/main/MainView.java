package com.getto.friends2.main;

/**
 * Created by Getto on 06.07.2016.
 */
public interface MainView {
    void onCreateView();
    void FindFriends();
    void updateAdapter();
    void showInfoFriend(int position);
}
