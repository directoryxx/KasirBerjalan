package com.example.directoryx.projectuasmob.Helper;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.directoryx.projectuasmob.MainActivity;

/**
 * Created by DirectoryX on 28/09/2017.
 */

public class SharedPrefManager {
    //the constants
    private static final String SHARED_PREF_NAME = "kasir";
    private static final String KEY_ID = "0";
    private static final String KEY_USERNAME = "keyusername";
    private static final String KEY_PASSWORD = "keypassword";
    private static SharedPrefManager mInstance;
    private static Context mCtx;

    private SharedPrefManager(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    //method to let the user login
    //this method will store the user data in shared preferences
    public void userLogin(User user) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_ID, String.valueOf(user.getId()));
        editor.putString(KEY_USERNAME, user.getUsername());
        editor.putString(KEY_PASSWORD, user.getPassword());
        editor.apply();
    }

    public static String getKeyId() {
        return KEY_ID;
    }

    public static String getKeyUsername() {
        return KEY_USERNAME;
    }

    public static String getKeyPassword() {
        return KEY_PASSWORD;
    }

    //this method will checker whether user is already logged in or not
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERNAME, null) != null;
    }

    //this method will give the logged in user
    public User getUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new User(
                sharedPreferences.getString(KEY_USERNAME, null),
                sharedPreferences.getString(KEY_PASSWORD, null),
                sharedPreferences.getString(KEY_ID, null)
        );
    }

    //this method will logout the user
    public void logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        Intent keluar = new Intent(mCtx, MainActivity.class);
        keluar.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mCtx.startActivity(keluar);
    }
}