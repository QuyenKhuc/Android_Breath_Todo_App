

package com.example.admin.assignment1.BreathApp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.assignment1.HomeActivity;
import com.example.admin.assignment1.R;

import java.util.Objects;

public class BreathActivity extends AppCompatActivity {
    Button practice;
    TextView dailyTime;
    TextView weeklyTime;
    TextView monthlyTime;
    private SharedPreferences mPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breath);

        dailyTime = findViewById(R.id.daily_time_value);
        weeklyTime = findViewById(R.id.weekly_time_value);
        monthlyTime = findViewById(R.id.monthly_time_value);
        practice = findViewById(R.id.practice_button);

        Toast.makeText(BreathActivity.this, "Let's Practice", Toast.LENGTH_SHORT).show();

        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor mEditor = mPreferences.edit();

        if(BreathPracticeActivity.isClicked){
            Intent intent = getIntent();
            Bundle extras = intent.getExtras();
            if(extras != null) {
                int daily = intent.getExtras().getInt("daily");
                int weekly = intent.getExtras().getInt("weekly");
                int monthly = intent.getExtras().getInt("monthly");

                mEditor.putInt(getString(R.string.totalDailyDisplay), daily);
                mEditor.putInt(getString(R.string.totalWeeklyDisplay), weekly);
                mEditor.putInt(getString(R.string.totalMonthlyDisplay), monthly);
                mEditor.apply();
            } else {
                Log.d("No Data", "No data send by intent");
            }
        }

        getPreferenceAndDisplay();
    }

    @SuppressLint("SetTextI18n")
    public void getPreferenceAndDisplay(){
        int daily = mPreferences.getInt(getString(R.string.totalDailyDisplay), 0);
        int weekly = mPreferences.getInt(getString(R.string.totalWeeklyDisplay), 0);
        int monthly = mPreferences.getInt(getString(R.string.totalMonthlyDisplay), 0);

        dailyTime.setText(String.valueOf(daily) + "s");
        weeklyTime.setText(String.valueOf(weekly) + "s");
        monthlyTime.setText(String.valueOf(monthly) + "s");
    }

    public void breathPracticeClicked(View view){
        Intent intent = new Intent(BreathActivity.this, BreathPracticeActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(BreathActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}
