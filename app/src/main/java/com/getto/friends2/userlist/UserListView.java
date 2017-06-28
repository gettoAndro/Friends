package com.getto.friends2.userlist;
import com.getto.friends2.model_retrofit.Users;

/**
 * Created by Getto on 06.07.2016.
 */
public interface UserListView {
    void addItems(Users users);
    void swipeRefresh();
    void swipeDismiss();
    void ErrorMessageFromConnect();
}
