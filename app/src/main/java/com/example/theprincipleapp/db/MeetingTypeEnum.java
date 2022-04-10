package com.example.theprincipleapp.db;

import androidx.annotation.NonNull;

public enum MeetingTypeEnum {
    LECTURE("Lecture"),
    LAB("Lab");

    private final String meetingName;

    MeetingTypeEnum(String meetingName){
        this.meetingName = meetingName;
    }

    @NonNull
    @Override
    public String toString(){
        return meetingName;
    }

}
