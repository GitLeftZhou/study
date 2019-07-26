package com.zhou.algorithm;

import org.junit.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AlgorithmDemo {
    /**
     * 轮询算法
     */
    @Test
    public void polling(){
        int[] arr = {10,9,8,7,6,5,4,3,2,1,0};
        int index = 0;
        for (int i = 0; i < 22; i++) {
            int nextIndex = (index+1) % arr.length;
            System.out.printf("nextIndex : %d arr[index] : ",nextIndex);
            index = nextIndex;
            System.out.println(arr[index]);
        }

//        Function<Integer,Integer> fun = e-> {
//            int nextIndex = (e+1) % arr.length;
//            System.out.printf("nextIndex : %d arr[index] : ",nextIndex);
//            e = nextIndex;
//            System.out.println(arr[e]);
//            return arr[e];
//        };

//        //构建一个数组
//        ArrayList<Integer> arrayList = Stream.iterate(0, x -> x + 1)
//                .limit(100)
//                .collect(Collectors.toCollection(ArrayList::new));

//        ArrayList<Integer> collect = Stream.iterate(0, x -> x + 1)
//                .limit(100).map(fun).collect(Collectors.toCollection(ArrayList::new));
//
//        for (int i = 0; i < collect.size(); i++) {
//            System.out.println(collect.get(i));
//        }

    }

    /**
     * 随机加权算法
     */
    @Test
    public void weight() {
        Map<String, Integer> servers = new HashMap<String, Integer>();
        servers.put("A", 1);
        servers.put("B", 2);
        servers.put("C", 3);
        servers.put("D", 4);
        Map<String,Integer> map = new HashMap<String,Integer>();
        String key = null;
        int requests = 10000;

        Instant timeStamp1 = Instant.now() ;
        // 统计获取次数
        for(int i=0;i<requests;i++) {
            key = selectServer(servers);
            if (map.containsKey(key)) {
                map.put(key, map.get(key) + 1);
            } else {
                map.put(key, 1);
            }
        }
        for(String key1:map.keySet()){
            System.out.println(key1+" : "+map.get(key1));
        }
        Instant timeStamp2 = Instant.now() ;
        System.out.println("算法1耗时" + Duration.between(timeStamp1,timeStamp2).toNanos());

        map.clear();

        timeStamp1 = Instant.now() ;
        List<String> arrays = new ArrayList<>(8);
        for (Iterator<Map.Entry<String, Integer>> iterator = servers.entrySet().iterator(); iterator.hasNext(); ) {
            Map.Entry<String, Integer> next = iterator.next();
            int weight =  next.getValue();
            for (int i = 0; i < weight; i++) {
                arrays.add(next.getKey());
            }
        }
        // 统计获取次数
        for(int i=0;i<requests;i++) {
            key = selectServer2(arrays);
            if (map.containsKey(key)) {
                map.put(key, map.get(key) + 1);
            } else {
                map.put(key, 1);
            }
        }
        for(String key1:map.keySet()){
            System.out.println(key1+" : "+map.get(key1));
        }
        timeStamp2 = Instant.now() ;
        System.out.println("算法2耗时" + Duration.between(timeStamp1,timeStamp2).toNanos());
    }


    public void test(){
        String abc = "abc";
        if (abc != null) {

        }
    }


    /**
     * 随机加权算法1
     * @param servers
     * @return
     */
    private String selectServer(Map<String, Integer> servers) {
        if (servers == null || servers.size() == 0){
            return null;
        }
        Integer sum = 0;
        Set<Map.Entry<String, Integer>> entrySet = servers.entrySet();
        Iterator<Map.Entry<String, Integer>> iterator = entrySet.iterator();
        while (iterator.hasNext()) {
            sum += iterator.next().getValue();
        }
        Integer rand = new Random().nextInt(sum) + 1;
        for (Map.Entry<String, Integer> entry : entrySet) {
            rand -= entry.getValue();
            if (rand <= 0) {
                return entry.getKey();
            }
        }
        return null;
    }

    /**
     * 随机加权算法2
     * @param arrays
     * @return
     */
    private String selectServer2(List<String> arrays) {
        if (arrays == null || arrays.size() == 0){
            return null;
        }
        Integer rand = new Random().nextInt(arrays.size());
        return arrays.get(rand % arrays.size());
    }

}
