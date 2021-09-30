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
import com.example.mlh.screens.TransactionsActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private TextView greetings;
    TextView transfer, request;
    ImageView userinfo;
    //Constant keys for shared preferences
    public final String SHARED_PREFS = "shared_prefs";

    //storing phone
    public final String USER_NAME = "user_name";

    SharedPreferences sharedPreferences;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

        //initializing shared prefs
        sharedPreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        //getting data and storing it
        username = sharedPreferences.getString(USER_NAME, null);

        //on click listeners
        transfer = (TextView)findViewById(R.id.transfer);
        request = (TextView)findViewById(R.id.request);
        userinfo = (ImageView)findViewById(R.id.userinfo);

        greetings =(TextView)findViewById(R.id.greetings);
        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

        if(timeOfDay >= 0 && timeOfDay < 12){
            greetings.setText("Good Morning, " + username);
        }else if(timeOfDay >= 12 && timeOfDay < 16){
            greetings.setText("Good Afternoon, " + username);
        }else if(timeOfDay >= 16 && timeOfDay < 24){
            greetings.setText("Good Evening, " + username);
        }

        //initialize and assigning the variable
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        //set home selected
        bottomNavigationView.setSelectedItemId(R.id.home);
        //listeners
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
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
                    case R.id.transactions:
                        Toast.makeText(MainActivity.this, "Transactions", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent (getApplicationContext(), TransactionsActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return true;
            }
        });
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}