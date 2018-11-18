package com.example.admin.assignment1.TodoApp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.admin.assignment1.R;

import java.util.Calendar;
import java.util.List;

public class TodoActivity extends AppCompatActivity {

    RecyclerView taskView;
    LinearLayoutManager linearLayoutManager;
    List<Task> allTasks;
    TaskAdapter adapter;
    Button addButton;

    private static final String TAG = TodoActivity.class.getSimpleName();
    private final String NEW_TASK_STATUS = "new";
    public static TodoSQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);

        taskView = (RecyclerView) findViewById(R.id.task_list);
        linearLayoutManager = new LinearLayoutManager(this);
        taskView.setLayoutManager(linearLayoutManager);
        taskView.setHasFixedSize(true);

        database = new TodoSQLiteDatabase(this);
        autoSendTask();

        allTasks = database.listTasks();

        if(allTasks.size() > 0) {
            taskView.setVisibility(View.VISIBLE);
            adapter = new TaskAdapter(this, allTasks);
            taskView.setAdapter(adapter);

        } else {
            taskView.setVisibility(View.GONE);
            Toast.makeText(this, "There is no task.", Toast.LENGTH_LONG).show();
        }

        addButton = (Button) findViewById(R.id.addTask);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTaskDialog();
            }
        });

    }

    public void autoSendTask(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 4);

        Intent intent = new Intent(TodoActivity.this, TaskReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    private void addTaskDialog(){
        LayoutInflater inflater = LayoutInflater.from(this);
        View subView = inflater.inflate(R.layout.add_task_layout, null);
        final EditText nameField = (EditText) subView.findViewById(R.id.enter_name);
        final EditText timeField = (EditText) subView.findViewById(R.id.enter_time);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Task");
        builder.setView(subView);
        builder.create();
        builder.setPositiveButton("ADD TASK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String name = nameField.getText().toString();
                final String time = timeField.getText().toString();
                if(TextUtils.isEmpty(name) || TextUtils.isEmpty(time)){
                    Toast.makeText(TodoActivity.this, "Check your input.", Toast.LENGTH_LONG).show();
                } else {
                    Task newTask = new Task(name, time, NEW_TASK_STATUS);
                    database.addTask(newTask);
                    finish();
                    startActivity(getIntent());
                }
            }
        });
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(TodoActivity.this, "Task cancelled", Toast.LENGTH_LONG).show();
            }
        });
        builder.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(database != null){
            database.close();
        }
    }
}
