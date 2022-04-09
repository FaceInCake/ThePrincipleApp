package com.example.theprincipleapp.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.PrimaryKey;
import androidx.room.Query;
import androidx.room.Update;

@Entity(indices = {
        @Index(value={"code"}, unique = true)
})
public class Course {
    @PrimaryKey(autoGenerate = true)
    public int oid;
    public String code;
    public String full_name;
    public String short_name;
    public String description = "";
//    public float credits = 3.00f;

    @Dao
    public interface DAO {
        @Insert(onConflict = OnConflictStrategy.REPLACE) long insert (Course courses);
        @Delete void delete (Course... courses);
        @Update void update (Course... courses);

        @Query("SELECT * FROM Course WHERE oid = :oid")
        Course get (int oid);
    }

}
