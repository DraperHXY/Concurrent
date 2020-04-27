package com.draper;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author draper_hxy
 */
public class BlockedQueue<T> {
    final Lock lock = new ReentrantLock();
    final Condition notFull = lock.newCondition();
    final Condition notEmpty = lock.newCondition();
    T t;

    void enq(T x, Listener listener) {
        lock.lock();
        try {
            while (t != null) {
                notFull.await();
            }
            t = x;
            notEmpty.signal();
            listener.call();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    void deq(Listener listener) {
        lock.lock();
        try {
            while (t == null) {
                notEmpty.await();
            }
            t = null;
            listener.call();
            notFull.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
