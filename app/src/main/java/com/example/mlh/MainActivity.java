package com.example.mlh;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.mlh.screens.AccountFragment;
import com.example.mlh.screens.HomeFragment;
import com.example.mlh.screens.PaymentFragment;
import com.example.mlh.screens.TransactionsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private TextView greetings;
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

        //initializing shared prefs
        sharedPreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        //getting data and storing it
        username = sharedPreferences.getString(USER_NAME, null);


        greetings =(TextView)findViewById(R.id.greetings);
        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

        if(timeOfDay >= 0 && timeOfDay < 12){
            greetings.setText("Good Morning " + username);
        }else if(timeOfDay >= 12 && timeOfDay < 16){
            greetings.setText("Good Afternoon " + username);
        }else if(timeOfDay >= 16 && timeOfDay < 24){
            greetings.setText("Good Evening " + username);
        }

        initViews();
        loadFragment(new HomeFragment());
    }

    private void initViews() {
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);
    }

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }



    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Fragment fragment = null;

        switch (item.getItemId()) {
            case R.id.homef:
                fragment = new HomeFragment();
                break;
            case R.id.payment_pg:
                fragment = new PaymentFragment();
                break;
            case R.id.account_pg:
                fragment = new AccountFragment();
                break;
            case R.id.transactions:
                fragment = new TransactionsFragment();
                break;
        }
        return loadFragment(fragment);
    }
}