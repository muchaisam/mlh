package com.example.mlh

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mlh.R
import android.content.Intent
import android.os.Handler
import com.example.mlh.user.LoggingActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        setContentView(R.layout.activity_splash)
        Handler().postDelayed({
            finish()
            startActivity(Intent(applicationContext, LoggingActivity::class.java))
        }, 4000)
    }
}