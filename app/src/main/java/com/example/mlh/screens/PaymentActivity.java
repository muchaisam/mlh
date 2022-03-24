package com.example.mlh.screens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mlh.MainActivity;
import com.example.mlh.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;


public class PaymentActivity extends AppCompatActivity implements View.OnClickListener {

    private ProgressDialog mProgressDialog;

    @BindView(R.id.etAmount)
    EditText mAmount;
    @BindView(R.id.etPhone)EditText mPhone;
    @BindView(R.id.btnPay)
    Button mPay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        ButterKnife.bind(this);

        mProgressDialog = new ProgressDialog(this);

        mPay.setOnClickListener(this);

        //initialize and assigning the variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        //set home selected
        bottomNavigationView.setSelectedItemId(R.id.payment);
        //listeners
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.payment:
                    return true;
                case R.id.home:
                    Toast.makeText(PaymentActivity.this, "Home", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    overridePendingTransition(0,0);
                    return true;
                case R.id.accountt:
                    Toast.makeText(PaymentActivity.this, "Account", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent (getApplicationContext(), AccountActivity.class));
                    overridePendingTransition(0,0);
                    return true;
                case R.id.transactions:
                    Toast.makeText(PaymentActivity.this, "Transactions", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent (getApplicationContext(), TransactionsActivity.class));
                    overridePendingTransition(0,0);
                    return true;
            }
            return true;
        });

    }

    @Override
    public void onClick(View view) {

    }
}