package com.example.theprincipleapp.helpers;

import java.util.Locale;

public enum Term {
    WINTER,
    SUMMER,
    FALL;

    @Override
    public String toString() {
        return this.name().toLowerCase(Locale.ROOT);
    }
}
