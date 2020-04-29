package com.draper;


import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Predicate;

/**
 * @author draper_hxy
 */
@SuppressWarnings("all")
public class ExtendGuardedObject<T> {

    T obj;
    final Lock lock = new ReentrantLock();
    final Condition done = lock.newCondition();
    final int timeout = 2;

    final static Map<Object, ExtendGuardedObject> gos = new ConcurrentHashMap<>();

    static <K> ExtendGuardedObject create(K key) {
        ExtendGuardedObject go = new ExtendGuardedObject();
        gos.put(key, go);
        return go;
    }

    static <K, T> void fireEvent(K key, T obj) {
        ExtendGuardedObject go = gos.remove(key);
        if (go != null) {
            go.onChanged(obj);
        }
    }

    T get(Predicate<T> p) {
        lock.lock();
        try {
            while (!p.test(obj)) {
                done.await(timeout, TimeUnit.SECONDS);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return obj;
    }

    void onChanged(T obj) {
        lock.lock();
        try {
            this.obj = obj;
            done.signalAll();
        } finally {
            lock.unlock();
        }
    }

}
