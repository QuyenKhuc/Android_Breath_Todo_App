package com.example.admin.assignment1.TodoApp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.admin.assignment1.TodoApp.Task;

import java.util.ArrayList;
import java.util.List;

public class TodoSQLiteDatabase extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "task";
    private static final String TABLE_TASKS = "tasks";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_TIME = "time";
    private static final String COLUMN_STATUS = "status";

    public TodoSQLiteDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TASKS_TABLE = "CREATE TABLE " + TABLE_TASKS + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
        + COLUMN_NAME + " TEXT," + COLUMN_TIME + " TEXT," + COLUMN_STATUS + " TEXT" + ")";

        db.execSQL(CREATE_TASKS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);
        onCreate(db);
    }

    public List<Task> listTasks(){
        String sql = "SELECT * FROM " + TABLE_TASKS;
        SQLiteDatabase db = this.getWritableDatabase();
        List<Task> storeTasks = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst()) {
            do {
                int id = Integer.parseInt(cursor.getString(0));
                String name = cursor.getString(1);
                String time = cursor.getString(2);
                String status = cursor.getString(3);
                storeTasks.add(new Task(id, name, time, status));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return storeTasks;
    }

    public void addTask(Task task){
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, task.getName());
        contentValues.put(COLUMN_TIME, task.getTime());
        contentValues.put(COLUMN_STATUS, task.getStatus());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_TASKS, null, contentValues);
    }

    public void updateTask(Task task){
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, task.getName());
        contentValues.put(COLUMN_TIME, task.getTime());
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(TABLE_TASKS, contentValues, COLUMN_ID + " = ?", new String[] {
                String.valueOf(task.getId())
        });
    }

    public void updateStatus(Task task){
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_STATUS, task.getStatus());
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(TABLE_TASKS, contentValues, COLUMN_ID + " = ?", new String[] {
                String.valueOf(task.getId())
        });
    }

    public void deleteTask(int task){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TASKS, COLUMN_ID + " = ?", new String[] {
                String.valueOf(task)
        });
    }





}
