package com.io.my_book_store_track.activities.authorization;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.io.my_book_store_track.Config;
import com.io.my_book_store_track.R;

import com.io.my_book_store_track.activities.home.MainActivity;
import com.io.my_book_store_track.apiCaller.ApiCaller;
import com.io.my_book_store_track.localStorage.LocalStorage;
import com.io.my_book_store_track.model.responseModel.LoginAndOrderResponseModel;
import com.io.my_book_store_track.utils.NewProgressBar;
import com.io.my_book_store_track.utils.Utils;
import com.io.my_book_store_track.utils.userOnlineInfo;
import com.koushikdutta.async.future.FutureCallback;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Handler;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Activity activity;
    private Button btn_login;
    private EditText etPhone;
    private String strPhone;
    private NewProgressBar dialog;
    private userOnlineInfo user;
    private LocalStorage localStorage;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        intializeViews();
        bindListner();
    }

    /*-------------------------------- intialize  all views that are used in this activity ------------------------------*/

    private void intializeViews() {
        activity = LoginActivity.this;

        localStorage = new LocalStorage(activity);
        user = new userOnlineInfo();
        btn_login = findViewById(R.id.btn_login);
        etPhone = findViewById(R.id.et_number);
        if (localStorage.getBoolean(LocalStorage.isLoggedIn)) {
            Intent intent = new Intent(activity, MainActivity.class);
            startActivity(intent);
            finish();

        }
    }

    /*---------------------------------- bind all views that are used in this activity --------------------------------*/

    private void bindListner() {
        btn_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                loginWork();
                return;
        }
    }

    private void loginWork() {
        strPhone = etPhone.getText().toString().trim();
        if (strPhone.equals("")) {
            etPhone.setError("please enter mobile Number");
        } else {
            apiLoginCaller();
        }

    }

    private void apiLoginCaller() {
        if (user.isOnline(activity)) {
            dialog = new NewProgressBar(activity);
            dialog.show();

            ApiCaller.LoginAndOrderList(activity, Config.Url.loginAndOrder, strPhone, new FutureCallback<LoginAndOrderResponseModel>() {
                @Override
                public void onCompleted(Exception e, LoginAndOrderResponseModel result) {
                    if (e != null) {
                        Utils.showAlertDialog(activity, "Something Went Wrong");
                        dialog.dismiss();
                        return;
                    }

                    if (result != null) {
                        if (result.getStatus() == true) {
                            localStorage.putDriverDetail(result);
                            localStorage.putBooleAan(LocalStorage.isLoggedIn, true);
                            Utils.toast(activity, result.getMessage());
                            Intent intent = new Intent(activity, MainActivity.class);
                            startActivity(intent);
                            finish();
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
}
