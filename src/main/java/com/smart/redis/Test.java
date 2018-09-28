package com.smart.redis;

import java.util.ArrayList;

public class Test {
    public static void main(String[] args){
        ArrayList list = new ArrayList();
        list.add("123");
        list.add("12");
        list.add("wer");
        System.out.println(list.get(1));
        list.remove("12");
        System.out.println(list.get(1));

        SimpleList simpleList = new SimpleList();
        System.out.println(simpleList.size());
        simpleList.add("123");
        simpleList.add(123);
        simpleList.add("12");
        simpleList.add("qwe");
        System.out.println(simpleList.get(2));
        simpleList.remove("12");
        System.out.println(simpleList.get(2));
    }
}
