package com.getto.friends2.userlist;

/**
 * Created by Getto on 07.04.2016.
 */
public class User {
    private String name,  email, phone;
    private String image;
    private boolean friends;

   public void setName(String name){this.name = name;}

    public void setImage(String image) {
        this.image = image;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setFriends(boolean friends) {
        this.friends = friends;
    }

    public boolean isFriends() {
        return friends;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

}
