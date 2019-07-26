package com.zhou.pattern.memento;

/**
 * 这是一个源发器类  Originator
 */
public class Emp {
    private String name;
    private double salary;

    public Emp(String name, double salary) {
        this.name = name;
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Emp{" +
                "name='" + name + '\'' +
                ", salary=" + salary +
                '}';
    }

    public Emp() {
    }

    //进行备忘操作  并返回备忘录对象
    public EmpMemento memento(){
        return new EmpMemento(this);
    }
    //进行数据恢复
    public boolean recovery(EmpMemento eMemento){
        if(null != eMemento){
            setName(eMemento.getName());
            setSalary(eMemento.getSalary());
        } else {
            return false;
        }
        return true;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }



}
