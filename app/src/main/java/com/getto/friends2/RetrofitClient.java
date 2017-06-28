package com.getto.friends2;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Getto on 24.06.2017.
 */

public class RetrofitClient {

    static final String BASE_URL = "https://randomuser.me/";

    public static UsersApi getApi() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        UsersApi usersApi = retrofit.create(UsersApi.class);
        return usersApi;
    }
}
