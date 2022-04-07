package com.example.theprincipleapp.db;

import android.content.Context;

import androidx.room.PrimaryKey;
import androidx.room.Room;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;


@RunWith(AndroidJUnit4.class)
public abstract class DBTest {
    protected static Context appCon;
    protected static UserDatabase udb;

    @BeforeClass
    public static void db_setup() {
        appCon = InstrumentationRegistry.getInstrumentation().getTargetContext();
        udb = Room.inMemoryDatabaseBuilder(appCon, UserDatabase.class).build();
    }

    @AfterClass
    public static void db_teardown () {
        udb.close();
    }

    @After
    public void clean () {
        udb.clearAllTables();
    }

}
