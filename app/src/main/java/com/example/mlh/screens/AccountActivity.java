package com.example.mlh.screens;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mlh.MainActivity;
import com.example.mlh.R;
import com.example.mlh.databinding.ActivityAccountBinding;
import com.example.mlh.model.AlarmReceiver;
import com.example.mlh.user.LoggingActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.Calendar;

public class AccountActivity extends AppCompatActivity {

    private ActivityAccountBinding binding;
    private MaterialTimePicker picker;
    private Calendar calendar;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private TextView thename, themail, phone;
    String firstname, lastname, useremail, usermobilenumber;
    SharedPreferences sharedPreferences;
    public final String SHARED_PREFS = "shared_prefs";
    public final String FIRST_NAME = "first_name";
    public final String LAST_NAME = "last_name";
    public final String USER_EMAIL = "user_email";
    public final String USER_PHONE = "user_mobile";
    Button logout;
    FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        firebaseDatabase = FirebaseDatabase.getInstance();

        //initializing shared prefs
        sharedPreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        //getting data and storing it
        firstname = sharedPreferences.getString(FIRST_NAME, null);
        lastname = sharedPreferences.getString(LAST_NAME, null);
        useremail = sharedPreferences.getString(USER_EMAIL, null);
        usermobilenumber = sharedPreferences.getString(USER_PHONE, null);

        //setting user data
        thename = findViewById(R.id.thename);
        themail = findViewById(R.id.themail);
        phone = findViewById(R.id.phone);

        thename.setText(firstname + " " +lastname);
        themail.setText(useremail);
        phone.setText(usermobilenumber);

        logout = findViewById(R.id.btnlog);
        logout.setOnClickListener(view -> {
            FancyToast.makeText(AccountActivity.this, "You have been logged out", FancyToast.SUCCESS, FancyToast.LENGTH_LONG, false).show();
            logout();
        });

        //channel
        createNotificationChannel();

        binding = ActivityAccountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //onclick listeners for the three buttons
        binding.timevieew.setOnClickListener (v -> showTimePicker());

        binding.setreminder.setOnClickListener(v -> setAlarm());

        binding.cancelreminder.setOnClickListener(v -> cancelAlarm());


        //initialize and assigning the variable
        BottomNavigationView bottomNavigationView =  findViewById(R.id.bottom_navigation);
        //set home selected
        bottomNavigationView.setSelectedItemId(R.id.accountt);
        //listeners
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.accountt:
                    return true;
                case R.id.home:
                    Toast.makeText(AccountActivity.this, "Home", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    overridePendingTransition(0,0);
                    return true;
                case R.id.payment:
                    Toast.makeText(AccountActivity.this, "Payment", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent (getApplicationContext(), PaymentActivity.class));
                    overridePendingTransition(0,0);
                    return true;
            }
            return false;
        });

    }

    private void cancelAlarm() {

        Intent intent = new Intent(this, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

        if (alarmManager == null){
            alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        }

        alarmManager.cancel(pendingIntent);
        FancyToast.makeText(this, "Reminder cancelled successfully", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
    }

    private void setAlarm() {

        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(this, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, pendingIntent);

        FancyToast.makeText(this, "Reminder set successfully", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
    }

    private void showTimePicker() {

        picker = new MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_12H)
                .setHour(12)
                .setMinute(0)
                .setTitleText("Set Reminder Time")
                .build();

        picker.show(getSupportFragmentManager(), "SecureLine");

        picker.addOnPositiveButtonClickListener(v -> {

            if (picker.getHour() > 12){
                binding.timevieew.setText(
                        String.format("%02d",(picker.getHour()-12))+ ":" +String.format("%02d", picker.getMinute()) + "PM"
                );
            }else {
                binding.timevieew.setText(picker.getHour() + ":" + picker.getMinute() + "AM");
            }

            calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, picker.getHour());
            calendar.set(Calendar.MINUTE, picker.getMinute());
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
        });
    }

    private void createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "SecureLineReminderChannel";
            String description = "Channel for Alarm Manager";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("SecureLine", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

        }
    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(AccountActivity.this, LoggingActivity.class));
    }
}