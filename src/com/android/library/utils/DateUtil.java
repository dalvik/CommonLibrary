package com.android.library.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.text.format.DateUtils;

public class DateUtil extends DateUtils {

    protected final static long MINUTE = 60 * 1000;
    protected final static long HOUR = 60 * MINUTE;
    protected final static long DAY = 24 * HOUR;


    /**
     * 获取格式化后的时间字符串<br/>
     * yyyy-MM-dd EE HH:mm
     */
    public static String formatYMDEHM(Date date) {
        return getFormatDate(date, "yyyy-MM-dd EE HH:mm");
    }

    /**
     * 获取格式化后的时间字符串<br/>
     * yyyy-MM-dd HH:mm
     */
    public static String formatYMDHM(Date date) {
        return getFormatDate(date, "yyyy-MM-dd HH:mm");
    }

    /**
     * 获取格式化后的时间字符串<br/>
     * yyyy-MM-dd HH:mm:ss
     */
    public static String formatYMDHms(Date date) {
        return getFormatDate(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 获取格式化后的时间字符串<br/>
     * yyyy-MM-dd
     */
    public static String formatYMD(Date date) {
        return getFormatDate(date, "yyyy-MM-dd");
    }

    /**
     * 获取格式化后的时间字符串<br/>
     * EE HH:mm
     */
    public static String formatEHM(Date date) {
        return getFormatDate(date, "EE HH:mm");
    }

    /**
     * 获取格式化后的时间字符串<br/>
     * HH:mm
     */
    public static String formatHM(Date date) {
        return getFormatDate(date, "HH:mm");
    }

    /**
     * 获取时间
     *
     * @param date
     * @param template
     * @return
     */
    public static String getFormatDate(Date date, String template) {
        SimpleDateFormat formatter = new SimpleDateFormat(template, Locale.SIMPLIFIED_CHINESE);
        String strDate = formatter.format(date);
        return strDate;
    }

    /**
     * 小时
     * @param date
     * @return
     */
    public static int getHour(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 是否同一天
     *
     * @param cal1
     * @param cal2
     * @return
     */
    public static boolean isSameDay(Calendar cal1, Calendar cal2) {
        if ((cal1 == null) || (cal2 == null)) {
            throw new IllegalArgumentException("The date must not be null");
        }
        return (cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA)) && (cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)) && (cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR));
    }


}
