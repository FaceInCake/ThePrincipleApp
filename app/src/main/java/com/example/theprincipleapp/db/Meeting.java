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


@Entity(
    indices = {@Index(value="cid")},
    foreignKeys = {@ForeignKey(parentColumns="cid",childColumns="cid",entity=Class.class)}
)
public class Meeting {
    @PrimaryKey(autoGenerate = true)
    public int mid;
    public int cid;
    public MeetingTypeEnum type;
    public int section;
    public Weekdays weekdays;
    public Date start;
    public Date end;

    @Dao
    public interface DAO {
        @Insert long insert (Meeting meeting);
        @Delete int delete (Meeting meeting);
        @Update int update (Meeting meeting);

        @Query("SELECT * FROM Meeting where cid = :cid")
        List<Meeting> getFrom (int cid);

        @Query("SELECt * FROM Meeting")
        List<Meeting> getAll();
    }
}
