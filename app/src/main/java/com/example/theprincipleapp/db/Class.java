package com.example.theprincipleapp.db;

import android.os.Parcel;
import android.os.Parcelable;

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

/**
 * Base Class Entity.
 * Use UserClass if you want the Class, Course, related Tasks, and related Meetings
 */
@Entity(
        indices = {@Index(value = {"oid"})},
        foreignKeys = {@ForeignKey(parentColumns="oid", childColumns="oid", entity=Course.class)}
)
public class Class implements Parcelable {
    @PrimaryKey(autoGenerate=true)
    public int cid;
    public int oid;
    public Date start;
    public Date end;
    public String professor = "TBD";

    @Dao
    public interface DAO {
        @Insert void insert (Class... classes);
        @Delete void delete (Class... classes);
        @Update void update (Class... classes);

        @Query("SELECT * FROM Class WHERE cid=:cid")
        Class get (int cid);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(cid);
        dest.writeInt(oid);
        dest.writeLong(start.getTime());
        dest.writeLong(end.getTime());
        dest.writeString(professor);
    }

    // Used to regenerate db.class from parcelable
    public static final Parcelable.Creator<Class> CREATOR = new Parcelable.Creator<Class>() {
        public Class createFromParcel(Parcel in) {
            return new Class(in);
        }

        public Class[] newArray(int size) {
            return new Class[size];
        }
    };

    private Class(Parcel in){
        cid = in.readInt();
        oid = in.readInt();
        start = new Date(in.readLong());
        end = new Date(in.readLong());
        professor = in.readString();
    }
}
