package com.zhou.java8.streams;

import java.time.Duration;
import java.time.Instant;
import java.util.OptionalLong;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

/**
 *  Fork(拆分)/Join(汇总) 框架
 *  1 继承 RecursiveTask 有返回值
 *        RecursiveAction  无返回值
 *  2 设置阀值
 *  3 拆分合并
 *
 */
public class ForkDemo extends RecursiveTask<Long> {

    private static final long serialVersionUID = 23425235342312L;
    private long start;
    private long end;
    //阀值
    private static final long THRESHOLD = 1000000L;
    private static final long MAX_NUMBER = 2000000L;

    public ForkDemo(long start, long end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        long length = end - start;
        if (length <= THRESHOLD){
            long sum = 0;
            for (long i = start;i <= end; i++){
                sum += i;
            }
            return sum;
        } else {
            long middle = (start + end) / 2;
            ForkDemo left = new ForkDemo(start, middle);
            left.fork();//ForkDemo left = new ForkDemo(start, middle);
            ForkDemo right = new ForkDemo(middle+1, end);
            right.fork();//拆分子任务，同时压入线程队列

            return left.join() + right.join();
        }
    }

    /**
     *  fork join
     */
    public static void test1(){
        Instant start = Instant.now();
        ForkJoinPool pool = new ForkJoinPool();
        ForkJoinTask<Long> task = new ForkDemo(0, MAX_NUMBER);
        Long sum = pool.invoke(task);
        System.out.println(sum);
        Instant end = Instant.now();
        System.out.println("fork join 耗费时间为:" + Duration.between(start, end).toMillis() + "毫秒");
    }

    /**
     * 普通for
     */
    public static void test2(){
        Instant start = Instant.now();
        long sum = 0;
        for (long i = 0; i <= MAX_NUMBER; i++) {
            sum += i;
        }
        System.out.println(sum);
        Instant end = Instant.now();
        System.out.println("for 耗费时间为:" + Duration.between(start, end).toMillis() + "毫秒");
    }

    /**
     * 并行流
     */
    public static void test3(){
        Instant start = Instant.now();
        OptionalLong sum = LongStream.rangeClosed(0, MAX_NUMBER)
                .parallel()
                .reduce(Long::sum);
        System.out.println(sum.getAsLong());
        Instant end = Instant.now();
        System.out.println("parallel map reduce 耗费时间为:" + Duration.between(start, end).toMillis() + "毫秒");
    }

    public static void main(String[] args) {
        test1();
        test2();
        test3();
    }

}
