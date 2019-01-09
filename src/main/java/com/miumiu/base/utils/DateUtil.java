package com.miumiu.base.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author chimmhuang
 * @date 2019/1/9 0009 18:31
 * @description
 */
public class DateUtil {

    /**
     * 默认日期格式
     */
    public static String DEFAULT_FORMAT = "yyyy-MM-dd";
    public static String NOW_DATE = "yyyy-MM-dd HH:mm:ss";
    public static String DEFAULT_FORMAT_AS_BJ = "yyyy年MM月dd日";

    /**
     * 格式化日期
     *
     * @param date 日期对象
     * @return String 日期字符串
     */
    public static String formatDate(Date date) {
        SimpleDateFormat f = new SimpleDateFormat(DEFAULT_FORMAT);
        String sDate = f.format(date);
        return sDate;
    }

    /**
     * 格式化日期
     *
     * @param date 日期对象
     * @return String 日期字符串
     */
    public static Date formatDate(String date) throws Exception {
        SimpleDateFormat f = new SimpleDateFormat(DEFAULT_FORMAT);
        return f.parse(date);
    }

    /**
     * 格式化日期
     *
     * @param date 日期对象
     * @param type - 格式
     * @return String 日期字符串
     */
    public static String formatDate(Date date, String type) {
        SimpleDateFormat f = new SimpleDateFormat(type);
        String sDate = f.format(date);
        return sDate;
    }

    public static String formatMonth(Date date) {
        SimpleDateFormat f = new SimpleDateFormat(DEFAULT_FORMAT);
        String sDate = f.format(date);
        return sDate;
    }

    /**
     * 自定义转换格式
     *
     * @param date - 时间
     * @param type - 转换格式
     * @return
     */
    public static String formatByown(Date date, String type) {
        SimpleDateFormat f = new SimpleDateFormat(type);
        String sDate = f.format(date);
        return sDate;
    }

    /**
     * 拿到当前的天
     *
     * @return
     */
    public static String nowDate() {
        Date date = new Date();
        return formatDate(date);
    }

    /**
     * 拿取指定的日期，负数为前几天
     * @param day
     * @return
     */
    public static String toDayTime(int day){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH, day);
        return formatByown(calendar.getTime(), DEFAULT_FORMAT);
    }



    /**
     * 拿取当前的时间
     *
     * @return
     */
    public static String now() {
        Date date = new Date();
        return formatByown(date, NOW_DATE);
    }

    /**
     * 拿到昨天的日期
     */
    public static String yesterDay() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        date = calendar.getTime();
        return formatDate(date);
    }

    /**
     * 获取当年的第一天
     *
     * @return
     */
    public static String getCurrYearFirst() {
        Calendar currCal = Calendar.getInstance();
        int currentYear = currCal.get(Calendar.YEAR);
        return getYearFirst(currentYear);
    }

    /**
     * 拿取当前年
     * @return
     */
    public static int getNowYear(){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        return year;
    }

    /**
     * 拿取当前年
     * @return
     */
    public static int getNowMonth(){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.MONTH)+1;
        return year;
    }

    /**
     * 获取当年的最后一天
     *
     * @return
     */
    public static String getCurrYearLast() {
        Calendar currCal = Calendar.getInstance();
        int currentYear = currCal.get(Calendar.YEAR);
        return getYearLast(currentYear);
    }

    /**
     * 获取某年第一天日期
     *
     * @param year 年份
     * @return Date
     */
    public static String getYearFirst(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        Date currYearFirst = calendar.getTime();
        return formatDate(currYearFirst);
    }

    /**
     * 获取某年最后一天日期
     *
     * @param year 年份
     * @return Date
     */
    public static String getYearLast(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.roll(Calendar.DAY_OF_YEAR, -1);
        Date currYearLast = calendar.getTime();
        return formatDate(currYearLast);
    }

    /**
     * 拿到当前月第一天
     *
     * @return
     */
    public static String getNowMonthFirst() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
        return formatDate(c.getTime());
    }

    /**
     * 拿到当前月最后一天
     *
     * @return
     */
    public static String getNowMonthLast() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        return formatDate(c.getTime());
    }

    /**
     * 拿到某一月的第一天
     *
     * @param month
     * @return
     */
    public static String getMonthFirst(int month) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
        return formatDate(c.getTime());
    }

    /**
     * 拿到当前月最后一天
     *
     * @return
     */
    public static String getMonthLast(int month) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        return formatDate(c.getTime());
    }

    /**
     * 拿到当前月的前一个月
     *
     * @return
     */
    public static String getBeforeMonth() {
        Calendar cq = Calendar.getInstance();
        cq.add(Calendar.MONTH, -1);
        cq.set(Calendar.DAY_OF_MONTH, 1);
        return formatDate(cq.getTime());
    }

    /**
     * 拿到当前月的后一个月
     *
     * @return
     */
    public static String getNextMonth() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, +1);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        return formatDate(c.getTime());
    }

    /**
     * 拿到今天的月-日
     *
     * @return
     */
    public static String getNowMonthDay() {
        Calendar cal = Calendar.getInstance();//使用日历类
        String month = String.valueOf(cal.get(Calendar.MONTH) + 1);//得到月，因为从0开始的，所以要加1
        String day = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));//得到天
        return month + "月" + day + "日";
    }

    /**
     * 转时间戳为可读日期
     *
     * @param in_time
     * @param mun     是否添加“00:00:00”
     * @return
     */
    public static String datePress(String in_time, int mun) {
        if ("-1".equals(in_time) || in_time == null || "".equals(in_time)) {
            return "-1";
        }
        SimpleDateFormat format = new SimpleDateFormat(DEFAULT_FORMAT);
        Date date = null;
        try {

            date = format.parse(in_time);
            if (mun == 0) {
                return in_time + " 00:00:00";
            }
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(date);
            calendar.add(calendar.DATE, mun);
            return format.format(calendar.getTime()) + " 00:00:00";
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 格式化时间为北京时间格式
     * @param date
     * @return
     */
    public static String formatDateAsBJ (String time) {
        try{
            SimpleDateFormat f = new SimpleDateFormat(DEFAULT_FORMAT_AS_BJ);
            String sDate = f.format(formatDate(time));
            return sDate;
        }catch (Exception e){
        }
        return null;
    }

    /**
     * 计算两个时间差为秒
     * @param thisTime
     * @param nowTime
     * @return
     */
    public static Long leadTimeAsSec(Date thisTime,String nowTime){
        return  (thisTime.getTime()-TimeToLong(nowTime))/1000;
    }


    /**
     * 时间转换为时间戳
     * @param times - 时间字符串
     * @return
     */
    public static long TimeToLong(String times){
        SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date begin = dfs.parse(times);
            long between=begin.getTime();
            return between;
        } catch (Exception e) {
            return 0;
        }
    }
}
