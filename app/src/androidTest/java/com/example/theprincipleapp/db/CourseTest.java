package com.example.theprincipleapp.db;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


public class CourseTest extends DBTest {

    @Before
    public void prepopulate () {
        udb.courseDao().insert(exampleCourse());
    }

    public static Course exampleCourse () {
        Course c = new Course();
        c.oid = 1;
        c.code = "COMP-1234";
        c.full_name = "Super Cool Computer Course";
        c.short_name = "Super Cool Comp";
        return c;
    }

    @Test
    public void testInsert () {
        // Pass if @Before worked
    }

    @Test
    public void testGetter () {
//        udb.courseDao().get(1)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(c -> { }, error -> { });
        Course c = udb.courseDao().get(1);
        assertNotNull(c);
        assertEquals("COMP-1234", c.code);
        assertEquals("Super Cool Comp", c.short_name);
        assertEquals(1, c.oid);
    }

    @Test
    public void testUpdate () {
        Course c = exampleCourse();
        c.oid = 1;
        c.short_name = "Changed!";
        udb.courseDao().update(c);
        Course d = udb.courseDao().get(1);
        assertNotNull(d);
        assertEquals("Changed!", d.short_name);
    }
}
