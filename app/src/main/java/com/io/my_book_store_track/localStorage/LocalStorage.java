package com.io.my_book_store_track.localStorage;

import android.content.Context;
import android.content.SharedPreferences;

import com.io.my_book_store_track.model.responseModel.LoginAndOrderResponseModel;
import com.google.gson.Gson;


/**
 * Created by Inderjot 4/3/2020.
 */

public class LocalStorage {
    public static final String LOGGEDINDATA = "LOGGEDINDATA";
    private static final String DISTRIBUTOR = "";
    public static String userId = "userId";
    public static String isLoggedIn ="isLoggedIn";

    private static LocalStorage instance;
    private SharedPreferences storage;



    public LocalStorage(Context context) {
        storage = android.preference.PreferenceManager.getDefaultSharedPreferences(context);
    }


    public static LocalStorage getInstance(Context context) {
        if (instance == null)
            instance = new LocalStorage(context);
        return instance;
    }

    public void putString(String name, String value) {
        storage.edit().putString(name, String.valueOf(value)).apply();
    }

    public void putBooleAan(String name, Boolean value) {
        storage.edit().putBoolean(name, value).apply();
    }

    public boolean getBoolean(String name) {
        return storage.getBoolean(name, false);

    }
    public void putDriverDetail(LoginAndOrderResponseModel profile) {
        final Gson gson = new Gson();
        String profileInJson = gson.toJson(profile);
        storage.edit().putString(DISTRIBUTOR, profileInJson).apply();
    }

    public LoginAndOrderResponseModel getDriverDetial() {
        final Gson gson = new Gson();
        String profileJson = storage.getString(DISTRIBUTOR, null);
        LoginAndOrderResponseModel profile = gson.fromJson(profileJson, LoginAndOrderResponseModel.class);
        return profile;
    }

    public String getString(String name) {
        return storage.getString(name, "");
    }

    public void putInt(String name, int value) {
        storage.edit().putInt(name, value).apply();
    }

    public int getInt(String name) {
        return storage.getInt(name, -1);
    }


    public void clearAll() {
        storage.edit().clear().apply();
    }
}

