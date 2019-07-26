package com.zhou.pattern.prototype;

import java.util.Date;

public class Sheep implements  Cloneable{
    private String name;
    private Date birthday;

    @Override
    protected Object clone() throws CloneNotSupportedException {
        Sheep obj = (Sheep) super.clone();
        // 克隆属性   深克隆
        obj.birthday = (Date) this.birthday.clone();
        return obj;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Sheep(String name, Date birthday) {
        this.name = name;
        this.birthday = birthday;
    }
}
