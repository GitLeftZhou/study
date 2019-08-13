package com.zhou.jstack;

import java.time.Duration;
import java.time.Instant;

/**
 * 注：一定在linux用户下执行,java程序执行用户（root用户不能操作其他用户jstack）
 *  1.   jps 找到java命令的pid
 *  2.   top -Hp pid 查看进程下耗时的线程nid
 *  3.   printf "%x\n" nid 转换10进制为16进制
 *  4.   jstack pid | grep OX_nid > filename.txt  只查询耗时线程信息
 *  5.   jstack -l pid > pid.txt  查看线程内存快照，详细信息
 */
public class JstackDemo extends Thread {
    @Override
    public void run() {
        Instant bgn = Instant.now();
        Instant durationBgn = Instant.now();
        boolean flag = true;
        while(flag){
            double a = Math.random() * Math.random();
            Instant durationEnd = Instant.now();
            if (Duration.between(durationBgn, durationEnd).getSeconds()>3){
                durationBgn = Instant.now();;
                System.out.println(Thread.currentThread().getName()+" is running....");
            }
            if (Duration.between(bgn,durationBgn).toMinutes()>5){
                flag = false;
                System.out.println(Thread.currentThread().getName()+" is dead....");
            }
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new JstackDemo().start();
        }
    }
}
