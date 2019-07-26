package com.zhou.pattern.memento;

/**
 * 备忘录类
 * 原示例为新建同构的新类，我这里直接使用了继承
 */
public class EmpMemento extends Emp {

    public EmpMemento(Emp e) {
        setName(e.getName());
        setSalary(e.getSalary());
    }


}
