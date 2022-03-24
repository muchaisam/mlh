package com.example.mlh.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mlh.MainActivity;
import com.example.mlh.R;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    private TextView next;
    private EditText mobiledigit;
    private FirebaseAuth auth;

    //Constant keys for shared preferences
    public final String SHARED_PREFS = "shared_prefs";

    //storing phone
    public final String PHONE_KEY = "phone_key";

    SharedPreferences sharedPreferences;
    String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() !=null) {
            startActivity(new Intent(RegisterActivity.this, MainActivity.class));
        }
//         else {
//            Log.d(TAG, "onAuthStateChanged:signed_in");
//            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            startActivity(intent);
//            finish();
//        }

        mobiledigit = findViewById(R.id.mobiledigit);

        next = findViewById(R.id.next);
        next.setOnClickListener(v -> {
            String mobile = mobiledigit.getText().toString().trim();

            if (mobile.isEmpty() || mobile.length() <10){
                mobiledigit.setError("Kindly enter your mobile number");
                mobiledigit.requestFocus();
                return;
            }

            Intent intent = new Intent(RegisterActivity.this, MobileVerification.class);
            intent.putExtra("mobile", mobile);
            startActivity(intent);
        });


    }
}