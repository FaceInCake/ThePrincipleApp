package com.example.theprincipleapp.db;

import org.junit.Test;
import static org.junit.Assert.*;

public class WeekdaysTest {
    static byte b0 = 0;
    static byte b5 = 5;

    @Test
    public void testGetByte() {
        Weekdays wds = new Weekdays(b5);
        assertEquals(b5, wds.getByte());
        Weekdays wds2 = new Weekdays();
        assertEquals(b0, wds2.getByte());
        Weekdays wds3 = new Weekdays(Weekday.MONDAY, Weekday.FRIDAY);
        assertEquals((byte)17, wds3.getByte());
    }

    @Test
    public void testTestToString() {
        Weekdays wds = new Weekdays();
        assertEquals("Weekdays{}", wds.toString());
        Weekdays wds2 = new Weekdays(b5);
        assertEquals("Weekdays{Monday,Wednesday}", wds2.toString());
        Weekdays wds3 = new Weekdays(Weekday.TUESDAY, Weekday.FRIDAY);
        assertEquals("Weekdays{Tuesday,Friday}", wds3.toString());
    }

    @Test
    public void testAdd() {
        Weekdays wds = new Weekdays();
        wds.add(Weekday.TUESDAY);
        assertEquals((byte)2, wds.getByte());
        wds.add(Weekday.MONDAY, Weekday.WEDNESDAY);
        assertEquals((byte)7, wds.getByte());
    }

    @Test
    public void testRemove() {
        Weekdays wds = new Weekdays(Weekday.MONDAY, Weekday.TUESDAY, Weekday.FRIDAY);
        wds.remove(Weekday.MONDAY, Weekday.FRIDAY);
        assertEquals(Weekday.TUESDAY.getId(), wds.getByte());
    }

    @Test
    public void testToggle() {
        Weekdays wds = new Weekdays(Weekday.MONDAY, Weekday.TUESDAY);
        wds.toggle(Weekday.TUESDAY, Weekday.WEDNESDAY);
        assertEquals(b5, wds.getByte());
    }

}