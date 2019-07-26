package com.zhou.pattern.memento;

import java.util.LinkedList;
import java.util.Stack;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * 负责人类
 * 负责管理备忘录对象
 * 使用 栈
 *
 * 当对象太多，占用内存资源，可以使用序列化存储到文件
 *
 */
public class CareTaker {
    //使用栈式存储
    private LinkedList<EmpMemento> stacks = new LinkedList<>();

    public boolean push(EmpMemento empMemento){
        return stacks.offer(empMemento);
    }

    public EmpMemento pop(){
        return stacks.pollLast();
    }

}
