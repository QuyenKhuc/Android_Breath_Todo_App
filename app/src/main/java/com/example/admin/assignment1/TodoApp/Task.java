package com.example.admin.assignment1.TodoApp;

public class Task {
    private int id;
    private String name;
    private String time;
    private String status;

    public Task(int id, String name, String time){
        this.id = id;
        this.name = name;
        this.time = time;
    }

    public Task(String name, String time, String status){
        this.name = name;
        this.time = time;
        this.status = status;
    }

    public Task(int id, String name, String time, String status){
        this.id = id;
        this.name = name;
        this.time = time;
        this.status = status;
    }

    public Task(int id, String status){
        this.id = id;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
