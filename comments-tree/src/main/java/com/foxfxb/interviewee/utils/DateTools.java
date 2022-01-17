package com.foxfxb.interviewee.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间操作类
 *
 * @author: fengxianbo
 * @version: 1.0, 2017年6月3日
 */
public class DateTools {

    public static final String                          DEFAULT_FORMAT          = "yyyy-MM-dd HH:mm:ss";
    public static final String                          CLAZZ_FORMAT            = "yyyy-MM-dd HH:mm";
    public static final String							SHORT_FORMAT_DATE		= "yyyyMMdd";
    public static final String                          SIMPLE_FORMAT           = "yyyy-MM-dd";
    public static final String							SHORT_FORMAT_DATETIME	= "yyyyMMddHHmmss";
    public static final String                          SIMPLE_MONTH_FORMAT     = "yyyy/MM";
    public static final String                          SIMPLE_MONTH_FORMAT2     = "yyyy-MM";
    private static final ThreadLocal<SimpleDateFormat>	threadLocal				= new ThreadLocal<SimpleDateFormat>();
    private static final Object							object					= new Object();

    public static final String							PROOF_FORMAT_DATE		= "yyyy-M-d";



    public static int getMonth(Calendar c) {
        return c.get(Calendar.MONTH) + 1;
    }



    /**
     * 获取日期所在的月份
     *
     * @param d
     * @return
     */
    public static int getMonth(Date d) {

        return getMonth(getCalendar(d));
    }



    /**
     * 获取日期所在的月份
     *
     * @param date
     *            默认yyyy-MM-dd 格式
     * @return
     */
    public static int getMonth(String date) {
        return getMonth(getCalendar(date));
    }

    public static int getYear(Date date) {
        return getCalendar(date).get(Calendar.YEAR);
    }

    /**
     * 获取日期所在的月份
     *
     * @param date
     * @param format
     * @return
     */
    public static int getMonth(String date, String format) {
        return getMonth(getCalendar(date, format));
    }


    /**
     *
     * @param d
     * @return
     */
    public static Calendar getCalendar(Date d) {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        return c;
    }



    /**
     *
     * @param date
     *            默认 yyyy-MM-dd格式
     * @return
     */
    public static Calendar getCalendar(String date) {
        return getCalendar(date, SIMPLE_FORMAT);
    }



    /**
     *
     * @param date
     * @param format
     * @return
     */
    public static Calendar getCalendar(String date, String format) {
        return getCalendar(stringToDateFormat(date, format));
    }



    /**
     *
     * 增加天数
     *
     * @param d
     * @param days
     * @return
     */
    public static Date addDay(Date d, int days) {
        Calendar c = getCalendar(d);
        c.add(Calendar.DAY_OF_MONTH, days);
        return c.getTime();
    }

    /**
     *
     * 增加小时
     *
     * @param d
     * @param hours
     * @return
     */
    public static Date addHour(Date d, int hours) {
        Calendar c = getCalendar(d);
        c.add(Calendar.HOUR, hours);
        return c.getTime();
    }

    public static int getDayOfWeek(Date d) {
        return get(Calendar.DAY_OF_WEEK, d);
    }

    public static int getDate(Date d) {
        return get(Calendar.DATE, d);
    }

    public static int getHour(Date d) {
        return get(Calendar.HOUR_OF_DAY, d);
    }

    public static int getMinute(Date d) {
        return get(Calendar.MINUTE, d);
    }

    public static Date setDay(Date d, int day){
        d = set(Calendar.DAY_OF_MONTH, d, day);
        return d;
    }

    public static Date setYear(Date d, int y){
        d = set(Calendar.YEAR, d, y);
        return d;
    }

    public static Date setMonth(Date d, int m){
        d = set(Calendar.MONTH, d, m);
        return d;
    }

    public static Date setHour(Date d, int hour){
        d = set(Calendar.HOUR_OF_DAY, d, hour);
        return d;
    }

    public static Date setMinute(Date d, int m){
        d = set(Calendar.MINUTE, d, m);
        return d;
    }

    protected static int get(int field, Date d){
        return getCalendar(d).get(field);
    }

    /**
     *
     * @see <code>Calendar.field</code>.
     */
    protected static Date set(int field, Date d, int s){
        Calendar c = getCalendar(d);
        c.set(field, s);
        return c.getTime();
    }

    /**
     * 增加天数
     *
     * @param d
     *            默认格式yyyy-MM-dd
     * @param days
     * @return
     */
    public static Date addDay(String d, int days) {
        return addDay(stringToDateFormat(d, SIMPLE_FORMAT), days);
    }

    public static Date stringToDateFormat(String date, String format) {
        if (CommonUtils.isNull(date)) {
            return null;
        }

        if (CommonUtils.isNull(format)){
            format = SIMPLE_FORMAT;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);

        try {
            return dateFormat.parse(date);
        } catch (ParseException e) {
        }
        return null;
    }

    /**
     * 计算差异天数
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int difDays(Date date1, Date date2) {
        if(date1 == null && date2 == null){
            return 0;
        }else if(date1 == null){
            return 999999;
        }else if(date2 == null){
            return -999999;
        }

        long difSeccond = date1.getTime() - date2.getTime();

        return (int) (difSeccond / (24 * 60 * 60 * 1000));

    }



    /**
     * 计算差异天数
     *
     * @param date1
     *            yyyy-MM-dd
     * @param date2
     *            yyyy-MM-dd
     * @return
     */
    public static int difDays(String date1, String date2) {
        return difDays(parseDate(date1), parseDate(date2));
    }



    /**
     *
     * 格式化时间，字符串默认格式为yyyy-MM-dd
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String formatDate(String date, String pattern) {
        return formatDate(parseDate(date), pattern);
    }



    public static String formatDate(String date, String pattern, String format) {
        return formatDate(parseDate(date, pattern), format);
    }



    /**
     * 格式化时间
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String formatDate(Date date, String pattern) {
        String formatDateStr = null;
        if (date != null) {
            try {
                formatDateStr = getDateFormat(pattern).format(date);
            } catch (Exception e) {
            }
        }
        return formatDateStr;
    }



    public static String formatDate(Date date) {
        return formatDate(date, DEFAULT_FORMAT);
    }

    private static SimpleDateFormat getDateFormat(String pattern) {
        SimpleDateFormat dateFormat = threadLocal.get();
        if (dateFormat == null) {
            synchronized (object) {
                if (dateFormat == null) {
                    dateFormat = new SimpleDateFormat(pattern);
                    dateFormat.setLenient(false);
                    threadLocal.set(dateFormat);
                }
            }
        }
        dateFormat.applyPattern(pattern);
        return dateFormat;
    }



    /**
     * 将字符串转为date对象
     *
     * @param dateStr
     * @param pattern
     * @return
     */
    public static Date parseDate(String dateStr, String pattern) {
        Date formatDate = null;
        if (dateStr != null && !dateStr.trim().equals("")) {
            try {
                formatDate = getDateFormat(pattern).parse(dateStr);
            } catch (Exception e) {
            }
        }
        return formatDate;
    }



    /**
     * 将日期字符串转化为日期,日期字符串为空返回null 默认格式yyyy-MM-dd
     *
     * @param dateStr
     *            日期字符串
     */
    public static Date parseDate(String dateStr) {
        return parseDate(dateStr, SIMPLE_FORMAT);
    }



    /**
     * 获取月份最后一天
     *
     * @param dateStr
     * @return
     */
    public static Date getMonthEndDate(String dateStr) {
        return getMonthEndDate(getCalendar(dateStr));
    }

    /**
     * 获取月份第一天
     *
     * @param d
     * @return
     */
    public static Date getMonthStartDate(Date d) {
        return getMonthStartDate(getCalendar(d));
    }

    /**
     * 获取月份最后一天
     *
     * @param d
     * @return
     */
    public static Date getMonthEndDate(Date d) {
        return getMonthEndDate(getCalendar(d));
    }

    /**
     * 获取月份最后一天
     *
     * @param c
     * @return
     */
    public static Date getMonthEndDate(Calendar c) {
        c.add(Calendar.MONTH, 1);
        c.set(Calendar.DAY_OF_MONTH, 0);
        return getDateEnd(c);
    }

    /**
     * 获取月份第一天
     *
     * @param c
     * @return
     */
    public static Date getMonthStartDate(Calendar c) {
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

        return c.getTime();
    }

    public static Date getDateStart(Date d) {
        return getDateStart(getCalendar(d));
    }



    public static Date getDateStart(Calendar c) {
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return c.getTime();
    }



    public static Date getDateStart(String d) {
        return getDateStart(getCalendar(d));
    }



    public static Date getDateEnd(Date d) {
        return getDateEnd(getCalendar(d));
    }



    public static Date getDateEnd(Calendar c) {
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        return c.getTime();
    }



    public static Date getDateEnd(String d) {
        return getDateEnd(getCalendar(d));
    }



    public static String getTimestamp() {
        int t = (int) (System.currentTimeMillis() / 1000);
        return String.valueOf(t);
    }

    public static int getAge(Date birthday){

        return null == birthday ? 0 : getAge(getCalendar(birthday));
    }

    public static int getAge(String birthday){
        return null == birthday || "".equals(birthday) ? 0 : getAge(getCalendar(birthday));
    }

    protected static int getAge(Calendar birthday){
        int birthYear = birthday.get(Calendar.YEAR);
        int nowYear = Calendar.getInstance().get(Calendar.YEAR);
        return nowYear - birthYear;
    }

    public static void main(String s[]){
        String da = "2019-08-24 17:52";
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        System.out.println(parseDate(da, "yyyy-MM-dd HH:mm"));
        System.out.println(sdf.format(parseDate(da, "yyyy-MM-dd HH:mm")));
        System.out.println(sdf.format(new Date(1566697920000L)));
    }
}
