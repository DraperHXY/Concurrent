package com.draper;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author draper_hxy
 */
public class VectorTest {

    public void addIfNotExist(Vector vector, Object o) {
        if (!vector.contains(o)) {
            vector.add(o);
        }
    }

    public void addIfNotExist(HashSet hashSet, Object o) {

    }

    public void addIfNotExist(HashMap hashMap, Object o) {
        hashMap.putIfAbsent(o, o);
    }

    public void addIfNotExist(ConcurrentHashMap concurrentHashMap, Object o) {
        concurrentHashMap.putIfAbsent(o, o);

    }

}
