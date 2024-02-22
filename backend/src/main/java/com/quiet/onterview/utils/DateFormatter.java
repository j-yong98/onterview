package com.quiet.onterview.utils;

import java.time.format.DateTimeFormatter;

public class DateFormatter {

    public static DateTimeFormatter communityDateFormatter = DateTimeFormatter.ofPattern(
            "yyyy/MM/dd");
    public static DateTimeFormatter interviewRoomDateFormatter = DateTimeFormatter.ofPattern(
            "yyyy-MM-dd hh:mm:dd");
}
