package com.example.theprincipleapp.db;

import androidx.annotation.Nullable;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.Insert;
import androidx.room.PrimaryKey;
import androidx.room.Update;

import java.util.Date;

@Entity(
    indices={@Index(value="cid")},
    foreignKeys={@ForeignKey(parentColumns="cid",childColumns="cid",entity=Class.class)}
)
public class Task {
    @PrimaryKey(autoGenerate = true)
    public int tid;
    public int cid;
    public String name;
    public String description;
    public String location;
    public TaskTypeEnum taskType;
    public Date openDate;
    public Date dueDate;
    public boolean isFinished;

    @Dao
    public interface DAO {
        @Insert void insert (Task... tasks);
        @Delete void delete (Task... tasks);
        @Update void update (Task... tasks);
    }
}