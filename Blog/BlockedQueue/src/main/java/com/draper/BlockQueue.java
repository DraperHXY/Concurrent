package com.draper;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author draper_hxy
 */
public class BlockQueue<T> {

    private final Lock lock = new ReentrantLock();
    private final Condition aCondition = lock.newCondition();
    private final Condition bCondition = lock.newCondition();

    private T t;

    public void enq(T x, Listener listener) {
        lock.lock();
        try {
            while (t != null) {
                aCondition.await();
            }
            t = x;
            listener.call();
            bCondition.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void deq(Listener listener) {
        lock.lock();
        try {
            while (t == null) {
                bCondition.await();
            }
            t = null;
            listener.call();
            aCondition.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }

}
