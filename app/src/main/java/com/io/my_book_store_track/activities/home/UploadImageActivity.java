package com.io.my_book_store_track.activities.home;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.io.my_book_store_track.Config;
import com.io.my_book_store_track.R;
import com.io.my_book_store_track.activities.authorization.LoginActivity;
import com.io.my_book_store_track.apiCaller.ApiCaller;
import com.io.my_book_store_track.localStorage.LocalStorage;
import com.io.my_book_store_track.model.responseModel.OrderUpdateResponseModel;
import com.io.my_book_store_track.utils.ImageUtility;
import com.io.my_book_store_track.utils.NewProgressBar;
import com.io.my_book_store_track.utils.PermissionFile;
import com.io.my_book_store_track.utils.Utils;
import com.io.my_book_store_track.utils.userOnlineInfo;
import com.koushikdutta.async.future.FutureCallback;

import java.io.File;

public class UploadImageActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView iv_Image;
    private Activity activity;
    private TextView text;
    private String orderId, driverId, status;
    private RelativeLayout btn_updateStatus;
    private static final int REQUEST_WRITE_STORAGE = 1004;
    private static final int REQUEST_CODE_LOCATION = 1000;
    private static final int REQUEST_CODE_STORAGE = 1003;
    private static int GalleryPicker = 123;
    private PermissionFile permissionFile;
    private String licenseFile = "";
    private ImageUtility imageUtility;
    private File imgFile;
    private File destination;
    private Uri outputFileUri;
    int CameraPicker = 124;
    private NewProgressBar dialog;
    private userOnlineInfo user;
    LinearLayout iv_cart;
    LocalStorage localStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);
        intializeViews();
        bindListner();
        startWorking();
    }


    private void intializeViews() {
        activity = UploadImageActivity.this;
        user = new userOnlineInfo();
        iv_cart = findViewById(R.id.tv_cart);
        localStorage = new LocalStorage(activity);
        permissionFile = new PermissionFile(activity);
        imageUtility = new ImageUtility(activity);
        iv_Image = findViewById(R.id.iv_Image);
        text = findViewById(R.id.text);
        btn_updateStatus = findViewById(R.id.btn_updateStatus);

    }

    private void bindListner() {
        iv_Image.setOnClickListener(this);
        btn_updateStatus.setOnClickListener(this);
        iv_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(activity, iv_cart);
                popup.getMenuInflater().inflate(R.menu.items_men, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        int id = item.getItemId();
                        switch (id) {
                            case R.id.item1:
                                Intent i = new Intent(activity, LoginActivity.class);
                                startActivity(i);
                                localStorage.putDriverDetail(null);
                                localStorage.putBooleAan(LocalStorage.isLoggedIn, false);
                                return true;


                        }
                        return true;
                    }
                });

                popup.show();//showing popup menu
            }

        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_Image:
                cameraIntent();
                return;
            case R.id.btn_updateStatus:
                if (imgFile == null || imgFile.length() == 0) {
                    Utils.toast(activity, "please take a Picture");
                } else {
                    apiOrderStatusUpdate();
                }
                return;
        }
    }


    private void startWorking() {
        getDataFromIntent();
        readWritePermission();
        multiplePermission();
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            orderId = Config.OrderID;
            driverId =Config.driverID;
            status = intent.getStringExtra("status");
            if (status.equals("Delivered")){
                text.setText("Upload Order Signature");
            }
        }

    }


    private void readWritePermission() {
        boolean hasPermission = (ContextCompat.checkSelfPermission(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
        if (!hasPermission) {
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_WRITE_STORAGE);
        }
    }

    private void multiplePermission() {
        if (!permissionFile.checkLocStorgePermission(activity)) {
            permissionFile.checkLocStorgePermission(activity);
        }
    }

    /* ---------------------------------------------pick image from the Camera using intent -----------------------------------------*/

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        final File root = permissionFile.getFile();
        root.mkdirs();
        String filename = permissionFile.getUniqueImageFilename();
        destination = new File(root, filename);
        outputFileUri = FileProvider.getUriForFile(
                activity,
                activity
                        .getPackageName() + ".provider", destination);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
        startActivityForResult(intent, CameraPicker);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null && data.getData() != null) {
            if (requestCode == GalleryPicker) {
                onCaptureImageResult(data, "gallery");
            }
        } else if (resultCode == RESULT_OK && requestCode == CameraPicker) {
            onCaptureImageResult(data, "camera");

        }
    }

    /* --------------------------------- get the actual storage path of image (Camera an dgallery) ----------------------------------*/

    void onCaptureImageResult(Intent data, String imageType) {
        if (imageType.equals("camera")) {
            licenseFile = imageUtility.compressImage(destination.getPath());
            Toast.makeText(activity, "submit", Toast.LENGTH_SHORT).show();

            imgFile = new File(licenseFile);
            if (imgFile.exists()) {
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                iv_Image.setImageBitmap(myBitmap);
            }

        } else {
            licenseFile = imageUtility.compressImage(imageUtility.getRealPathFromURI(activity, data.getData()));
            Toast.makeText(activity, "submit", Toast.LENGTH_SHORT).show();

            imgFile = new File(licenseFile);
            if (imgFile.exists()) {
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                iv_Image.setImageBitmap(myBitmap);
            }

        }
    }


    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {

            case REQUEST_WRITE_STORAGE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    Toast.makeText(activity, "The app was not allowed to write to your storage. Hence, it cannot function properly. Please consider granting it this permission", Toast.LENGTH_LONG).show();
                }
            }
            case REQUEST_CODE_LOCATION:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                }
                break;

            case REQUEST_CODE_STORAGE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                }
                break;

        }
    }

    private void apiOrderStatusUpdate() {
        if (user.isOnline(activity)) {
            dialog = new NewProgressBar(activity);
            dialog.show();
            ApiCaller.updateOrder(activity,Config.Url.updateStatus,orderId,driverId,imgFile,status,new FutureCallback<OrderUpdateResponseModel>(){

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
                            Intent i = new Intent(activity,MainActivity.class);
                            startActivity(i);
                            dialog.dismiss();
                        } else {
                            Utils.toast(activity, result.getMessage());
                            dialog.dismiss();

                        }
                    }
                }
            });
        } else {
            Utils.showAlertDialog(activity, "No Internet Connection");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.items_men, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.item1:
                Intent i = new Intent(activity, LoginActivity.class);
                startActivity(i);
                localStorage.putDriverDetail(null);
                localStorage.putBooleAan(LocalStorage.isLoggedIn, false);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onRestart() {
        intializeViews();
        bindListner();
        startWorking();
        super.onRestart();
    }
}
