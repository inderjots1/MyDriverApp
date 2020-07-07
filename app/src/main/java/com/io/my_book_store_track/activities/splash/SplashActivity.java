package com.io.my_book_store_track.activities.splash;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.io.my_book_store_track.R;

public class SplashActivity extends AppCompatActivity {


    private VideoView videoView;
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        intializeViews();
        startWorking();
    }

    /*-------------------------------------- intialize all views that are used in this activity -----------------------------*/

    private void intializeViews() {
        activity = SplashActivity.this;
        videoView = (VideoView) findViewById(R.id.vvAnim);
        getSupportActionBar().hide();
    }

    /*--------------------------------------------------------- start Working ----------------------------------------------*/

    private void startWorking() {
        Uri video = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.animation);

        videoView.setVideoURI(video);

        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                if (isFinishing())
                    return;

                startActivity(new Intent(activity, LanguageChangeActivity.class));
                overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_from_left);
                finish();
            }
        });

        videoView.start();
    }

}
