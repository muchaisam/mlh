package com.example.mlh.screens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.mlh.MainActivity;
import com.example.mlh.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        //initialize and assigning the variable
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        //set home selected
        bottomNavigationView.setSelectedItemId(R.id.accountt);
        //listeners
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.accountt:
                        return true;
                    case R.id.home:
                        Toast.makeText(AccountActivity.this, "Home", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.transactions:
                        Toast.makeText(AccountActivity.this, "Transactions", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent (getApplicationContext(), TransactionsActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.payment:
                        Toast.makeText(AccountActivity.this, "Payment", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent (getApplicationContext(), PaymentActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

    }
}