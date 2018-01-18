package com.example.directoryx.projectuasmob.Helper;

/**
 * Created by directoryx on 17/12/17.
 */


public class User {
    private String username ,password,id;


    public User(String username, String password,String id) {
        this.username = username;
        this.password = password;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
