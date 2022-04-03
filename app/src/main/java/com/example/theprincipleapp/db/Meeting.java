package com.example.theprincipleapp.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.Insert;
import androidx.room.PrimaryKey;
import androidx.room.Update;
import io.reactivex.rxjava3.core.Completable;


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
        @Insert Completable insert (Meeting... meetings);
        @Delete Completable delete (Meeting... meetings);
        @Update Completable update (Meeting... meetings);
    }
}
