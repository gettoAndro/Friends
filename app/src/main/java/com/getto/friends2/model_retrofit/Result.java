package com.getto.friends2.model_retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Getto on 24.06.2017.
 */

public class Result implements Serializable {

    @SerializedName("name")
    @Expose
    private Name name;

    @SerializedName("email")
    @Expose
    private String email;


    @SerializedName("phone")
    @Expose
    private String phone;

    @SerializedName("picture")
    @Expose
    private Picture picture;

    private boolean friend;


    public boolean isFriend() {
        return friend;
    }

    public void setFriend(boolean friend) {
        this.friend = friend;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    public Picture getPicture() {
        return picture;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }

}
