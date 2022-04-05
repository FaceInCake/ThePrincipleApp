package com.example.theprincipleapp.db;

import androidx.annotation.NonNull;

public enum TaskTypeEnum {
    DEFAULT("» Select Task Type «"),
    ASSIGNMENT("Assignment"),
    QUIZ("Quiz"),
    TEST("Test"),
    EXAM("Exam"),
    PROJECT("Project");

    private final String friendlyName;

    TaskTypeEnum(String friendlyName){
        this.friendlyName = friendlyName;
    }

    @NonNull
    @Override
    public String toString(){
        return friendlyName;
    }
}
