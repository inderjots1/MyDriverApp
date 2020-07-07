package com.io.my_book_store_track.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.github.gcacace.signaturepad.views.SignaturePad;
import com.io.my_book_store_track.Config;
import com.io.my_book_store_track.R;
import com.io.my_book_store_track.activities.home.MainActivity;
import com.io.my_book_store_track.apiCaller.ApiCaller;
import com.io.my_book_store_track.model.responseModel.OrderUpdateResponseModel;
import com.io.my_book_store_track.utils.NewProgressBar;
import com.io.my_book_store_track.utils.Utils;
import com.io.my_book_store_track.utils.userOnlineInfo;
import com.koushikdutta.async.future.FutureCallback;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;


public class DeliveredFragment extends Fragment {

    private Activity activity;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private SignaturePad mSignaturePad;
    private Button mClearButton;
    private Button mSaveButton;
    ImageView iv_image;
    File file;
    RelativeLayout btn_updateStatus;
    private NewProgressBar dialog;
    private userOnlineInfo user;

    public DeliveredFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_delivered, container, false);
        verifyStoragePermissions(getActivity());
        activity = getActivity();
        mSignaturePad = view.findViewById(R.id.signature_pad);
        btn_updateStatus = view.findViewById(R.id.btn_updateStatus);
        user = new userOnlineInfo();


        mSignaturePad.setOnSignedListener(new SignaturePad.OnSignedListener() {
            @Override
            public void onStartSigning() {
                //Toast.makeText(getActivity(), "OnStartSigning", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSigned() {
              /*  mSaveButton.setEnabled(true);
                mClearButton.setEnabled(true);*/
            }

            @Override
            public void onClear() {
                //mSaveButton.setEnabled(false);
                //   mClearButton.setEnabled(false);
            }
        });
       /* mClearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSignaturePad.clear();
            }
        });*/

        btn_updateStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap signatureBitmap = mSignaturePad.getSignatureBitmap();
                if (addJpgSignatureToGallery(signatureBitmap)) {
                    Log.e("file", "" + file);
                    apiOrderStatusUpdate("Delivered");
                    //Toast.makeText(getActivity(), "Signature saved into the Gallery", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Unable to store the signature", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length <= 0
                        || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getActivity(), "Cannot write images to external storage", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public File getAlbumStorageDir(String albumName) {
        // Get the directory for the user's public pictures directory.
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), albumName);
        if (!file.mkdirs()) {
            Log.e("SignaturePad", "Directory not created");
        }
        return file;
    }

    public void saveBitmapToJPG(Bitmap bitmap, File photo) throws IOException {
        Bitmap newBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(newBitmap);
        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(bitmap, 0, 0, null);
        OutputStream stream = new FileOutputStream(photo);
        newBitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream);
        stream.close();
    }

    public boolean addJpgSignatureToGallery(Bitmap signature) {
        boolean result = false;
        try {
            file = new File(getAlbumStorageDir("SignaturePad"), String.format("Signature_%d.jpg", System.currentTimeMillis()));
            saveBitmapToJPG(signature, file);
            scanMediaFile(file);
            result = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private void scanMediaFile(File photo) {

        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);


        Uri contentUri = Uri.fromFile(photo);
        mediaScanIntent.setData(contentUri);
        getActivity().sendBroadcast(mediaScanIntent);
    }


    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    private void loadImageFromStorage(String path) {

        File f = new File(path, "profile.jpg");

        Log.e("heoo", "" + f);
        // Bitmap b = BitmapFactory.decodeFile(f.getAbsolutePath());
        Bitmap myBitmap = BitmapFactory.decodeFile(f.getAbsolutePath());
        // Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));

        iv_image.setImageBitmap(myBitmap);

    }



    private void apiOrderStatusUpdate(String delivered) {
        if (user.isOnline(getActivity())) {
            dialog = new NewProgressBar(getActivity());
            dialog.show();

            if (Config.devliveryStatus.equals(delivered)) {
                dialog.dismiss();
                Toast.makeText(getActivity(), "you have already update status : " + Config.devliveryStatus, Toast.LENGTH_SHORT).show();
            } else {
                ApiCaller.updateOrder(activity, Config.Url.updateStatus, Config.OrderID, Config.driverID, file, delivered, new FutureCallback<OrderUpdateResponseModel>() {

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
                                Config.postion = 0;
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
