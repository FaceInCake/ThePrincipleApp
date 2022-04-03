package com.example.theprincipleapp.db;

import android.content.Context;

import androidx.room.Room;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import junit.framework.TestCase;
import org.junit.runner.RunWith;

import java.sql.SQLException;

@RunWith(AndroidJUnit4.class)
public class ClassTest extends TestCase {
    Context appCon;
    UserDatabase udb;

    public void setUp() throws Exception {
        super.setUp();
        appCon = InstrumentationRegistry.getInstrumentation().getTargetContext();
        udb = Room.inMemoryDatabaseBuilder(appCon, UserDatabase.class).build();
    }

    public void tearDown() {
        udb.close();
    }
}