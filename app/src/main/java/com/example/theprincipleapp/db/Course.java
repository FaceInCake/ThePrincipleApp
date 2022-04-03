package com.example.theprincipleapp.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.Insert;
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
        @Insert void insert (Course... courses);
        @Delete void delete (Course... courses);
        @Update void update (Course... courses);

        /**
         * Gets and returns the course with the given ID
         * @param oid The ID of the course to get
         * @return The Course with the matching id, //TODO: null if not found?
         */
        @Query("SELECT * FROM Course WHERE oid=:oid")
        Course get (int oid);

        /**
         * Finds and returns the Course with the given course code
         * @param code 'ABCD-1234' format, the code to look for
         * @return The Course with the matching course code, //TODO: null if not found?
         */
        @Query("SELECT * FROM Course WHERE code=:code")
        Course find (String code);
    }

}
