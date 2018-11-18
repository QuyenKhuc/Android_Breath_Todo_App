package com.example.admin.assignment1.BreathApp;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.PersistableBundle;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;

import com.example.admin.assignment1.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class BreathPracticeActivity extends AppCompatActivity {
    private Button startMotion, stopMotion;
    private GifDrawable gifDrawable;
    private Chronometer chronometer;

    private long lastPause;
    private int timeDuration;
    private int lastDailyTime = 0;
    private int lastWeeklyTime = 0;
    private int lastMonthlyTime = 0;

    private String currentDate;
    private String currentDay;
    private String currentWeek;
    private String currentMonth;
    private String currentYear;

    private String lastDate;
    private String lastWeek;
    private String lastMonth;
    private String lastYear;

    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;

    private Boolean isOnSaveData = null;
    public static Boolean isClicked = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breath_practice);

        chronometer = findViewById(R.id.chronometer);
        GifImageView breathMotion = findViewById(R.id.breath_motion);
        startMotion = findViewById(R.id.start);
        stopMotion = findViewById(R.id.stop);
        gifDrawable = (GifDrawable) breathMotion.getDrawable();
        gifDrawable.stop();

        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mPreferences.edit();

    }

    private boolean isFirstTime() {
        if (isOnSaveData == null) {
            SharedPreferences firstTimePreference = PreferenceManager.getDefaultSharedPreferences(this);
            isOnSaveData = mPreferences.getBoolean("firstTime", true);
            if (isOnSaveData) {
                Boolean isFirstStop = false;
                mEditor.putBoolean(getString(R.string.isFirstStop), isFirstStop);
                mEditor.apply();
                getCurrentDate();
                saveDatePreference();
                SharedPreferences.Editor editor = firstTimePreference.edit();
                editor.putBoolean("firstTime", false);
                editor.apply();
            }
        }
        return isOnSaveData;
    }


    public void startMotionClicked(View view){
        gifDrawable.start();
        if(lastPause != 0) {
            chronometer.setBase(chronometer.getBase() + SystemClock.elapsedRealtime() - lastPause);
        } else {
            chronometer.setBase(SystemClock.elapsedRealtime());
        }
        chronometer.start();
        startMotion.setEnabled(false);
        stopMotion.setEnabled(true);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void stopMotionClicked(View view){
        gifDrawable.stop();
        //lastPause = SystemClock.elapsedRealtime();
        lastPause = 0;
        chronometer.stop();
        stopMotion.setEnabled(false);
        startMotion.setEnabled(true);
        timeDuration = (int)((SystemClock.elapsedRealtime() - chronometer.getBase()) / 1000);
        Log.v(""+timeDuration, "time");
        isFirstTime();
        getPreference();
        getCurrentDate();
        Boolean isFirstStop = mPreferences.getBoolean(getString(R.string.isFirstStop), true);
        if(!isFirstStop){
            timeCalculation();
        }

    }



    public void getCurrentDate(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy:MM:dd");
        currentDate = String.valueOf(dateFormat.format(calendar.getTime()));

        currentDay = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
        currentWeek = String.valueOf(calendar.get(Calendar.WEEK_OF_MONTH));
        currentMonth = String.valueOf(calendar.get(Calendar.MONTH));
        currentYear = String.valueOf(calendar.get(Calendar.YEAR));
    }

    public void saveDatePreference(){
        mEditor.putString(getString(R.string.date), currentDate);
        mEditor.putString(getString(R.string.week), currentWeek);
        mEditor.putString(getString(R.string.month), currentMonth);
        mEditor.putString(getString(R.string.year), currentYear);
        mEditor.apply();
    }

    public void saveTimePreference(){
        lastDailyTime += timeDuration;
        mEditor.putInt(getString(R.string.totalDaily), lastDailyTime);
        mEditor.apply();

        lastWeeklyTime += timeDuration;
        mEditor.putInt(getString(R.string.totalWeekly), lastWeeklyTime);
        mEditor.apply();

        lastMonthlyTime += timeDuration;
        mEditor.putInt(getString(R.string.totalMonthly), lastMonthlyTime);
        mEditor.apply();
    }

    public void getPreference(){
        lastDate = mPreferences.getString(getString(R.string.date), "");
        lastWeek = mPreferences.getString(getString(R.string.week), "");
        lastMonth = mPreferences.getString(getString(R.string.month), "");
        lastYear = mPreferences.getString(getString(R.string.year), "");
        lastDailyTime = mPreferences.getInt(getString(R.string.totalDaily), 0);
        lastWeeklyTime = mPreferences.getInt(getString(R.string.totalWeekly), 0);
        lastMonthlyTime = mPreferences.getInt(getString(R.string.totalMonthly), 0);

        Log.v(""+lastDailyTime, "daily");
        Log.v(""+lastWeeklyTime, "week");
        Log.v(""+lastMonthlyTime, "month");
    }

    public void timeCalculation(){
        if(currentDay.equals("1")) {
            saveDatePreference();
            lastDailyTime = 0;
            lastWeeklyTime = 0;
            lastMonthlyTime = 0;
            saveTimePreference();

        } else if(currentDate.equals(lastDate)){
            saveTimePreference();

        } else if(!currentDate.equals(lastDate) && currentWeek.equals(lastWeek) &&
                currentMonth.equals(lastMonth) && currentYear.equals(lastYear)) {
            saveDatePreference();
            lastDailyTime = 0;
            saveTimePreference();

        } else if(!currentDate.equals(lastDate) && !currentWeek.equals(lastWeek) &&
                currentMonth.equals(lastMonth) && currentYear.equals(lastYear)) {
            saveDatePreference();
            lastDailyTime = 0;
            lastWeeklyTime = 0;
            saveTimePreference();
        }


    }

    @Override
    public void onBackPressed() {
        AlertDialog show = new AlertDialog.Builder(this)
                .setTitle("Stop Practice")
                .setMessage("Are you sure you want to exit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        BreathPracticeActivity.super.onBackPressed();
                        getPreference();
                        Intent intent = new Intent(BreathPracticeActivity.this, BreathActivity.class);
                        intent.putExtra("daily", lastDailyTime);
                        intent.putExtra("weekly", lastWeeklyTime);
                        intent.putExtra("monthly", lastMonthlyTime);
                        startActivity(intent);
                        isClicked = true;
                        finish();
                    }

                })
                .setNegativeButton("No", null)
                .show();

    }

}