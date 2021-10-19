package com.example.mlh.screens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.mlh.MainActivity;
import com.example.mlh.R;
import com.example.mlh.databinding.ActivityAccountBinding;
import com.example.mlh.model.AlarmReceiver;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.util.Calendar;

public class AccountActivity extends AppCompatActivity {

    private ActivityAccountBinding binding;
    private MaterialTimePicker picker;
    private Calendar calendar;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

        //channel
        createNotificationChannel();

        binding = ActivityAccountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //onclick listeners for the three buttons
        binding.timevieew.setOnClickListener (new View.OnClickListener(){
            @Override
            public void onClick(View v){

                showTimePicker();
            }
        });

        binding.setreminder.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v){

                setAlarm();
            }
        });

        binding.cancelreminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cancelAlarm();
            }
        });


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

    private void cancelAlarm() {

        Intent intent = new Intent(this, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

        if (alarmManager == null){
            alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        }

        alarmManager.cancel(pendingIntent);
        Toast.makeText(this, "Reminder cancelled successfully", Toast.LENGTH_SHORT).show();
    }

    private void setAlarm() {

        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(this, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, pendingIntent);

        Toast.makeText(this, "Reminder set successfully", Toast.LENGTH_SHORT).show();
    }

    private void showTimePicker() {

        picker = new MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_12H)
                .setHour(12)
                .setMinute(0)
                .setTitleText("Set Reminder Time")
                .build();

        picker.show(getSupportFragmentManager(), "SecureLine");

        picker.addOnPositiveButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
            }
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
}