package com.company.conferenceroombooking.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    public static final String DDMMYY = "ddMMyy";

    public DateUtils() {
    }

    public static String getDateInString(String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(new Date());
    }
}
