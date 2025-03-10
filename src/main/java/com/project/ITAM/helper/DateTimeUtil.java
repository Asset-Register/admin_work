package com.project.ITAM.helper;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {

    public static String currentDateTime() {
         return LocalDateTime.now(ZoneId.systemDefault()).format(DateTimeFormatter. ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

}
