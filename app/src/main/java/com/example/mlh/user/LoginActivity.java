package com.example.mlh.user;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mlh.MainActivity;
import com.example.mlh.R;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

public class LoginActivity extends AppCompatActivity {

    MaterialButton tomain;
    EditText input_email;
    EditText input_password;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        overridePendingTransition(R.anim.abc_fade_in,R.anim.abc_fade_out);

        firebaseAuth = FirebaseAuth.getInstance();

        input_email =  findViewById(R.id.emailinput);
        input_password = findViewById(R.id.passcodeview);
        progressDialog = new ProgressDialog(this);

        tomain = findViewById(R.id.tomain);
        tomain.setOnClickListener(view -> userLogin());
    }

    private void userLogin(){
        String email = input_email.getText().toString().trim();
        String password  = input_password.getText().toString().trim();

        // If email is empty, return
        if (TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please enter a valid email",Toast.LENGTH_LONG).show();
            return;
        }

        // If email is empty, return
        if (TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please enter password",Toast.LENGTH_LONG).show();
            return;
        }

        progressDialog.setMessage("Hang on as we log you in.....");
        progressDialog.show();

        // Sign in with email and password
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    progressDialog.dismiss();
                    if (task.isSuccessful()){
                        // If email is not verified, verify
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        if (!user.isEmailVerified()){
                            Toast.makeText(LoginActivity.this, "Please Verify your email first.",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            // start main activity
                            finish();
                            FancyToast.makeText(LoginActivity.this, "Welcome to Cleansafi. Place your order now.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }
                    }
                    else {
                        // Failed to log in
                        FancyToast.makeText(LoginActivity.this, "Authentication failed.Please Try again",Toast.LENGTH_SHORT).show();
                    }
                });
    }

}