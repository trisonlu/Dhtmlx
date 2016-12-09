package com.ausxin;

import com.ausxin.domain.NormalDomain;

/**
 * Created by lushuqin on 2016/11/4.
 */
public class NoramlTest {

    public static void main(String[] args){
        NormalDomain n1 = new NormalDomain("张三","004");
        NormalDomain n2 = new NormalDomain("张三","004");
        System.out.println(n1 == n2);
        System.out.println(n1.equals(n2));
    }
}
