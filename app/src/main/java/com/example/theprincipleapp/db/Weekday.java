package com.example.theprincipleapp.db;

/**
 * Enum for storing a Week Day. Also has a flag id and pretty name.
 */
public enum Weekday {
    MONDAY    ("Monday",    (byte)0x01, "Mon"),
    TUESDAY   ("Tuesday",   (byte)0x02, "Tue"),
    WEDNESDAY ("Wednesday", (byte)0x04, "Wed"),
    THURSDAY  ("Thursday",  (byte)0x08, "Thur"),
    FRIDAY    ("Friday",    (byte)0x10, "Fri"),
    SATURDAY  ("Saturday",  (byte)0x20, "Sat"),
    SUNDAY    ("Sunday",    (byte)0x40, "Sun");

    public static byte MASK = 0x7F;

    private byte id;
    private String longAbbr;

    public byte getId() { return id; }
    public String getLongAbbr() { return longAbbr; }

    private final String name;
    @Override public String toString() { return name; }

    Weekday(String name, byte id, String longAbbr) {
        this.name = name;
        this.id = id;
        this.longAbbr = longAbbr;
    }
}
