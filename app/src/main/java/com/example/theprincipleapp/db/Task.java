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
import java.util.List;


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
    public TaskTypeEnum type;
    public Date open;
    public Date due;
    public float grade;
    public boolean finished;

    @Dao
    public interface DAO {
        @Insert(onConflict = OnConflictStrategy.REPLACE)
        long insert (Task task);
        @Delete int delete (Task task);
        @Update int update (Task task);

        @Query("SELECT * FROM Task WHERE tid = :tid")
        Task get (int tid);

        @Query("SELECT * FROM Task where cid = :cid")
        List<Task> getFrom (int cid);

        @Query("SELECT * FROM Task WHERE NOT finished AND open < (SELECT strftime('%s', 'now')) AND due > (SELECT strftime('%s', 'now'))")
        List<Task> getTodo ();

        @Query("SELECT * FROM Task")
        List<Task> getAll();
    }
}
