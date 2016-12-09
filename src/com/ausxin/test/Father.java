package com.ausxin.test;

/**
 * Created by lushuqin on 2016/11/21.
 */
public class Father implements Cloneable{
    private String name ;
    Father() {
        System.out.println("Father");
        System.out.println(getClass().getName());
    }

    public Father(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
