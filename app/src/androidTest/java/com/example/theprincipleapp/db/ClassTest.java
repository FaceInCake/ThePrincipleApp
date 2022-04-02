package com.example.theprincipleapp.db;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import junit.framework.TestCase;
import org.junit.runner.RunWith;

import java.sql.SQLException;

@RunWith(AndroidJUnit4.class)
public class ClassTest extends TestCase {
    Context appCon;

    public void setUp() throws Exception {
        super.setUp();
        appCon = InstrumentationRegistry.getInstrumentation().getTargetContext();
        if (! UserDatabase.createDatabase(appCon))
            throw new SQLException("Failed to create user-database");
    }

    public void tearDown() {
        UserDatabase.UDB.close();
    }
}