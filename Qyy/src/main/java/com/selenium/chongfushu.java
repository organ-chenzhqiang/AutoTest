package com.selenium;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class chongfushu {
    public int[] a(int n[]) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < n.length; i++) {
            if (map.get(n[i]) == null) {
                int v = 0;
                for (int j = i + 1; j < n.length; j++) {
                    if (n[i] == n[j]) {
                        v++;
                        map.put(n[i], v);
                    }
                }
            }
        }
        Set<Map.Entry<Integer, Integer>> entrySet = map.entrySet();
        int[] b = new int[map.size()];
        int e = 0;
        for (Map.Entry<Integer, Integer> entry : entrySet) {
            b[e] = entry.getKey();
            e++;
        }
        for (int i = 0; i < map.size(); i++) {
            for (int j = i + 1; j < map.size(); j++) {
                if (map.get(b[i]) > map.get(b[j])) {
                    int temp = b[i];
                    b[i] = b[j];
                    b[j] = temp;
                }
            }
        }
        return b;
    }

    public static void main(String[] args) {
        int[] arr = { 1, 2, 3, 4, 5, 7, 1, 2, 3, 4, 6, 7, 1, 2, 3, 1, 99, 23, 2 };
        System.out.println(Arrays.toString(new chongfushu().a(arr)));
    }
}

