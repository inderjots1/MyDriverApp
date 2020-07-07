package com.io.my_book_store_track.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.io.my_book_store_track.Config;
import com.io.my_book_store_track.R;
import com.io.my_book_store_track.activities.home.MainActivity;
import com.io.my_book_store_track.activities.home.Potrait;
import com.io.my_book_store_track.apiCaller.ApiCaller;
import com.io.my_book_store_track.model.responseModel.OrderUpdateResponseModel;
import com.io.my_book_store_track.utils.NewProgressBar;
import com.io.my_book_store_track.utils.Utils;
import com.io.my_book_store_track.utils.userOnlineInfo;
import com.koushikdutta.async.future.FutureCallback;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class PickUp extends Fragment implements View.OnClickListener {

    RelativeLayout btn_updateStatus;
    private Activity activity;
    userOnlineInfo user;
    NewProgressBar dialog;
    ImageView iv_Image_p;
    Bitmap bitmap;
    File file;
    public static String a = "";
    String shipped = "Shipped";
    String outdev = "Out For Delivery";

    public PickUp() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pick_up, container, false);
        intializeViews(view);
        bindListner();
        return view;
    }

    private void intializeViews(View view) {
        activity = getActivity();
        user = new userOnlineInfo();

        btn_updateStatus = view.findViewById(R.id.btn_updateStatus);
        iv_Image_p = view.findViewById(R.id.iv_Image_p);
        bitmap = BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.upload);
        String a = saveToInternalStorage(bitmap);
        loadImageFromStorage(a);

    }

    private void bindListner() {
        btn_updateStatus.setOnClickListener(this);
        iv_Image_p.setOnClickListener(this);


    }

    private String saveToInternalStorage(Bitmap bitmapImage) {
        ContextWrapper cw = new ContextWrapper(getActivity());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath = new File(directory, "a.jpg");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }

    private void loadImageFromStorage(String path) {
        file = new File(path, "a.jpg");


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_Image_p:
                a = "a";
                scan();
                return;
            case R.id.btn_updateStatus:
                if (a.equals("")) {
                    Toast.makeText(activity, "please scan code", Toast.LENGTH_SHORT).show();
                } else {
                    apiOrderStatusUpdate();
                }

                return;
        }

    }


    public void scan() {
        IntentIntegrator integrator = new IntentIntegrator(getActivity());
        integrator.setCaptureActivity(Potrait.class);
        integrator.setOrientationLocked(false);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt("Scan Your Barcode");
        integrator.initiateScan();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(getContext(), "Result Not Found", Toast.LENGTH_SHORT).show();
            } else {
                a = result.getContents();
                Toast.makeText(getActivity(), "" + result.getContents(), Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void apiOrderStatusUpdate() {
        if (user.isOnline(activity)) {
            dialog = new NewProgressBar(activity);
            dialog.show();
            if (Config.devliveryStatus.equals(shipped) || Config.devliveryStatus.equals(outdev) || Config.devliveryStatus.equals("Delivered")) {
                dialog.dismiss();
                Toast.makeText(activity, "you have already update status : " + Config.devliveryStatus, Toast.LENGTH_SHORT).show();
            } else {

                ApiCaller.updateOrder(activity, Config.Url.updateStatus, Config.OrderID, Config.driverID, file, shipped, new FutureCallback<OrderUpdateResponseModel>() {

                    @Override
                    public void onCompleted(Exception e, OrderUpdateResponseModel result) {
                        if (e != null) {
                            Utils.showAlertDialog(activity, "Something Went Wrong");
                            dialog.dismiss();
                            return;
                        }

                        if (result != null) {
                            if (result.getStatus() == true) {
                                Utils.toast(activity, result.getMessage());
                                Config.driverID = "";
                                Config.OrderID = "";
                                Intent i = new Intent(activity, MainActivity.class);
                                startActivity(i);
                                dialog.dismiss();
                            } else {
                                Utils.toast(activity, result.getMessage());
                                dialog.dismiss();

                            }
                        }
                    }
                });
            }
        } else {
            Utils.showAlertDialog(activity, "No Internet Connection");
        }
    }


}
