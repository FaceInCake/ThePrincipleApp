package com.example.theprincipleapp.db;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;
import java.util.Date;

@Database(
    entities = {Class.class, Course.class, Task.class, Meeting.class},
    exportSchema = true,
    version = 1
)
@TypeConverters({UserDatabase.Converters.class})
public abstract class UserDatabase extends RoomDatabase {
    /**
     * Singleton of the User Database object.
     */
    public static UserDatabase UDB = null;

    /**
     * Attempts to create the local user database
     * @param c The Application context
     * @return True on success, shows a Toast and returns false on failure
     */
    public static boolean createDatabase (@NonNull Context c) {
        try {
            UDB = Room.databaseBuilder(c, UserDatabase.class, "local-user-db").build();
        } catch (Exception e) {
            Toast.makeText(c, e.toString(), Toast.LENGTH_LONG).show();
            UDB = null; // Double check no residue
            return false;
        }
        return true;
    }

    public abstract Class.DAO classDao();
    public abstract Course.DAO courseDao();
    public abstract Task.DAO taskDao();
    public abstract Meeting.DAO meetingDao();

    public static class Converters {
        @TypeConverter
        public static Date unixToDate (Long value) {
            return value==null? null : new Date(value);
        }
        @TypeConverter
        public static Long dateToUnix (Date date) {
            return date==null? null : date.getTime();
        }
        @TypeConverter
        public static Byte weekdaysToFlags (Weekdays wds) {
            return wds==null? null : wds.getByte();
        }
        @TypeConverter
        public static Weekdays flagsToWeekdays (Byte b) {
            return b==null? null : new Weekdays(b);
        }
    }

}
