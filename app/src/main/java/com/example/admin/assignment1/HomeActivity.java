package com.example.admin.assignment1;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;

import com.example.admin.assignment1.BreathApp.BreathActivity;
import com.example.admin.assignment1.TodoApp.TodoActivity;

public class HomeActivity extends AppCompatActivity {
    Button todo_button, breath_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        todo_button = findViewById(R.id.todo_button);
        breath_button = findViewById(R.id.breath_button);

    }

    public void todoButtonClicked(View view){
        Intent intent = new Intent(HomeActivity.this, TodoActivity.class);
        startActivity(intent);
    }

    public void breathButtonClicked(View view) {
        Intent intent = new Intent(HomeActivity.this, BreathActivity.class);
        startActivity(intent);
    }
}
