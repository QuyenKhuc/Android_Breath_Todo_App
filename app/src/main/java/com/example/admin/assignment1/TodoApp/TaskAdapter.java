package com.example.admin.assignment1.TodoApp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.admin.assignment1.R;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskViewHolder> {
    private Context context;
    private List<Task> listTasks;
    private TodoSQLiteDatabase database;
    private final String DONE_TASK_STATUS = "done";
    private final String NEW_TASK_STATUS = "new";

    public TaskAdapter(Context context, List<Task> listTasks) {
        this.context = context;
        this.listTasks = listTasks;
        database = new TodoSQLiteDatabase(context);
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_layout, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final TaskViewHolder holder, int position) {
        final Task singleTask = listTasks.get(position);
        holder.name.setText(singleTask.getName());
        holder.time.setText(singleTask.getTime());

        holder.editTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTaskDialog(singleTask);
            }
        });

        holder.deleteTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database.deleteTask(singleTask.getId());
                ((Activity)context).finish();
                context.startActivity(((Activity)context).getIntent());
            }
        });

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.checkBox.isChecked()){
                    database.updateStatus(new Task(singleTask.getId(), DONE_TASK_STATUS));
                    Toast.makeText(context, "Task Done", Toast.LENGTH_LONG).show();
                } else {
                    database.updateStatus(new Task(singleTask.getId(), NEW_TASK_STATUS));
                }
            }
        });

        if(singleTask.getStatus().equals(DONE_TASK_STATUS)){
            holder.checkBox.setChecked(true);
        } else {
            holder.checkBox.setChecked(false);
        }

    }

    @Override
    public int getItemCount() {
        return listTasks.size();
    }


    private void editTaskDialog(final Task task){
        LayoutInflater inflater = LayoutInflater.from(context);
        View subView = inflater.inflate(R.layout.add_task_layout, null);
        final EditText nameField = (EditText) subView.findViewById(R.id.enter_name);
        final EditText timeField = (EditText) subView.findViewById(R.id.enter_time);

        if(task != null){
            nameField.setText(task.getName());
            timeField.setText(task.getTime());
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Edit Task");
        builder.setView(subView);
        builder.create();
        builder.setPositiveButton("EDIT TASK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String name = nameField.getText().toString();
                final String time = timeField.getText().toString();
                if(TextUtils.isEmpty(name) || TextUtils.isEmpty(time)){
                    Toast.makeText(context, "Check your input value", Toast.LENGTH_LONG).show();
                } else {
                    database.updateTask(new Task(task.getId(), name, time));
                    ((Activity)context).finish();
                    context.startActivity(((Activity)context).getIntent());
                }
            }
        });
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(context, "Task cancelled", Toast.LENGTH_LONG).show();
            }
        });
        builder.show();
    }


}
