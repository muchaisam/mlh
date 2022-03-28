package com.example.mlh;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mlh.screens.AccountActivity;
import com.example.mlh.screens.PaymentActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private TextView greetings;
    TextView transfer, request;
    ImageView userinfo;
    //Constant keys for shared preferences
    public final String SHARED_PREFS = "shared_prefs";
    public final String LAST_NAME = "last_name";

    SharedPreferences sharedPreferences;
    String lastname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

        //initializing shared prefs
        //initializing shared prefs
        sharedPreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        //getting data and storing it
        lastname = sharedPreferences.getString(LAST_NAME, null);

        //on click listeners
        transfer = findViewById(R.id.transfer);
        request = findViewById(R.id.request);

        greetings =findViewById(R.id.greetings);
        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

        if(timeOfDay >= 0 && timeOfDay < 12){
            greetings.setText("Good Morning, " + lastname);
        }else if(timeOfDay >= 12 && timeOfDay < 16){
            greetings.setText("Good Afternoon, " + lastname);
        }else if(timeOfDay >= 16 && timeOfDay < 24){
            greetings.setText("Good Evening, " + lastname);
        }

        //initialize and assigning the variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        //set home selected
        bottomNavigationView.setSelectedItemId(R.id.home);
        //listeners
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home:
                    return true;
                case R.id.payment:
                    Toast.makeText(MainActivity.this, "Payment", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent (getApplicationContext(), PaymentActivity.class));
                    overridePendingTransition(0,0);
                    return true;
                case R.id.accountt:
                    Toast.makeText(MainActivity.this, "Account", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent (getApplicationContext(), AccountActivity.class));
                    overridePendingTransition(0,0);
                    return true;
            }
            return true;
        });
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}