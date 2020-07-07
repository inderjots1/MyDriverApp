package com.io.my_book_store_track.activities.home;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.io.my_book_store_track.Config;
import com.io.my_book_store_track.R;
import com.io.my_book_store_track.activities.authorization.LoginActivity;
import com.io.my_book_store_track.apiCaller.ApiCaller;
import com.io.my_book_store_track.fragment.DeliveredFragment;
import com.io.my_book_store_track.fragment.OnWayFragment;
import com.io.my_book_store_track.fragment.PickUp;
import com.io.my_book_store_track.localStorage.LocalStorage;
import com.io.my_book_store_track.model.responseModel.OrderUpdateResponseModel;
import com.io.my_book_store_track.utils.ImageUtility;
import com.io.my_book_store_track.utils.NewProgressBar;
import com.io.my_book_store_track.utils.PermissionFile;
import com.io.my_book_store_track.utils.Utils;
import com.io.my_book_store_track.utils.userOnlineInfo;
import com.koushikdutta.async.future.FutureCallback;

import java.io.File;

public class UpdateOrderActivity extends AppCompatActivity implements View.OnClickListener {

    int postion = Config.postion;
Fragment currFrag;
    private Activity activity;
    private String orderId, driverId;
    private RelativeLayout btnPickUp, btnOnWay, btnDeliverd;
    private NewProgressBar dialog;
    private userOnlineInfo user;
    private LinearLayout iv_cart;
    LocalStorage localStorage;
    private ImageView ivOpenDrawer;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    int CameraPicker = 124;
    private static int GalleryPicker = 123;
    private ImageView iv_Image;
    private TextView text;
    private RelativeLayout btn_updateStatus;
    private static final int REQUEST_WRITE_STORAGE = 1004;
    private static final int REQUEST_CODE_LOCATION = 1000;
    private static final int REQUEST_CODE_STORAGE = 1003;

    private PermissionFile permissionFile;
    private String licenseFile = "";
    private ImageUtility imageUtility;
    private File file;
    private File destination;
    private Uri outputFileUri;
    private LinearLayout linearLayout;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
    //  private SignaturePad mSignaturePad;
    private LinearLayout ll_signature;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_order);
        intializeView();
        bindListner();
        // startWorking();
    }



    /*--------------------------------------- intialize all views that are used in this activity ----------------------------*/

    private void intializeView() {
        activity = UpdateOrderActivity.this;
        user = new userOnlineInfo();
        // verifyStoragePermissions(this);
        localStorage = new LocalStorage(activity);
        //  mSignaturePad = findViewById(R.id.signature_pad);
        linearLayout = findViewById(R.id.ll_signature);
        btnPickUp = findViewById(R.id.btn_pickup);
        btnOnWay = findViewById(R.id.btn_onway);
        btnDeliverd = findViewById(R.id.btn_delivered);
        iv_cart = findViewById(R.id.tv_cart);
        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        localStorage = new LocalStorage(activity);
        permissionFile = new PermissionFile(activity);
        imageUtility = new ImageUtility(activity);

        iv_Image = findViewById(R.id.iv_Image);
        ll_signature = findViewById(R.id.ll_signature);
        ivOpenDrawer = findViewById(R.id.ivOpenDrawer);
        text = findViewById(R.id.text);
        btn_updateStatus = findViewById(R.id.btn_updateStatus);
        changeFrag(new PickUp(), false); }


    private void bindListner() {
        btnPickUp.setOnClickListener(this);
        btnOnWay.setOnClickListener(this);
        btnDeliverd.setOnClickListener(this);
        tabLayout.removeAllTabs();
        tabLayout.addTab(tabLayout.newTab().setText("Pick up"));
        tabLayout.addTab(tabLayout.newTab().setText("Out For Delivery"));
        tabLayout.addTab(tabLayout.newTab().setText("Delivered"));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                postion = tab.getPosition();
                if (postion == 1) {
                    Config.postion = 0;
                    text.setText("On my way");
                    changeFrag(new OnWayFragment(), false);
                } else if (postion == 2) {
                    Config.postion = 0;
                    text.setText("Delivered");
                    changeFrag(new DeliveredFragment(), false);
                } else if (postion == 0) {
                    Config.postion = 0;
                    text.setText("update Order Picture");
                    changeFrag(new PickUp(), false);

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });



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
        ivOpenDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }

    private void changeFrag(Fragment fragment, boolean addToBack) {

        currFrag = fragment;
        FragmentTransaction m = getSupportFragmentManager().beginTransaction()
                .replace(R.id.viewPager, fragment);
        if (addToBack) {
            m.addToBackStack(null);
            m.setCustomAnimations(R.anim.fade_in,
                    R.anim.fade_out);
        }
        m.commit();
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
    protected void onSaveInstanceState(Bundle InstanceState) {
        super.onSaveInstanceState(InstanceState);
        InstanceState.clear();
    }
    @Override
    public void onBackPressed() {
        Config.postion = 0;
        super.onBackPressed();
    }


}
