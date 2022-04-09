package com.example.mlh.user;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mlh.MainActivity;
import com.example.mlh.R;
import com.example.mlh.model.AppUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.Objects;

public class MoreInfo extends AppCompatActivity {

    private EditText first_name, last_name, user_email, user_passcode;
    private TextView user_mobile;
    ProgressBar progressBar;
    FirebaseAuth firebaseAuth;

    //shared preferences
    public final String SHARED_PREFS = "shared_prefs";
    //storing the phone
    public final String FIRST_NAME = "first_name";
    public final String LAST_NAME = "last_name";
    public final String USER_EMAIL = "user_email";
    public final String USER_PHONE = "user_mobile";

    SharedPreferences sharedPreferences;
    String firstname;
    String lastname;
    String useremail;
    String usermobilenumber;

    private final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moreinfo);

        firebaseAuth = FirebaseAuth.getInstance();
        first_name = findViewById(R.id.first_name);
        last_name = findViewById(R.id.last_name);
        user_mobile = findViewById(R.id.user_mobile);
        user_email = findViewById(R.id.user_email);
        user_passcode = findViewById(R.id.user_passcode);
        progressBar = findViewById(R.id.progressbar);
        TextView tv_updateuseremail = findViewById(R.id.tv_updateuserdetail);

        //storing data in shared prefs
        sharedPreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        firstname = sharedPreferences.getString(FIRST_NAME, null);
        lastname = sharedPreferences.getString(LAST_NAME, null);
        useremail = sharedPreferences.getString(USER_EMAIL, null);
        usermobilenumber = sharedPreferences.getString(USER_PHONE, null);

        tv_updateuseremail.setOnClickListener(view -> {

            registerUser();

            String firstname = first_name.getText().toString().trim();
            String lastname = last_name.getText().toString().trim();
            String useremail = user_email.getText().toString().trim();
            String usermobilenumber = user_mobile.getText().toString().trim();
            String userpasscode = user_passcode.getText().toString().trim();

            //check if vars are empty
            if (TextUtils.isEmpty(firstname)){
                FancyToast.makeText(MoreInfo.this, "Please Fill in your firstname", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
            }
            if (TextUtils.isEmpty(useremail)){
                FancyToast.makeText(MoreInfo.this, "Please fill in your email", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
            }

            //if email and name are not empty display progressbar
            progressBar.setVisibility(View.VISIBLE);

            AppUser appUser = new AppUser(firstname, lastname, useremail, usermobilenumber, userpasscode);

            FirebaseDatabase.getInstance()
                    .getReference("appusers")
                    .child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
                    .setValue(appUser)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()){
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            //putting values
                            editor.putString(FIRST_NAME, first_name.getText().toString());
                            editor.putString(LAST_NAME, last_name.getText().toString());
                            editor.putString(USER_EMAIL, user_email.getText().toString());
                            editor.putString(USER_PHONE, user_mobile.getText().toString());
                            //save the data
                            editor.apply();

                            progressBar.setVisibility(View.GONE);
                            FancyToast.makeText(MoreInfo.this, "Nice Work! Welcome board.", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                            startActivity(new Intent(MoreInfo.this, MainActivity.class));
                            finish();
                        }else {
                            progressBar.setVisibility(View.GONE);
                            FancyToast.makeText(MoreInfo.this, "Something went wrong. Please Try Again.", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                        }
                    });
        });
    }
    // Register user
    private void registerUser(){
        String email = user_email.getText().toString().trim();
        String password = user_passcode.getText().toString().trim();

        // If email is empty, please enter email
        if (TextUtils.isEmpty(email)){
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
            return;
        }

        // If password is empty, please enter password
        if (TextUtils.isEmpty(password)){
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
            return;
        }



        // Create user with email and password
        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()){
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        assert user != null;
                        user.sendEmailVerification().addOnCompleteListener(task1 -> {
                            if (task1.isSuccessful()) {
                                // Successfully registered user, please verify through user email
                                FancyToast.makeText(context, "Kindly also check your email for verification.",FancyToast.INFO, FancyToast.LENGTH_SHORT,false).show();
                            }
                            else{
                                FancyToast.makeText(context, Objects.requireNonNull(task1.getException()).getMessage() ,FancyToast.ERROR, FancyToast.LENGTH_SHORT,false).show();
                            }
                        });
                        finish();
                        // Redirect to login activity
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    }
                    else {
                        Toast.makeText(MoreInfo.this,"Could not register you ... Please try again",Toast.LENGTH_SHORT).show();
                    }
                });
    }
}