package com.io.my_book_store_track;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import java.io.File;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Inderjot Singh 06/03/2020.
 */

public class Config {
    public final static String IP = "mybookstores.net";
    public static String imageUrl ="https://mybookstores.net/api/media/render/?path=";
    public static String driverID;
    public static String OrderID;
    public static String devliveryStatus;
    public static int postion;

    public static class Url {
        public static final String loginAndOrder="delivery/login/";
        public static final String orders="delivery/orders/";
        public static final String updateStatus="delivery/orders-status";

    }




}
