package com.example.theprincipleapp.db;

import android.app.Activity;
import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;
import com.example.theprincipleapp.R;
import com.example.theprincipleapp.helpers.Util;
import java.util.Date;


@Database(
    entities = {Class.class, Course.class, Task.class, Meeting.class},
    exportSchema = true,
    version = 2
)
@TypeConverters({UserDatabase.Converters.class})
public abstract class UserDatabase extends RoomDatabase {
    /**
     * Singleton of the User Database object.
     */
    public static UserDatabase UDB = null;
    public static Context UDB_CONTEXT = null;
    public static boolean IS_INITIALIZED = false;

    /**
     * Attempts to create the local user database
     * @param activity Activity to tie the database to
     * @return True on success, shows a Toast and returns false on failure
     */
    public static boolean createDatabase (Activity activity) {
        if (IS_INITIALIZED) return true;
        if (activity == null) return false;
        try {
            UDB = Room.databaseBuilder(activity, UserDatabase.class, "local-user-db")
                .fallbackToDestructiveMigration()
                .build();
            UDB_CONTEXT = activity;
            IS_INITIALIZED = true;
        } catch (Exception e) {
            Util.alertError(activity, R.string.err_dbInit);
            UDB = null; // Double check no residue
            return false;
        }
        return true;
    }

    public abstract Class.DAO classDao();
    public abstract Course.DAO courseDao();
    public abstract Task.DAO taskDao();
    public abstract Meeting.DAO meetingDao();
    public abstract UserClass.DAO userClassDao();

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
