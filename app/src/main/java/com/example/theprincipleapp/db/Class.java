package com.example.theprincipleapp.db;

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
import java.util.Date;
import io.reactivex.rxjava3.core.Completable;

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

    @Dao
    public interface DAO {
        @Insert(onConflict = OnConflictStrategy.REPLACE) void insert (Class... classes);
        @Delete void delete (Class... classes);
        @Update void update (Class... classes);

        @Query("SELECT * FROM Class WHERE cid=:cid")
        Class get (int cid);
    }
}
