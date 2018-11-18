package com.example.admin.assignment1.TodoApp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class TaskReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String NEW_TASK_STATUS = "new";

        String TASK_1_NAME = "mindfulness breathing";
        String TASK_1_TIME = "15-min";
        Task task1 = new Task(TASK_1_NAME, TASK_1_TIME, NEW_TASK_STATUS);

        String TASK_2_NAME = "bedtime retrospection";
        String TASK_2_TIME = "15-min";
        Task task2 = new Task(TASK_2_NAME, TASK_2_TIME, NEW_TASK_STATUS);

        if(TodoActivity.database.listTasks().contains(task1)) {
            Log.d("Done", "Already has task 1");
        } else {
            //Log.d("check", ""+ TodoActivity.database.listTasks().contains(task1));
            TodoActivity.database.addTask(task1);

        }

        if(TodoActivity.database.listTasks().contains(task2)) {
            Log.d("Done", "Already has task 2");
        } else {
            //Log.d("check", ""+ TodoActivity.database.listTasks().contains(task2));
            TodoActivity.database.addTask(task2);
        }

        Log.d("MyAlarm", "Alarm just fired");

    }
}
