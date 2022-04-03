package com.example.theprincipleapp.db;

import androidx.room.Embedded;
import androidx.room.Relation;
import java.util.List;

public class UserClass {
    @Embedded
    public Class cls;
    @Relation(parentColumn="oid",entityColumn="oid")
    public Course course;
    @Relation(parentColumn="cid",entityColumn="cid")
    public List<Task> tasks;
}
