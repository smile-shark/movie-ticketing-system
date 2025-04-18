package com.movie.utils;

public class VagueUtil {
    public static String vague(String str) {
        if (str == null) {
            return "%";
        }
        return "%" + String.join("%", str.split("")) + "%";
    }
}
