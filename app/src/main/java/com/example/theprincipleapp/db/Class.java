package com.example.theprincipleapp.db;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Pair;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.PrimaryKey;
import androidx.room.Query;
import androidx.room.Update;
import java.util.Calendar;
import java.util.Date;


/**
 * Base Class Entity.
 * Use UserClass if you want the Class, Course, related Tasks, and related Meetings
 */
@Entity(
        indices = {@Index(value = {"oid"})},
        foreignKeys = {@ForeignKey(parentColumns="oid", childColumns="oid", entity=Course.class)}
)
public class Class {
    @PrimaryKey(autoGenerate=true)
    public int cid;
    public int oid;
    public Date start;
    public Date end;
    public String professor = "TBD";

    /**
     * Gets the year of this class
     * @return int year, ex. 2019
     */
    public int getYear () {
        Calendar c = Calendar.getInstance();
        c.setTime(start);
        return c.get(Calendar.YEAR);
    }

    /**
     * Gets the term for the given class
     * @return 0=Winter, 1=Summer, 2=Fall
     */
    public int getTerm (Date d) {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        int m = c.get(Calendar.MONTH);
        return Class.getTerm(m);
    }

    /**
     * Gets the term for the given month
     * @return 0=Winter, 1=Summer, 2=Fall
     */
    public static int getTerm (int month) {
        if (month <= 2) return 0;
        if (month <= 6) return 1;
        return 2;
    }

    /**
     * Returns the unix min start and max end time of the given year and term
     * @param year The int year in question
     * @param term int repr of the term. 0=Winter, 1=Summer, 2=Fall
     * @return First: minimum start date of term in unix time, Second: maximum end date of term in unix time
     */
    public static Pair<Long,Long> getTermBounds (int year, int term) throws IllegalArgumentException {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(0);
        c.set(Calendar.YEAR, year);
        long a = c.getTimeInMillis();
        final long month = 2629743;
        switch (term) {
            case 0: return new Pair<>(a, a+month);
            case 1: return new Pair<>(a+month*3, a+month*5);
            case 2: return new Pair<>(a+month*7, a+month*9);
            default: throw new IllegalArgumentException("term is out of range");
        }
    }

    @Dao
    public interface DAO {
        @Insert(onConflict = OnConflictStrategy.REPLACE)
        long insert (Class cls);

        @Delete int delete (Class cls);
        @Update int update (Class cls);

        @Query("SELECT * FROM Class WHERE cid=:cid")
        Class get (int cid);
    }

}
