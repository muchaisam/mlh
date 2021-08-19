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

public class TransactionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);


        //initialize and assigning the variable
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        //set home selected
        bottomNavigationView.setSelectedItemId(R.id.transactions);
        //listeners
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.transactions:
                        return true;
                    case R.id.home:
                        Toast.makeText(TransactionsActivity.this, "Home", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.payment:
                        Toast.makeText(TransactionsActivity.this, "Payment", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent (getApplicationContext(), PaymentActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.accountt:
                        Toast.makeText(TransactionsActivity.this, "Account", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent (getApplicationContext(), AccountActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }
}