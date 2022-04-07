package com.example.theprincipleapp.db;

import org.junit.Before;
import org.junit.Test;
import java.util.Date;
import static org.junit.Assert.*;


public class ClassTest extends DBTest {

    @Before
    public void prepopulate () {
        udb.courseDao().insert(CourseTest.exampleCourse());
        udb.classDao().insert(exampleClass());
    }

    public static Class exampleClass () {
        Class c = new Class();
        c.professor = "Mr Bowers";
        c.oid = 1; // Remember to make sure it works with CourseTest.exampleCourse()
        c.start = new Date(1641790800); // Jan 10, 2022, 0:00:00
        c.end = new Date(1650427200); // Apr 20, 2022, 0:00:00
        return c;
    }

    @Test
    public void testInsert () {
        // Pass if @Before passes
    }

    @Test
    public void testGetter () {
        Class c = udb.classDao().get(1);
        assertNotNull(c);
        assertEquals("Mr Bowers", c.professor);
        assertEquals(1, c.oid);
        assertEquals(1641790800, c.start.getTime());
        assertTrue(c.end.after(c.start));
    }

}
