package com.zhou.java8.streams;

import org.junit.Test;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 一 Stream 的三个操作步骤
 *    1. 创建Stream
 *    2. 中间操作
 *    3. 终止操作(终端操作)
 *
 *  惰性求值：执行终止操作时，一次性执行全部内容
 *
 */
public class StreamDemo {

    private List<String> alist = Arrays.asList("tom", "allen", "jim","tom");


    @Test
    public void test1(){
        //1.通过Collection系列的stream()或者parallelStream()
        List<String> list = new ArrayList<>();
        Stream<String> stream1 = list.stream();

        //2.通过Arrays.stream(T [] t)
        String[] strings = new String[10];
        Stream<String> stream2 = Arrays.stream(strings);

        //3.Stream.of()
        Stream<String> stream3 =  Stream.of("a", "b", "c");


        //4.创建无限流
        Stream<Integer> stream4 = Stream.iterate(0,(x) -> x+2);
        stream4.limit(5).forEach(System.out::println);

        System.out.println("--------------------------");

        Stream<Double> stream5 = Stream.generate(() ->  Math.random() * 100);
        stream5.limit(5).forEach(System.out::println);

    }

    @Test
    public void test2(){
        /**
         * 筛选与切片
         * filter 接收 Predicate Lambda 从流中筛选元素
         * limit  阶段流，使其元素不超过给定的数量
         * skip   跳过元素，返回一个扔掉了前n个元素的流，若个数不足n则返回一个空流。与limit(n)互补
         * distinct 筛选，根据元素的hashCode()和equals()去除重复元素
         */
        alist.stream().filter(e -> "tom".equalsIgnoreCase(e)).forEach(System.out::println);
        System.out.println("=============");
        alist.stream().limit(3).forEach(System.out::println);
        System.out.println("=============");
        alist.stream().skip(2).forEach(System.out::println);
        System.out.println("=============");
        alist.stream().distinct().forEach(System.out::println);
    }

    @Test
    public void test3(){
        /**
         * 映射
         * map 接收Lambda，将元素转换成其他形式或提取信息。
         * 接收一个函数作为参数，该函数会被应用到每个元素上，并将其映射成一个新的元素。
         * flatMap 接收一个函数作为参数，将流中的每个值都换成另一个流，然后把所有流连接成一个流
         *
         * map flatMap  类比---->  add addAll
         */
        int[] arr = {10,9,8,7,6,5,4,3,2,1,0};
        int index = 0;
        Function<Integer,Integer> fun = e-> {
            int nextIndex = (e+1) % arr.length;
            System.out.printf("nextIndex : %d arr[index] : ",nextIndex);
            e = nextIndex;
            System.out.println(arr[e]);
            return arr[e];
        };

        //构建一个数组
        ArrayList<Integer> arrayList = Stream.iterate(0, x -> x + 1)
                .limit(100)
                .collect(Collectors.toCollection(ArrayList::new));

        //使用map构建
        ArrayList<Integer> collect = Stream.iterate(0, x -> x + 1)
                .limit(100).map(fun).collect(Collectors.toCollection(ArrayList::new));


    }

    public void test04(){
        /**
         * 排序  sort()  自然排序，调用对象的compareTo
         *      sort(Comparator c) 自定义排序器   sort((e1,e2) - > {})
         */
    }

    public void test05(){
        /**
         * 查找与匹配(终止操作)
         * allMatch   检查是否匹配所有元素
         * anyMatch   检查是否至少匹配一个元素
         * noneMatch  检查是否没有匹配所有元素
         * findFirst  返回第一个元素
         * findAny    返回当前流中的任意元素
         * count      返回当前流中的总个数
         * max        返回流中最大值
         * min        返回流中最小值
         */
    }

    @Test
    public void test06(){
        /**
         * 归约  reduce(T identity ,BinaryOperator)  可以将流中元素反复结合起来，得到一个值
         *
         */
        //可以用来实现求和效果
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        // 注意  reduce 中的 identity 起始值，不是流中的第一个
        // 本例中 第一次循环时  e1=0 e2=1 e1+e2=1 第二次循环 e1=1,e2=2 e1+e2=3
        // 第三次循环 e1=3,e2=3 e1+e2=6
        Integer reduce = list.stream().reduce(0, (e1, e2) -> e1 + e2);
        System.out.println(reduce);

        Optional<Integer> optional = list.stream().reduce(Integer::sum);
        System.out.println(optional.get());
    }

    @Test
    public void test07(){
        /**
         * 收集 collect 将流转换为其他形式。接收一个Collector接口的实现
         * 用于给Stream 中的元素做汇总的方法
         * .stream().map().collect(Collectors.toList())
         * .stream().map().collect(Collectors.toSet())
         * .stream().map().collect(Collectors.toCollection(HashSet::new))
         * Collectors.counting
         * Collectors.averagingDouble
         * Collectors.summingDouble
         */
        // 组函数
        List<Double> list = Arrays.asList(55.5, 234.2, 343453.3, 534534.2);
        DoubleSummaryStatistics dss = list.stream().collect(Collectors.summarizingDouble(e -> {
            return e;
        }));
        System.out.println(dss.getAverage());
        System.out.println(dss.getSum());

        //List转字符串时使用，可以减少自己组装字符串去掉最后一个逗号的麻烦
        List<String> strings = Arrays.asList("asfwe", "2342dfs", "asdfwebv");
        String s = strings.stream().collect(Collectors.joining(","));
        System.out.println(s);

    }












}
