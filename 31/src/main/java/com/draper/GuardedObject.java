package com.draper;


import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Predicate;

/**
 * @author draper_hxy
 */
@SuppressWarnings("all")
public class GuardedObject<T> {

    T obj;
    final Lock lock = new ReentrantLock();
    final Condition done = lock.newCondition();
    final int timeout = 1;

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

    public static void main(String[] args) {
        GuardedObject o = new GuardedObject<>();
        o.get(o1 -> 1 == (2 - 1));
    }

}
