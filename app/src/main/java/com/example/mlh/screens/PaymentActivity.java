package com.example.mlh.screens;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.mlh.MainActivity;
import com.example.mlh.R;
import com.flutterwave.raveandroid.RavePayActivity;
import com.flutterwave.raveandroid.RaveUiManager;
import com.flutterwave.raveandroid.rave_java_commons.RaveConstants;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.shashank.sony.fancytoastlib.FancyToast;

import butterknife.BindView;
import butterknife.ButterKnife;


public class PaymentActivity extends AppCompatActivity implements View.OnClickListener {

    private ProgressDialog mProgressDialog;
    String firstname, lastname, useremail, usermobilenumber, totalprice, orderid;
    SharedPreferences sharedPreferences;
    public final String SHARED_PREFS = "shared_prefs";
    public final String FIRST_NAME = "first_name";
    public final String LAST_NAME = "last_name";
    public final String USER_EMAIL = "user_email";
    public final String USER_PHONE = "user_mobile";
    public final String TOTAL_PRICE = "total_price";

    @BindView(R.id.btnPay)
    Button mPay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        ButterKnife.bind(this);

        //initializing shared prefs
        sharedPreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        //getting data and storing it
        firstname = sharedPreferences.getString(FIRST_NAME, null);
        lastname = sharedPreferences.getString(LAST_NAME, null);
        useremail = sharedPreferences.getString(USER_EMAIL, null);
        usermobilenumber = sharedPreferences.getString(USER_PHONE, null);

        mProgressDialog = new ProgressDialog(this);

        mPay.setOnClickListener(view -> makePayment());

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
            }
            return true;
        });

    }

    @Override
    public void onClick(View view) {

    }

    private void makePayment() {
        new RaveUiManager(this)
                .setAmount(Double.parseDouble("20"))
                .withTheme(R.style.MyCustomTheme)
                .setEmail(useremail)
                .setCountry("KE")
                .setCurrency("KES")
                .setfName(firstname)
                .setlName(lastname)
                .setNarration("Purchase Goods")
                //Fluuterwave provides  a public and encryption key on "https://dashboard.flutterwave.com"
                .setPublicKey("FFFFFFFFFFFFFFFFFFFF")
                .setEncryptionKey("SDFGHJKJHGFDFGHJHGFDFGH")
                .setTxRef(System.currentTimeMillis() + "Ref")
                .acceptAccountPayments(true)
                .acceptCardPayments(true)
                .acceptMpesaPayments(true)
                .onStagingEnv(false)
                .shouldDisplayFee(true)
                .showStagingLabel(false)
                .initialize();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RaveConstants.RAVE_REQUEST_CODE && data != null) {
            String message = data.getStringExtra("response");
            if (resultCode == RavePayActivity.RESULT_SUCCESS) {
                FancyToast.makeText(this, "SUCCESS " + message, FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                new android.os.Handler().postDelayed(() -> {
                    Intent f = new Intent(PaymentActivity.this, MainActivity.class);
                    startActivity(f);
                    finish();
                },100);
            }
            else if (resultCode == RavePayActivity.RESULT_ERROR) {
                FancyToast.makeText(this, "ERROR " + message, FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
            }
            else if (resultCode == RavePayActivity.RESULT_CANCELLED) {
                FancyToast.makeText(this, "CANCELLED " + message, FancyToast.LENGTH_LONG, FancyToast.INFO, false).show();
            }
        }
    }
}
