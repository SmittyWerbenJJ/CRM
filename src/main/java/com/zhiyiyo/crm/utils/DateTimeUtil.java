package com.zhiyiyo.crm.utils;

import java.text.SimpleDateFormat;

import java.util.Date;

public class DateTimeUtil {

    /**
     * 获取系统当前时间
     * @return 日期时间字符串，格式为 yyyy-MM-dd HH:mm:ss
     */
    public static String getSysTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }

}
