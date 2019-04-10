package com.telfa.andrei.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Date工具类
 * @since 1.8
 */
public class DateUtil {

    /**
     * 默认长日期格式
     */
    private static final DateTimeFormatter DEFAULT_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * 获取当前时间的秒数表示
     * @return 当前时间的秒数
     */
    public static long getDateline() {
        return System.currentTimeMillis() / 1000;
    }

    public static long getDateline(String date) {
        return (long) (toDate(date, "yyyy-MM-dd").getTime() / 1000);
    }

    public static long getDateline(String date,String pattern){
        return (long)(toDate(date, pattern).getTime()/1000);
    }

    /**
     * 将一个字符串转换成日期格式
     *
     * @param date
     * @param pattern
     * @return
     */
    public static Date toDate(String date, String pattern) {
        if (("" + date).equals("")) {
            return null;
        }
        if (pattern == null) {
            pattern = "yyyy-MM-dd";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date newDate = new Date();
        try {
            newDate = sdf.parse(date);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return newDate;
    }

    /**
     * 获取当前日期的年/月/日
     * @return int[0]代表年, int[1]代表月, int[2]代表日
     */
    public static int[] getCurrentYearMonthDay() {
        LocalDate localDate = LocalDate.now();
        return new int[] { localDate.getYear(), localDate.getMonthValue(), localDate.getDayOfMonth() };
    }

    /**
     * 日期字符串转秒
     * @param date 日期字符串, 日期字符串格式yyyy-MM-dd HH:mm:ss
     * @return 毫秒
     */
    public static long strToSeconds(String date) {
        LocalDateTime localDateTime = LocalDateTime.parse(date, DEFAULT_DATE_TIME_FORMATTER);
        return localDateTime.atZone(ZoneId.systemDefault()).toInstant().getEpochSecond();
    }
    
    /**
     * 把long型转换成字符串型
     * @param time
     * @param pattern
     * @return
     */
    public static String toString(Long time,String pattern){
        if(time>0){
            if(time.toString().length()==10){
                time = time*1000;
            }
            Date date = new Date(time);
            String str  = DateUtil.toString(date, pattern);
            return str;
        }
        return "";
    }
    
    /**
     * 把日期转换成字符串型
     * @param date
     * @param pattern
     * @return
     */
    public static String toString(Date date, String pattern){
        if(date == null){
            return "";
        }
        if(pattern == null){
            pattern = "yyyy-MM-dd";
        }
        String dateString = "";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            dateString = sdf.format(date);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return dateString;
    }
    
    /**
     * 计算时间差 （分钟）
     * @param time1       格式"yyyy-MM-dd HH:mm:ss"
     * @param time2     格式"yyyy-MM-dd HH:mm:ss"
     * @return
     */
    public static Integer getMinutes(String time1,String time2) {
        Integer days = 0;
        Integer minutes = null;
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date d1 = df.parse(time1);
            Date d2 = df.parse(time2);
            long diff = Math.abs(d1.getTime() - d2.getTime());//这样得到的差值是微秒级别
            long hours = (diff-days*(1000 * 60 * 60 * 24))/(1000* 60 * 60);
            minutes = (int) (diff/(1000* 60));
            return minutes;    
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return minutes;
    }

}
