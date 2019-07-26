package com.zhou.pattern.memento;

/**
 * 备忘录模式
 * 保存某个对象内部状态的拷贝，用以恢复到以前的状态
 *
 * 结构：
 * 1.源发器 Originator
 * 2.备忘录类 Memento
 * 3.负责人类 CareTake
 */
public class Client {
    public static void main(String[] args) {
        CareTaker taker = new CareTaker();
        Emp emp = new Emp("zhangsan", 5000);
        taker.push(emp.memento());//第一个回滚点
        System.out.println("第一次打印对象:"+emp);
        emp.setSalary(8000);
        taker.push(emp.memento());//第二个回滚点
        System.out.println("第二次打印对象:"+emp);
        emp.setSalary(10000);
//        taker.push(emp.memento());
        System.out.println("第三次打印对象:"+emp);
        emp.recovery(taker.pop());
        System.out.println("恢复到第二次:"+emp);
        emp.recovery(taker.pop());
        System.out.println("恢复到第一次:"+emp);
    }
}
