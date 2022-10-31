package com.volvo.tax.config;

import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

/**
 * AppConstants contains the constant shared withing the application.
 *
 * @author Lasith Perera
 */
@Component
public class AppConstants {
    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);
    private static final String DATE_PATTERN = "yyyy-MM-dd";
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_PATTERN);
    private static final String TIME_PATTERN = "HH-mm-ss";
    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern(TIME_PATTERN);

}
