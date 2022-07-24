package com.example.mlh.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.example.mlh.MainActivity;
import com.example.mlh.R;
import com.google.firebase.auth.FirebaseAuth;
import com.shashank.sony.fancytoastlib.FancyToast;


import dev.shreyaspatil.MaterialDialog.MaterialDialog;

public class LoggingActivity extends AppCompatActivity {

        LinearLayout login , lay_signup;
        private FirebaseAuth auth;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_logging);
            auth = FirebaseAuth.getInstance();
            overridePendingTransition(R.anim.abc_fade_in,R.anim.abc_fade_out);

            if(auth.getCurrentUser() !=null){
                startActivity(new Intent(LoggingActivity.this, MainActivity.class));
            }

            login = (LinearLayout) findViewById(R.id.login);
            login.setOnClickListener(view -> {
                startActivity(new Intent(LoggingActivity.this, LoginActivity.class));
                finish();
            });
            lay_signup = (LinearLayout) findViewById(R.id.lay_signup);
            lay_signup.setOnClickListener(view -> {
                Intent i = new Intent(LoggingActivity.this, RegisterActivity.class);
                startActivity(i);
                finish();
            });

            if (!isNetworkAvailable()){
                MaterialDialog mDialog = new MaterialDialog.Builder(this)
                        .setTitle("No Internet")
                        .setMessage("Please check your internet connection")
                        .setCancelable(false)
                        .setPositiveButton("Dismiss", R.drawable.ic_close, (dialogInterface, which) -> dialogInterface.dismiss())
                        .build();

                // Show Dialog
                mDialog.show();
                FancyToast.makeText(LoggingActivity.this, "You are not connected to the internet", FancyToast.WARNING, FancyToast.LENGTH_SHORT, false).show();
            }else if (isNetworkAvailable())
            {
                FancyToast.makeText(LoggingActivity.this, "Get Started", FancyToast.SUCCESS, FancyToast.LENGTH_SHORT, false).show();
            }


        }

        public boolean isNetworkAvailable(){
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivityManager != null){

                NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
                if (capabilities != null){
                    if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)){
                        return true;
                    }
                    else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)){
                        return true;
                    }
                    else return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET);
                }
            }
            return false;
        }
    }
