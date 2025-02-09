package org.Game2D.v0.rendertest;

import java.util.ArrayList;

public class Pointers {
    public static void main(String[] args){
        int a = 5;
        int b = a;
        a++;
        System.out.println(b);

        ArrayList<Integer> list1 = new ArrayList<>();
        list1.add(1);
        list1.add(2);
        ArrayList<Integer> list2 = list1;
        list1.add(3);
        for(int i : list2){
            System.out.println(i);
        }
    }
}
