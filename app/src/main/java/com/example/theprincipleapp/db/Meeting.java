package com.example.theprincipleapp.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.Insert;
import androidx.room.PrimaryKey;
import androidx.room.Update;


@Entity(
    indices = {@Index(value="cid")},
    foreignKeys = {@ForeignKey(parentColumns="cid",childColumns="cid",entity=Class.class)}
)
public class Meeting {
    @PrimaryKey(autoGenerate = true)
    public int mid;
    public int cid;
    public Type type;
    public int section;
    public Weekdays weekdays;

    public enum Type {Lecture, Lab}

    @Dao
    public interface DAO {
        @Insert void insert (Meeting... meetings);
        @Delete void delete (Meeting... meetings);
        @Update void update (Meeting... meetings);
    }
}
