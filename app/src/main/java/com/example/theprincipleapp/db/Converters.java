package com.example.theprincipleapp.db;

import androidx.room.TypeConverter;

import java.util.Date;

public class Converters {
    @TypeConverter
    public static Date unixToDate (Long value) {
        return value==null? null : new Date(value);
    }
    @TypeConverter
    public static Long dateToUnix (Date date) {
        return date==null? null : date.getTime();
    }
    @TypeConverter
    public static Weekdays flagsToSet (Byte b) {
        return b==null? null : new Weekdays(b);
    }
    @TypeConverter
    public static Byte setToFlags (Weekdays wds) {
        return wds==null? null : wds.getByte();
    }
}
