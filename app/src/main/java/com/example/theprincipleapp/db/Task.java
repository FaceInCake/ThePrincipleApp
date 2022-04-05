package com.example.theprincipleapp.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.Insert;
import androidx.room.PrimaryKey;
import androidx.room.Query;
import androidx.room.Update;
import java.util.Date;
import java.util.List;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

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
    public boolean finished;

    @Dao
    public interface DAO {
        @Insert Completable insert (Task... tasks);
        @Delete Completable delete (Task... tasks);
        @Update Completable update (Task... tasks);
        @Query("SELECT * FROM Task WHERE NOT finished AND open < (SELECT strftime('%s', 'now')) AND due > (SELECT strftime('%s', 'now'))")
        Single<List<Task>> getTodo ();
    }
}
