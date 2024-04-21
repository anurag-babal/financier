package com.example.userservice.util;

import java.time.LocalDate;

public class DateFormatter {
    public static LocalDate stringToLocalDate(String date) {
        return LocalDate.parse(date);
    }

    public static String localDateToString(LocalDate date) {
        return date.toString();
    }
}
