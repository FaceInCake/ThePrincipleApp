package com.example.theprincipleapp.helpers;

public enum Term {
    WINTER,
    SUMMER,
    FALL;

    @Override
    public String toString() {
        return Util.toTitleCase(this.name());
    }
}
