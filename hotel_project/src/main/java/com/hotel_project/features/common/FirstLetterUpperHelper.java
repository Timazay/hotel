package com.hotel_project.features.common;

public class FirstLetterUpperHelper {
    public static String makeFirstLetterInUpperCase(String s) {
        return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
    }
}
