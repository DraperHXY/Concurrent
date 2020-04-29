package com.draper;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author draper_hxy
 */
public class LruCache<K, V> {

    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private final Lock readLock = readWriteLock.readLock();
    private final Lock writeLock = readWriteLock.writeLock();

    private final ConcurrentHashMap<K, V> cache;
    private final ConcurrentLinkedQueue<K> queue;
    private final int capacity;

    public LruCache(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException();
        }
        this.capacity = capacity;
        cache = new ConcurrentHashMap<>();
        queue = new ConcurrentLinkedQueue<>();
    }


    public V put(K key, V value) {
        writeLock.lock();
        try {
            if (cache.containsKey(key)) {
                moveToTailOfQueue(key);
                cache.put(key, value);
                return value;
            }
            if (cache.size() == capacity) {
                removeOldestKey();
            }
            cache.put(key, value);
            queue.add(key);
            return value;
        } finally {
            writeLock.unlock();
        }
    }

    public V get(K key) {
        readLock.lock();
        try {
            if (cache.containsKey(key)) {
                moveToTailOfQueue(key);
                return cache.get(key);
            }
            return null;
        } finally {
            readLock.unlock();
        }
    }

    private void moveToTailOfQueue(K key) {
        queue.remove(key);
        queue.add(key);
    }

    private void removeOldestKey() {
        K key = queue.poll();
        cache.remove(key);
    }

}
