package com.zl.way.util;

import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

/**
 * 日期工具类
 * 
 * @author xuzhongliang
 * @date 2019-08-19
 */
public final class DateUtil {
    public static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";

    /**
     * 获得当前日期
     * 
     * @return java.util.Date
     */
    public static Date getCurrent() {
        return DateTime.now().toDate();
    }

    /**
     * 获得指定日期格式的字符串。如果格式为空，返回默认日期格式字符串。
     * 
     * @param format
     * @return
     */
    public static String getString(String format) {
        if (null == format || "".equals(format)) {
            throw new IllegalArgumentException("format参数不正确");
        }
        return DateTime.now().toString(format);
    }

    public static String getDefaultDateTime() {
        return getString(DEFAULT_DATE_TIME_FORMAT);
    }

    public static String getDefaultDate() {
        return getString(DEFAULT_DATE_FORMAT);
    }

    /**
     * 日期时间字符串转日期时间，自带格式
     * 
     * @param dateString
     * @param format
     * @return
     */
    public static Date getDate(String dateString, String format) {
        if (null == dateString || "".equals(dateString)) {
            throw new IllegalArgumentException("dateString参数不正确");
        }
        if (null == format || "".equals(format)) {
            throw new IllegalArgumentException("format参数不正确");
        }
        return DateTime.parse(dateString, DateTimeFormat.forPattern(format)).toDate();
    }

    public static void main(String[] args) {
        System.out.println(DateUtil.getCurrent());
        System.out.println(DateUtil.getDefaultDate());
        System.out.println(DateUtil.getString("yyyy-MM"));
        System.out.println(DateUtil.getDefaultDateTime());
        System.out.println(DateUtil.getString("MM-dd hh:mm"));
        System.out.println(DateUtil.getDate("2017-05-31", DateUtil.DEFAULT_DATE_FORMAT));
        System.out.println(DateUtil.getDate("05/31/2017 22:22:22", "MM/dd/yyyy HH:mm:ss"));
    }
}
