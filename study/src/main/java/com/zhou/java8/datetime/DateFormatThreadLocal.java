package com.zhou.java8.datetime;

import com.google.common.base.Preconditions;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 解决SimpleDateFormat的线程安全问题
 */
public class DateFormatThreadLocal {

    public static final String yyyyMMdd = "yyyyMMdd";
    public static final String yyyyMMddhhmmss = "yyyyMMddhhmmss";

    private static final ThreadLocal<DateFormat> DF_yyyyMMdd = ThreadLocal.withInitial(() -> new SimpleDateFormat(yyyyMMdd));
    private static final ThreadLocal<DateFormat> DF_yyyyMMddhhmmss = ThreadLocal.withInitial(() -> new SimpleDateFormat(yyyyMMddhhmmss));

    private static Map<String,ThreadLocal<DateFormat>> MAPPING = new HashMap<>();
    static {
        MAPPING.put(yyyyMMdd, DF_yyyyMMdd);
        MAPPING.put(yyyyMMddhhmmss, DF_yyyyMMddhhmmss);
    }

    public static Date parse(String source) throws ParseException {
        return DF_yyyyMMdd.get().parse(source);
    }

    public static String format(Date date){
        return DF_yyyyMMdd.get().format(date);
    }

    /**
     *
     * @param source
     * @param patten DateFormatThreadLocal.yyyyMMdd   DateFormatThreadLocal.yyyyMMddhhmmss
     * @return
     * @throws ParseException
     */
    public static Date parse(String source,String patten) throws ParseException {
        Preconditions.checkArgument(MAPPING.containsKey(patten));
        return MAPPING.get(patten).get().parse(source);
    }

    public static String format(Date date,String patten){
        Preconditions.checkArgument(MAPPING.containsKey(patten));
        return MAPPING.get(patten).get().format(date);
    }



}
