package com.example.mlh.screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.mlh.MainActivity;
import com.example.mlh.R;
import com.example.mlh.fragment.DatePickerFrag;
import com.example.mlh.fragment.TimePickerFrag;
import com.example.mlh.model.AlarmReceiver;
import com.example.mlh.model.NotificationHelper;
import com.example.mlh.user.LoggingActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AccountActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {


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

    Button DatePick, TimePick, SetAlarm;
    TextView showT, showD;
    Calendar set = Calendar.getInstance();
    StringBuilder sb = new StringBuilder();
    private NotificationHelper mNotificationHelper;
    SimpleDateFormat dateFormat, timeFormat;
    String time, calendarDate;

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
        InitialzeMethod();
        DatePick.setOnClickListener(view -> {
            DialogFragment datePicker = new DatePickerFrag(); //Pop up Date Picker Dialog
            datePicker.show(getSupportFragmentManager(), "date picker");
        });

        TimePick.setOnClickListener(view -> {
            DialogFragment timePicker = new TimePickerFrag(); //Pop up Time Picker Dialog
            timePicker.show(getSupportFragmentManager(), "time picker");
        });

    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(AccountActivity.this, LoggingActivity.class));
    }

    private void InitialzeMethod() {

        DatePick = findViewById(R.id.DatePick);
        showD = findViewById(R.id.DateView);
        TimePick = findViewById(R.id.TimePick);
        showT = findViewById(R.id.TimeView);
        SetAlarm = findViewById(R.id.SetAlarm);

        Intent intent = getIntent();
        Bundle extras = getIntent().getExtras();
        String GoalName = intent.getExtras().getString("GoalName"); //Fetching Goal Name from Intent


        mNotificationHelper = new NotificationHelper(this, GoalName); //initializing NotificationHelper Class with Parameters

    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        set.set(Calendar.YEAR, i); //Set all data in Global Variable Calendar instance
        set.set(Calendar.MONTH, i1);
        set.set(Calendar.DATE, i2);
        dateFormat = new SimpleDateFormat("dd-MM-yyyy"); //Date Format
        calendarDate = dateFormat.format(set.getTime());
        showD.setText(calendarDate); //setting TextView
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        set.set(Calendar.HOUR_OF_DAY, i); //Set all data in Global Variable Calendar instance
        set.set(Calendar.MINUTE, i1);
        timeFormat = new SimpleDateFormat("HH-mm aa"); //Time Format
        time = timeFormat.format(set.getTime());
        showT.setText(time); //Setting TextView
    }


    public void SetAlarmMan(View view) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE); //Creating Alarm Manager
        Intent intent = new Intent(this, AlarmReceiver.class);
        intent.putExtra("goal", sb.toString()); //passing Goal name
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0); //pending intent
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, set.getTimeInMillis(), pendingIntent); //wakes device if sleep
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(AccountActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}