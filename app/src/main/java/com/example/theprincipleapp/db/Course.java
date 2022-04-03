package com.example.theprincipleapp.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.Insert;
import androidx.room.PrimaryKey;
import androidx.room.Update;
import io.reactivex.rxjava3.core.Completable;

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
        @Insert Completable insert (Course... courses);
        @Delete Completable delete (Course... courses);
        @Update Completable update (Course... courses);
    }

}
