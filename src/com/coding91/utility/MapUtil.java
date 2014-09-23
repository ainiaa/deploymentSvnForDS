/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coding91.utility;

import java.io.File;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.tmatesoft.svn.core.wc.SVNStatus;
import org.tmatesoft.svn.core.wc2.SvnStatus;

/**
 *
 * @author Administrator
 */
public class MapUtil {

    public static enum SortingOrder {

        /**
         * Resulting sort will be from smaller to biggest.
         */
        ASCENDING,
        /**
         * Resulting sort will be from biggest to smallest.
         */
        DESCENDING
    }

    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map, final SortingOrder sortingOrder) {
        List<Map.Entry<K, V>> list = new LinkedList<>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
            @Override
            public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
                int compare = (o1.getValue()).compareTo(o2.getValue());
                switch (sortingOrder) {
                    case ASCENDING:
                        return compare;
                    case DESCENDING:
                        return (-1) * compare;
                }
                return 0;
            }
        });

        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }

    public static <K extends Comparable<? super K>, V> Map<K, V> sortByKey(Map<K, V> map, final SortingOrder sortingOrder) {

        List<Map.Entry<K, V>> list = new LinkedList<>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
            @Override
            public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
                int compare = (o1.getKey()).compareTo(o2.getKey());
                switch (sortingOrder) {
                    case ASCENDING:
                        return compare;
                    case DESCENDING:
                        return (-1) * compare;
                }
                return 0;
            }
        });

        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }

    public static void foreach(Map map) {
        Iterator iterator = map.keySet().iterator();
        System.out.println("start ====");
        while (iterator.hasNext()) {
            String propertyName = (String) iterator.next();
            String propertyValue = (String) map.get(propertyName);
            System.out.println(propertyName + "=>" + propertyValue);
        }
        System.out.println("end ====");
    }

    public static void foreach(Map<File, SvnStatus> map, boolean t) {
        Iterator iterator = map.keySet().iterator();
        System.out.println("start ====");
        while (iterator.hasNext()) {
            File f = (File) iterator.next();
            String propertyName = f.getName();
            String propertyValue;
            SvnStatus svnStatus = (SvnStatus) map.get(f);
            propertyValue = svnStatus.getNodeStatus().toString();
            System.out.println(propertyName + "=>" + propertyValue);
        }
        System.out.println("end ====");
    }

}
