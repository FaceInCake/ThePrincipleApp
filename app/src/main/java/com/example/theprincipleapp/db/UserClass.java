package com.example.theprincipleapp.db;

import androidx.room.Dao;
import androidx.room.Embedded;
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
    public interface DAO {

        @Transaction
        @Query("SELECT * FROM Class WHERE cid = :cid")
        UserClass get (int cid);

        @Transaction
        @Query("SELECT * FROM Class")
        List<UserClass> getAll ();
    }
}
