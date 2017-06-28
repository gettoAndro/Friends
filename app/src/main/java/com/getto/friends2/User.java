package com.getto.friends2;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;

/**
 * Created by Getto on 24.06.2017.
 */
@Entity(indexes = {
        @Index(value = "name, email, phone, image", unique = true)
})

public class User {
    @Id
    private Long id_user;

    @NotNull
    private String name;
    private String email;
    private String phone;
    private String image;
    private boolean friends;


    public User(){

    }

    @Generated(hash = 715094188)
    public User(Long id_user, @NotNull String name, String email, String phone,
            String image, boolean friends) {
        this.id_user = id_user;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.image = image;
        this.friends = friends;
    }



    public Long getId_user() {
        return id_user;
    }

    public void setId_user(Long id_user) {
        this.id_user = id_user;
    }

    public String getName() {
        return name;
    }

    public void setName(@NotNull String name) {
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isFriends() {
        return friends;
    }

    public void setFriends(boolean friends) {
        this.friends = friends;
    }

    public boolean getFriends() {
        return this.friends;
    }
}
