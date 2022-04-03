package com.example.theprincipleapp.db;

/**
 * Enum for storing a Week Day. Also has a flag id and pretty name.
 */
public enum Weekday {
    MONDAY    ("Monday",    (byte)0x01),
    TUESDAY   ("Tuesday",   (byte)0x02),
    WEDNESDAY ("Wednesday", (byte)0x04),
    THURSDAY  ("Thursday",  (byte)0x08),
    FRIDAY    ("Friday",    (byte)0x10),
    SATURDAY  ("Saturday",  (byte)0x20),
    SUNDAY    ("Sunday",    (byte)0x40);

    public static byte MASK = 0x7F;

    private byte id;
    public byte getId() { return id; }

    private String name;
    @Override public String toString() { return name; }

    Weekday(String name, byte id) {
        this.id = id;
        this.name = name;
    }
}
