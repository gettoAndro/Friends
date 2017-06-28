package com.getto.friends2.details;


import com.getto.friends2.User;
import com.getto.friends2.UserDao;

/**
 * Created by Getto on 08.07.2016.
 */
public class DetailsPresenter {
    private DetailsView detailsView;

    public DetailsPresenter(DetailsView detailsView) {
        this.detailsView = detailsView;
    }

    public void onCreate(){
        detailsView.onCreateView();
    }
        public void onLaunchGallery(){
            detailsView.launchGallery();
        }


        public void updateUser(UserDao userDao, User user){
            userDao.update(user);
        }

}
