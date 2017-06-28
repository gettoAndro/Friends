package com.getto.friends2;

import com.getto.friends2.model_retrofit.Users;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Getto on 24.06.2017.
 */

public interface UsersApi {

    @GET("api/")
    Call<Users> getUsers(@Query("page")int page, @Query("results")int count);

}
