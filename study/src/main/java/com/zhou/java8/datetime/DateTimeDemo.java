package com.zhou.java8.datetime;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * JDK8 新的时间API
 * 1. LocalDate LocalTime LocalDateTime ISO 标准时间  @test4()
 * 2. Instant 时间戳(UNIX 元年 1970-01-01 00:00:00)到某个时间的毫秒值 @test5()
 * 3. Duration(持续时间) 计算时间之间的间隔
 *         获取   秒 getSecond  纳秒 getNano
 *         注意： 毫秒 toMillis
 *    Period(时期) 计算日期之间的间隔
 * 4. TemporalAdjuster 时间校正器 用于获取未来的时间  @see test6
 * 5. DateTimeFormatter 格式化器
 *        使用定义好的格式 DateTimeFormatter.XXXX
 *        使用自定义格式   DateTimeFormatter.ofPattern(patten)
 * 6. ZonedDate  ZonedTime  ZonedDateTime 时区  @see test7()
 */
public class DateTimeDemo {

    private final static Logger LOGGER = LoggerFactory.getLogger(DateTimeDemo.class);
    /**
     * 这个方法可以测试出SimpleDateFormat的线程不安全
     */
    @Test
    public void test1(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Callable<String> task = () -> {
            Date date = sdf.parse("20181126");
            return sdf.format(date);
        };
        try {
            multiThreadParse(task);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    /**
     * 在JDK8以前解决线程不安全问题
     */
    @Test
    public void test2() {
        Callable<String> task = () -> {
            Date date = DateFormatThreadLocal.parse("20181126153624", DateFormatThreadLocal.yyyyMMddhhmmss);
            return DateFormatThreadLocal.format(date, DateFormatThreadLocal.yyyyMMddhhmmss);
        };
        try {
            multiThreadParse(task);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void multiThreadParse(Callable<String> task)
            throws InterruptedException, ExecutionException {
        ExecutorService pool = Executors.newFixedThreadPool(10);
        List<Future<String>> results = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            results.add(pool.submit(task));
        }
        for (Future<String> future : results) {
            System.out.println(future.get());
        }
        pool.shutdown();
    }

    /**
     * JDK8中推荐使用方式
     */
    @Test
    public void test3(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
        Callable<LocalDate> task = () -> LocalDate.parse("20181126", dtf);
        ExecutorService pool = Executors.newFixedThreadPool(10);
        List<Future<LocalDate>> results = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            results.add(pool.submit(task));
        }
        try {
            for (Future<LocalDate> future : results) {
                System.out.println(future.get());
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        pool.shutdown();
    }


    /**
     * LocalDate LocalTime LocalDateTime ISO 标准时间
     */
    @Test
    public void test4(){
        LocalDateTime ldt = LocalDateTime.now();
        System.out.println(ldt);
        System.out.println("--------------------------");
        LocalDateTime time = LocalDateTime.of(2015, 10, 19, 22, 56, 59);
        System.out.println(time);
        System.out.println("--------------------------");
        LocalDateTime plusYears = ldt.plusYears(2);
        System.out.println(plusYears);
        System.out.println("--------------------------");
        LocalDateTime minusMonths = ldt.minusMonths(2);
        System.out.println(minusMonths);
        System.out.println(minusMonths.getYear());
        System.out.println(minusMonths.getMonthValue());
        System.out.println(minusMonths.getDayOfWeek());
        System.out.println(minusMonths.getDayOfMonth());
        System.out.println(minusMonths.getDayOfYear());
        System.out.println("--------------------------");
    }

    /**
     * Instant 时间戳(UNIX 元年 1970-01-01 00:00:00)到某个时间的毫秒值
     * 默认获取UTC时区 格林威治时间
     */
    @Test
    public void test5(){
        Instant now = Instant.now();
        System.out.println(now);

        OffsetDateTime atOffset = now.atOffset(ZoneOffset.ofHours(8));
        System.out.println(atOffset);
    }

    /**
     * TemporalAdjuster
     */
    @Test
    public void test6(){
        LocalDateTime ldt = LocalDateTime.now();
        System.out.println("当前时间:"+ldt);
        System.out.println("----------------------");
        LocalDateTime ldt2 = ldt.withDayOfMonth(10);
        System.out.println("这个月的十号:"+ldt2);
        System.out.println("----------------------");
        //已经实现的时间校正器
        LocalDateTime ldt3 = ldt.with(TemporalAdjusters.next(DayOfWeek.SUNDAY));
        System.out.println("下一个周日:"+ldt3);
        System.out.println("----------------------");
        //自定义时间校正器 下一个工作日
        LocalDateTime ldt5 = ldt.with((l) -> {
            LocalDateTime ldt4 = (LocalDateTime) l;
            DayOfWeek dow = ldt4.getDayOfWeek();
            if (dow.equals(DayOfWeek.FRIDAY)) {
                return ldt4.plusDays(3);
            } else if (dow.equals(DayOfWeek.SATURDAY)) {
                return ldt4.plusDays(2);
            } else {
                return ldt4.plusDays(1);
            }
        });
        System.out.println("下一个工作日:"+ldt5);
        System.out.println("----------------------");
    }

    @Test
    public void test7(){
        //获取所有有效的时区
        Set<String> availableZoneIds = ZoneId.getAvailableZoneIds();
        String s = availableZoneIds.stream().collect(Collectors.joining(","));
        System.out.println(s);
        System.out.println("--------------------");
        // 根据时区获得时间
        LocalDateTime ldt = LocalDateTime.now(ZoneId.of("Asia/Aden"));
        System.out.println(ldt);
        System.out.println("--------------------");

        //获取当地时间 然后标识与指定时区偏移值
        LocalDateTime ldt2 = LocalDateTime.now();
        System.out.println(ldt2);
        ZonedDateTime ldt3 = ldt2.atZone(ZoneId.of("Asia/Aden"));
        System.out.println(ldt3);
        System.out.println("--------------------");

        // 根据指定时区获得时间，并标识与当地的偏移值
        LocalDateTime ldt4 = LocalDateTime.now(ZoneId.of("Asia/Aden"));
        System.out.println(ldt4);
        ZonedDateTime ldt5 = ldt4.atZone(ZoneId.of("Asia/Aden"));
        System.out.println(ldt5);
        System.out.println("--------------------");


    }
}
