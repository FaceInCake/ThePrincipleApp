package com.example.theprincipleapp;

import android.content.Context;

import androidx.room.Room;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import com.example.theprincipleapp.db.UserDatabase;

import junit.framework.Test;
import junit.framework.TestCase;

/**
 * Instrumented test, which will execute on an Android device.
 * Also provided a setUp and tearDown method. Feel free to delete what you don't use.
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest extends TestCase {
    static Context appCon;
    static UserDatabase udb;

    @BeforeClass
    public static void setup() throws Exception {
        appCon = InstrumentationRegistry.getInstrumentation().getTargetContext();
        udb = Room.inMemoryDatabaseBuilder(appCon, UserDatabase.class).build();
    }

    @AfterClass
    public static void teardown() {
        udb.close();
    }

    @Before
    public void before() {
        udb.clearAllTables();
    }

    public void useAppContext() {
        assertEquals("com.example.theprincipleapp", appCon.getPackageName());
    }

    public void useDatabase() {
        assertTrue(udb.isOpen());
    }
}
