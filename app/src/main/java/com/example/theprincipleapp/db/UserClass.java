package com.example.theprincipleapp.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Embedded;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Relation;
import androidx.room.Transaction;
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
        abstract UserClass getUserClass (int cid);

        @Transaction
        @Query("SELECT * FROM Class")
        List<UserClass> getAllFromDateRange(long dateLower, long dateUpper);
        @Query("SELECT * FROM Class WHERE :dateLower <= start AND start <= :dateUpper")
        abstract List<UserClass> getAll ();

        @Transaction
        @Insert(onConflict = OnConflictStrategy.REPLACE)
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
    }
}
