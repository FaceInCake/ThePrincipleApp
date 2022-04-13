package com.example.theprincipleapp.db;

import androidx.annotation.NonNull;
import com.example.theprincipleapp.helpers.Util;


public enum MeetingTypeEnum {
    LECTURE,
    LAB;

    @NonNull
    @Override
    public String toString(){
        return Util.toTitleCase(this.name());
    }

}
