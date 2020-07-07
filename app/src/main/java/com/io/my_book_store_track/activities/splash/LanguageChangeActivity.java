package com.io.my_book_store_track.activities.splash;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.io.my_book_store_track.R;
import com.io.my_book_store_track.activities.authorization.LoginActivity;
import com.io.my_book_store_track.localStorage.LocalStorage;

import java.util.Locale;

public class LanguageChangeActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_en, btn_kuw;
    private LocalStorage localStorage;
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_language_change);
        intializeViews();
        bindListner();
    }

    /*------------------------------------- intiliaze all Views that are used in this activity ---------------------------*/

    private void intializeViews() {
        getSupportActionBar().hide();
        activity = LanguageChangeActivity.this;
        btn_kuw = findViewById(R.id.btn_kuw);
        btn_en = findViewById(R.id.btn_en);
        localStorage = new LocalStorage(this);
    }

    /*----------------------------------------- bind all views that are used in this activity ---------------------------*/
    private void bindListner() {
        btn_kuw.setOnClickListener(this);
        btn_en.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_kuw:
                kuwaitLanguage();
                return;
            case R.id.btn_en:
                englishLangyage();
        }
    }

    /*------------------------------------------------- kuwait language Work -----------------------------------------------*/

    private void kuwaitLanguage() {
        Locale locale = new Locale("hi");
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, null);
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        finish();
    }

    /*----------------------------------------------- English language Work -----------------------------------------------*/

    private void englishLangyage() {
        Locale locale = new Locale("en");
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, null);
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
