package com.draper;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author draper_hxy
 */
public class BlockedQueue<T> {

    private final Lock lock = new ReentrantLock();

    private Condition a = lock.newCondition();
    private Condition b = lock.newCondition();

    private T t;

    public void enq(T t) {

        while(true){
            lock.tryLock();
        }
    }

    public T deq() {
        return t;
    }


}
