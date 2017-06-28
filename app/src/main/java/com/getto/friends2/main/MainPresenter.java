package com.getto.friends2.main;


import com.getto.friends2.User;
import com.getto.friends2.UserDao;

/**
 * Created by Getto on 06.07.2016.
 */
public class MainPresenter {
    private MainView mainView;
    private Model model;

    MainPresenter(MainView mainView, Model model){
        this.mainView = mainView;
        this.model = model;
    }

    public void onCreate(){
        mainView.onCreateView();
    }

    public void updateList(){
        mainView.updateAdapter();
    }

    public void OnClickFindFriend(){
        mainView.FindFriends();
    }


    public void delFriend(User user, UserDao userDao){
        userDao.delete(user);
    }
}
