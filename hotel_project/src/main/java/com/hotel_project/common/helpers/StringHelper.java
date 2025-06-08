package com.hotel_project.common.helpers;

public class StringHelper {
    public static String makeFirstLetterInUpperCase(String s) {
        return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
    }
}
