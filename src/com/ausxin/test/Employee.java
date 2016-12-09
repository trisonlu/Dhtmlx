package com.ausxin.test;

import java.util.Date;

/**
 * Created by lushuqin on 2016/11/22.
 */
public class Employee implements  Cloneable{
    private String name;
    private Date inDate;
    private int  age;
    private Father father;

    public Employee(int age, Date inDate, String name,Father father) {
        this.age = age;
        this.inDate = inDate;
        this.name = name;
        this.father = father;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getInDate() {
        return inDate;
    }

    public void setInDate(Date inDate) {
        this.inDate = inDate;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Father getFather() {
        return father;
    }

    public void setFather(Father father) {
        this.father = father;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        Employee clone = (Employee) super.clone();
        clone.inDate = (Date) inDate.clone();
        clone.father = (Father) father.clone();
        return clone;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", inDate=" + inDate +
                ", age=" + age +
                ", father=" + (father!=null?father.getName():"") +
                '}';
    }
}
