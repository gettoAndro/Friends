package com.getto.friends2.userlist;

import com.getto.friends2.model_retrofit.Users;
import retrofit2.Callback;

/**
 * Created by Getto on 24.06.2017.
 */

public interface CallBackUser extends Callback<Users> {

    void onLoad();

}
