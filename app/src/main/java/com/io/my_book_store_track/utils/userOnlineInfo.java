package com.io.my_book_store_track.utils;

import android.content.Context;
import android.net.ConnectivityManager;

public class userOnlineInfo {

    public boolean isOnline(Context c) {
        ConnectivityManager cm =
                (ConnectivityManager)c.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null &&
                cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }
}
