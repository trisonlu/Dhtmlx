package com.ausxin.test;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by lushuqin on 2016/11/21.
 */
public class ClassTest {

    public static void main(String[] args) throws Exception{
        ClassTest classTest = new ClassTest();

        //classTest.getClasses();
        //cloneTest();
        //stringTest();
        //linkedListTest();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = dateFormat.parse("2016-10-10");
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date1 = dateFormat1.format(date);
        System.out.println(date1);
    }

    private static void linkedListTest() {
        LinkedList<String> list = new LinkedList<String>();
        list.add("one");
        list.add("two");
        list.add("three");
        list.add("four");
        list.push("onnn");

        Iterator iterator = list.listIterator();
        System.out.println(iterator.next());
        System.out.println(iterator.next());

        System.out.println(iterator.next());
        System.out.println(iterator.next());
        System.out.println(iterator.next());


    }

    private static void stringTest() {
        Vector<String> vector = new Vector<String>(2,2);
        vector.add("one");
        System.out.println(vector.capacity());
        vector.add("one");
        System.out.println(vector.capacity());
        vector.add("one");
        System.out.println(vector.capacity());
        vector.add("one");
        System.out.println(vector.capacity());
        vector.add("one");
        System.out.println(vector.capacity());
        vector.addElement("one");

        int first = 4;
        int second = first >> 1;


        System.out.println(vector.capacity());
        System.out.println(second);
        vector.add(null);
        for (int i=0;i<vector.size();i++){
            System.out.println(vector.get(i));
        }
        System.out.println(vector.capacity());
        //List<String> list = Collections.synchronizedList(new ArrayList<String>());
        ArrayList<String> list = new ArrayList<String>();
        list.add(null);
        System.out.println(list.size());
        LinkedList<String> list1 = new LinkedList<String>();
        list1.add(null);
        System.out.println(list1.size());
    }

    private static void cloneTest() throws CloneNotSupportedException {
        Employee e1 = new Employee(1,new Date(2016, 11, 1),"employee1",new Father("father1"));
        Employee e2 = (Employee) e1.clone();
        Employee e3 = e1;
        //e2.setInDate(new Date(2016,11,2));
        e2.setName("employee2");
        e2.setAge(2);
        //e2.setFather(new Father("father2"));
        e2.getFather().setName("father2");
        e2.getInDate().setTime(333);
        //e3.setInDate(new Date(2016,11,3));
        e3.setName("employee3");
        e3.setAge(3);
        //e3.setFather(new Father("father3"));
        System.out.println(e1.toString());
        System.out.println(e2.toString());
        System.out.println(e3.toString());
    }

    public void getClasses(){
        Grandson x = new Grandson();
        Son one = new Son();
        one = (Son)x;
        Father two = (Father)x;
        if (one == two){
            System.out.println("==");
        } else {
            System.out.println("!=");
        }
        System.out.println(x.hashCode());
        System.out.println(one.hashCode());
        System.out.println(two.hashCode());
        System.out.println(x.toString());
        System.out.println(one.toString());
        System.out.println(two.toString());
        System.out.println("===============");

        System.out.println(this.getClass().getName());
        System.out.println(x.getClass().getSuperclass().getName());
        System.out.println(Grandson.class.getName());
        try {
            System.out.println(Class.forName("com.ausxin.test.Grandson").getName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
