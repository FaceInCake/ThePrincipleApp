package com.example.theprincipleapp.db;

import androidx.annotation.NonNull;
import com.example.theprincipleapp.helpers.Util;


/**
 * Enum for storing a Week Day. Also has a flag id and pretty name.
 */
public enum Weekday {
    MONDAY    ((byte)0x01),
    TUESDAY   ((byte)0x02),
    WEDNESDAY ((byte)0x04),
    THURSDAY  ((byte)0x08),
    FRIDAY    ((byte)0x10),
    SATURDAY  ((byte)0x20),
    SUNDAY    ((byte)0x40);

    public static byte MASK = 0x7F;

    private byte id;
    public byte getId() { return id; }

    @NonNull @Override public
    String toString() { return Util.toTitleCase(this.name()); }

    Weekday(byte id) {
        this.id = id;
    }
}
