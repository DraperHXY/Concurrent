package com.draper;

/**
 * @author draper_hxy
 */
public class Main {

    public static void main(String[] args) {
        LruCache<String, Object> cache = new LruCache<>(3);
        cache.put("1", "1");
        cache.put("2", "2");
        cache.put("3", "3");
        cache.get("2");
        cache.get("1");
        cache.put("4","4");
        System.out.println(cache.get("3"));
    }

}
