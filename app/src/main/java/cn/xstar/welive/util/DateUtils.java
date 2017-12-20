package cn.xstar.welive.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @author: xstar
 * @since: 2017-11-28.
 */

public class DateUtils {
    public static SimpleDateFormat format = new SimpleDateFormat();
    public static Calendar calendar = Calendar.getInstance();
    public static final String datePattern = "yyyy/MM/dd HH:mm:ss";

    public static synchronized String toStr(Calendar calendar, String pattern) {
        format.applyPattern(pattern);
        return format.format(calendar.getTime());
    }

    public static String dateStr(Calendar calendar) {
        return toStr(calendar, datePattern);
    }

    public static String currentStr(String datePattern) {
        calendar.setTimeInMillis(System.currentTimeMillis());
        return toStr(calendar, datePattern);
    }

    public static String currentStr() {
        return currentStr(datePattern);
    }

}
