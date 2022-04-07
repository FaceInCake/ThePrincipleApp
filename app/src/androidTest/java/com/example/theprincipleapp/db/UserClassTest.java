package com.example.theprincipleapp.db;

import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


public class UserClassTest extends DBTest {

    @Before
    public void prepopulate () {
        UserClass u = exampleUserClass();
        udb.courseDao().insert(u.course);
        udb.classDao().insert(u.cls);
        //insert tasks
        //insert meetings
    }

    public static UserClass exampleUserClass () {
        UserClass u = new UserClass();
        u.cls = ClassTest.exampleClass();
        u.course = CourseTest.exampleCourse();
        u.meetings = new ArrayList<>();
        u.tasks = new ArrayList<>();
        return u;
    }

    @Test
    public void testInsert () {
        // Pass if @Before passes
    }

    @Test
    public void testGetter () {
        UserClass u = udb.userClassDao().get(1);
        assertNotNull(u);
        assertEquals("COMP-1234", u.course.code);
        assertEquals("Mr Bowers", u.cls.professor);
        assertTrue(u.meetings.isEmpty());
        assertTrue(u.tasks.isEmpty());
    }

    @Test
    public void testGetAll () {
        List<UserClass> ul = udb.userClassDao().getAll();
        assertNotNull(ul);
        assertEquals(1, ul.size());
        UserClass u = ul.get(0);
        assertEquals("COMP-1234", u.course.code);
        assertEquals("Mr Bowers", u.cls.professor);
        assertTrue(u.meetings.isEmpty());
        assertTrue(u.tasks.isEmpty());
    }


}
