package com.io.my_book_store_track.activities.home;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.io.my_book_store_track.Config;
import com.io.my_book_store_track.R;
import com.io.my_book_store_track.activities.authorization.LoginActivity;
import com.io.my_book_store_track.adapter.OrderAdapter;
import com.io.my_book_store_track.apiCaller.ApiCaller;
import com.io.my_book_store_track.localStorage.LocalStorage;
import com.io.my_book_store_track.model.Model;
import com.io.my_book_store_track.model.responseModel.Datum;
import com.io.my_book_store_track.model.responseModel.OrderListResponseModel;
import com.io.my_book_store_track.utils.NewProgressBar;
import com.io.my_book_store_track.utils.Utils;
import com.io.my_book_store_track.utils.userOnlineInfo;
import com.koushikdutta.async.future.FutureCallback;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    private Activity activity;
    private String userMobile;
    private RecyclerView rv_order;
    private OrderAdapter adapter;
    private List<Model> models;
    private TextView tv_notfound;
    private NewProgressBar dialog;
    private userOnlineInfo user;
    private LocalStorage localStorage;
    boolean doubleBackToExitPressedOnce = false;
    LinearLayout iv_cart;
    private SwipeRefreshLayout swipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intializeViews();
        startWorking();
    }


    @SuppressLint("WrongViewCast")
    private void intializeViews() {
        activity = MainActivity.this;
        localStorage = new LocalStorage(activity);
        swipeRefreshLayout = findViewById(R.id.swipe);
        swipeRefreshLayout.setOnRefreshListener(this);
        user = new userOnlineInfo();
        tv_notfound = findViewById(R.id.tv_notfound);
        rv_order = findViewById(R.id.rv_order);
        iv_cart = findViewById(R.id.tv_cart);
        models = new ArrayList<>();
        iv_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(MainActivity.this, iv_cart);
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

    private void startWorking() {
        getDataFromLocalStorage();
        apiOrder();

    }

    /*------------------------------------------------ get data from Local Stroage -------------------------------------*/

    private void getDataFromLocalStorage() {
        userMobile = String.valueOf(localStorage.getDriverDetial().getData().getDeliveryGuyId());
    }

    /*------------------------------------------------ order List api ---------------------------------------------------*/
    private void apiOrder() {
        if (user.isOnline(activity)) {
            dialog = new NewProgressBar(activity);
            dialog.show();


            ApiCaller.OrderList(activity, Config.Url.orders, userMobile, new FutureCallback<OrderListResponseModel>() {
                @Override
                public void onCompleted(Exception e, OrderListResponseModel result) {
                    if (e != null) {
                        Utils.showAlertDialog(activity, "Something Went Wrong");
                        dialog.dismiss();
                        return;
                    }

                    if (result != null) {
                        if (result.getStatus() == true) {
                            recyclerViewSetUp(result.getData());
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


    private void initArray() {
        models.clear();
        models.add(new Model("1"));
        models.add(new Model("2"));
        models.add(new Model("3"));
        models.add(new Model("4"));
        models.add(new Model("5"));
        models.add(new Model("6"));
        models.add(new Model("7"));
    }

    private void recyclerViewSetUp(List<Datum> orders) {

        if (orders.isEmpty()) {
            tv_notfound.setVisibility(View.VISIBLE);
            rv_order.setVisibility(View.GONE);
        } else {
            tv_notfound.setVisibility(View.GONE);
            rv_order.setVisibility(View.VISIBLE);
            rv_order.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
            adapter = new OrderAdapter(activity, orders);
            rv_order.setAdapter(adapter);
        }
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
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
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                intializeViews();
                startWorking();
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 1000);


    }
  /*  @Override
    protected void onRestart() {
        intializeViews();
        startWorking();
        super.onRestart();
    }*/

    @Override
    protected void onSaveInstanceState(Bundle InstanceState) {
        super.onSaveInstanceState(InstanceState);
        InstanceState.clear();
    }
}
