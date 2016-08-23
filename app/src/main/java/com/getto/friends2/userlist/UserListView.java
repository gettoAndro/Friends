package com.getto.friends2.userlist;

import android.support.v4.widget.SwipeRefreshLayout;

import com.getto.friends2.main.Model;

/**
 * Created by Getto on 06.07.2016.
 */
public interface UserListView {
  //void onCreateView(Model model);
    void Swipe();
    void SwipeComplete();
    void onRefreshSwipe();
    void onShowProgress();
    boolean isRefreshingSwipe();
    void onCompleteProgress();
    void onRefreshData();
    void ErrorMessageFromConnect();
}
