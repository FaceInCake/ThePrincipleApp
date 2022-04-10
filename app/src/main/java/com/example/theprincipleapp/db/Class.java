package com.example.theprincipleapp.db;

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
import com.example.theprincipleapp.helpers.Term;
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
     * @return Term enum repr. a term
     */
    public Term getTerm () {
        Calendar c = Calendar.getInstance();
        c.setTime(start);
        int m = c.get(Calendar.MONTH);
        return Class.getTerm(m);
    }

    /**
     * Gets the term for the given month
     * @return Term enum repr. a term
     */
    public static Term getTerm (int month) {
        if (month <= 2) return Term.WINTER;
        if (month <= 6) return Term.SUMMER;
        return Term.FALL;
    }

    /**
     * Returns the unix min start and max end time of the given year and term
     * @param year The int year in question
     * @param term Term enum repr the term
     * @return First: minimum start date of term in unix time, Second: maximum end date of term in unix time
     */
    public static Pair<Long,Long> getTermBounds (int year, Term term) {
        Calendar c0 = Calendar.getInstance(),
                 c1 = Calendar.getInstance();
        c0.set(year,0,0,0,0,0);
        c1.set(year,0,0,0,0,0);
        switch (term) {
            case WINTER:
                c0.set(Calendar.MONTH, 0);
                c1.set(Calendar.MONTH, 3);
                break;
            case SUMMER:
                c0.set(Calendar.MONTH, 3);
                c1.set(Calendar.MONTH, 7);
                break;
            case FALL:
                c0.set(Calendar.MONTH, 7);
                c1.roll(Calendar.YEAR, 1);
                break;
            default:
                c0.set(Calendar.MONTH, 0);
                c1.roll(Calendar.YEAR, 1);
                break;
        }
        return new Pair<>(c0.getTime().getTime(), c1.getTime().getTime());
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
