package com.example.theprincipleapp.db;

import android.content.Context;
import androidx.room.Room;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import junit.framework.TestCase;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;


@RunWith(AndroidJUnit4.class)
public class ClassTest extends TestCase {
    Context appCon;
    UserDatabase udb;

    @BeforeClass
    public void setUp() throws Exception {
        super.setUp();
        appCon = InstrumentationRegistry.getInstrumentation().getTargetContext();
        udb = Room.inMemoryDatabaseBuilder(appCon, UserDatabase.class).build();
    }

    @AfterClass
    public void tearDown() {
        udb.close();
    }

    @Before
    public void before() {
        udb.clearAllTables();
    }

}
