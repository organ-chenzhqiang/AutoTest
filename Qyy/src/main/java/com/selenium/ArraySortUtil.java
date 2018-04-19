package com.selenium;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArraySortUtil {
    public static List<String[]> aaList() {
        String[] arr1 = {"100米", "305米"};
        String[] arr2 = {"移动", "联通", "电信"};
        String[] arr3 = {"64", "128", "264", "32"};
        String[] arr4 = {"iphone7", "iphone7 plus", "iphone6 plus", "iphone6", "iphone8", "iphone8 plus"};
        String[] arr5 = {"港版", "大陆版", "美版", "日版"};
        String[] arr6 = {"红色","黄色"};
        List<String[]> list = new ArrayList<>();
        list.add(arr1);
        list.add(arr2);
        list.add(arr3);
        list.add(arr4);
        list.add(arr5);
        list.add(arr6);
        List<String[]> aaList = new ArrayList<>();
        test(list, arr1, "", aaList);
        return aaList;
    }

    public static void main(String[] args) {
        for (int i = 0; i < aaList().size(); i++) {
            System.out.println(Arrays.toString(aaList().get(i)));
        }

    }


    public static void test(List<String[]> list, String[] arr, String str, List<String[]> aaList) {
        for (int i = 0; i < list.size(); i++) {
            //取得当前的数组
            if (i == list.indexOf(arr)) {
                //迭代数组
                for (String st : arr) {
                    st = str + st + ",";
                    if (i < list.size() - 1) {
                        test(list, list.get(i + 1), st, aaList);
                    } else if (i == list.size() - 1) {
                        aaList.add(st.split(","));
                    }
                }
            }
        }
    }
}
