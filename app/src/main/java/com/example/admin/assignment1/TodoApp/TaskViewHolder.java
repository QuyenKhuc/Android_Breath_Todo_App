package com.example.admin.assignment1.TodoApp;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.assignment1.R;


public class TaskViewHolder extends RecyclerView.ViewHolder {
    public TextView name;
    public TextView time;
    public ImageView editTask;
    public ImageView deleteTask;
    public CheckBox checkBox;

    public TaskViewHolder(@NonNull View itemView) {
        super(itemView);
        name = (TextView) itemView.findViewById(R.id.task_name);
        time = (TextView) itemView.findViewById(R.id.task_time);
        editTask = (ImageView) itemView.findViewById(R.id.edit_task);
        deleteTask = (ImageView) itemView.findViewById(R.id.delete_task);
        checkBox = (CheckBox) itemView.findViewById(R.id.checkbox);
    }
}
