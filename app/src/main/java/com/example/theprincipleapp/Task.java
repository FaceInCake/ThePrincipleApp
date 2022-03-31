package com.example.theprincipleapp;

import com.example.theprincipleapp.helpers.TaskTypeEnum;

import java.io.Serializable;
import java.util.Calendar;

public class Task implements Serializable {
    private String name;
    private String location;
    private String description;
    private TaskTypeEnum taskType;
    private Calendar openDate;
    private Calendar dueDate;
    private boolean isFinished;


    public Task(String name, TaskTypeEnum taskType, String description, String location, Calendar openDate, Calendar dueDate){
        this.name = name;
        this.taskType = taskType;
        this.description = description;
        this.location = location;
        this.openDate = openDate;
        this.dueDate = dueDate;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskTypeEnum getTaskType() {
        return taskType;
    }

    public void setTaskType(TaskTypeEnum taskType) {
        this.taskType = taskType;
    }

    public Calendar getOpenDate() {
        return openDate;
    }

    public void setOpenDate(Calendar openDate) {
        this.openDate = openDate;
    }

    public Calendar getDueDate() {
        return dueDate;
    }

    public void setDueDate(Calendar dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }
}
