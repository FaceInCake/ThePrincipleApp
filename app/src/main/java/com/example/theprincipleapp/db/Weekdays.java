package com.example.theprincipleapp.db;

import androidx.annotation.NonNull;


/**
 * Stores a set of week days. Mon->Sun.
 */
public class Weekdays {
    private byte a;

    /**
     * Adds the given week days to this set
     * @param args The weekday(s) to add
     * @implNote add(Weekday.MONDAY, Weekday.WEDNESDAY)
     */
    public void add (Weekday ... args) {
        if (args != null)
            for (Weekday wd : args)
                if (wd != null)
                    a |= wd.getId();
    }

    /**
     * Removes the given week days from this set
     * @param args The weekday(s) to remove
     * @implNote remove(Weekday.SATURDAY, Weekday.SUNDAY)
     */
    public void remove (Weekday ... args) {
        a ^= Weekday.MASK;
        add(args);
        a ^= Weekday.MASK;
    }

    /**
     * Toggles the given week days from this set.
     * Adding days if they didn't exist, and removing them if they did.
     * @param args The weekday(s) to toggle
     * @implNote toggle(Weekday.MONDAY)
     */
    public void toggle (Weekday ... args) {
        if (args != null)
            for (Weekday wd : args)
                if (wd != null)
                    a ^= wd.getId();
    }

    /**
     * Checks to see if a weekday is in the byte
     * @param weekday The weekday to check for
     * @implNote contains(Weekday.MONDAY)
     * */
    public boolean contains(Weekday weekday){
        if (weekday != null){
            return (a & weekday.getId()) == (byte)1;
        }
        return false;
    }


    public byte getByte() { return a; }

    public Weekdays () { this.a = 0; }
    public Weekdays (byte b) { this.a = (byte) (b & Weekday.MASK); }
    public Weekdays (Weekday ... args) { this(); add(args); }

    @NonNull
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Weekdays{");
        for (Weekday wd : Weekday.values())
            if ((this.a & wd.getId()) > 0) {
                if (sb.length() > 9)
                    sb.append(',');
                sb.append(wd);
            }
        sb.append('}');
        return sb.toString();
    }

    public String abbreviate () {
        StringBuilder sb = new StringBuilder();
        for (Weekday wd : Weekday.values()) {
            if ((this.a & wd.getId()) > 0) {
                if (sb.length() > 0)
                    sb.append(',');
                sb.append(wd.toString().substring(0, 3));
            }
        }
        return sb.toString();
    }
}
