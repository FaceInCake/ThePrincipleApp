package com.example.theprincipleapp.db;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Date;


public class TaskTest extends DBTest {

    @Before
    public void prepopulate() {
        udb.courseDao().insert(CourseTest.exampleCourse());
        udb.classDao().insert(ClassTest.exampleClass());
        udb.taskDao().insert(exampleTask());
    }

    public static Task exampleTask () {
        Task t = new Task();
        t.tid = 1;
        t.cid = 1;
        t.name = "My New Task";
        t.description = "This is a new task to do!";
        t.type = TaskTypeEnum.ASSIGNMENT;
        t.location = "Yo mamma";
        t.open = new Date(1566401400); // Aug 21 2019 11:30:00
        t.due = new Date(1566406800); // Aug 21 2019 13:00:00
        t.finished = false;
        return t;
    }

    @Test
    public void testInsert() {
        // Pass if @Before passes
    }

    @Test
    public void testGetter() {
        Task t = udb.taskDao().get(1);
        assertNotNull(t);
        assertEquals("My New Task", t.name);
        assertEquals(TaskTypeEnum.ASSIGNMENT, t.type);
        assertEquals(1566401400, t.open.getTime());
        assertTrue(t.due.after(t.open));
    }

}
