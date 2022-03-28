package com.example.mlh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.mlh.user.LoggingActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        setContentView(R.layout.activity_splash);
        new android.os.Handler().postDelayed(() -> {
            finish();
            startActivity(new Intent(getApplicationContext(), LoggingActivity.class));
        },4000);
    }
}