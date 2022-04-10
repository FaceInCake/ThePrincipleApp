package com.example.theprincipleapp.db;

import android.util.Log;
import android.util.Pair;
import androidx.room.Dao;
import androidx.room.Embedded;
import androidx.room.Query;
import androidx.room.Relation;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.theprincipleapp.helpers.Term;
import java.util.List;

/**
 * Stores a Class along with related Course, Tasks, and Meetings information.
 */
public class UserClass {
    @Embedded
    public Class cls;
    @Relation(parentColumn="oid",entityColumn="oid")
    public Course course;
    @Relation(parentColumn="cid",entityColumn="cid")
    public List<Task> tasks;
    @Relation(parentColumn="cid",entityColumn="cid")
    public List<Meeting> meetings;

    @Dao
    public static abstract class DAO {

        @Transaction
        @Query("SELECT * FROM Class WHERE cid = :cid")
        public abstract UserClass get (int cid);

        @Transaction
        @Query("SELECT * FROM Class")
        public abstract List<UserClass> getAll ();

        @Transaction
        @Query("SELECT * FROM Class WHERE :dateLower <= start AND start <= :dateUpper")
        public abstract List<UserClass> getAllFromDateRange(long dateLower, long dateUpper);

        @Transaction
        public List<UserClass> getAllFrom(int year, Term term) {
            Pair<Long, Long> p = Class.getTermBounds(year, term);
            return getAllFromDateRange(p.first, p.second);
        }

        @Transaction
        public long insert (UserClass uc) {
            long oid = UserDatabase.UDB.courseDao().insert(uc.course);
            uc.cls.oid = (int) oid;
            long cid = UserDatabase.UDB.classDao().insert(uc.cls);
            // set meetings cid to above
            // insert meetings
            // set tasks cid to above
            // insert tasks
            return cid;
        }

        @Update
        public void update(UserClass uc){
            long oid = UserDatabase.UDB.courseDao().update(uc.course);
            uc.cls.oid = (int) oid;
            long cid = UserDatabase.UDB.classDao().update(uc.cls);
        }

    }
}
