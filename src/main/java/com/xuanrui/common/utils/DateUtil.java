package com.xuanrui.common.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间补充方法
 *
 * @author XuLuJiao
 * @version 1.0.0
 * @since 2018年3月7日上午8:50:16
 */
public class DateUtil {
    /**
     * 获取现在时间
     *
     * @return String 格式:yyyyMMddHHmmss
     */
    public static String getNowDate() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * 获取一个星期以前的时间
     *
     * @return String 格式:yyyyMMddHHmmss
     */
    public static String getDateAWeekAgo() {
        long times = System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000;
        Date aWeekAgo = new Date(times);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String dateString = formatter.format(aWeekAgo);
        return dateString;
    }

    /**
     * 获取前后时间
     *
     * @param field  日/周/月/年
     * @param amount 差值
     * @return String 格式:yyyyMMddHHmmss
     */
    public static String getBeforeOrAfterDate(int field, int amount) {
        Calendar now = Calendar.getInstance();
        now.add(field, amount);
        Date dateTime = new Date(now.getTimeInMillis());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        return formatter.format(dateTime);
    }
}
