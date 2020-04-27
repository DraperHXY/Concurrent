package com.draper;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author draper_hxy
 */
public class Factory {

    private final Lock lock = new ReentrantLock();
    private final Condition a = lock.newCondition();
    private final Condition b = lock.newCondition();
    private boolean flag = false;

    public void produce() {
        try {
            lock.lock();
            while (flag) {
                a.await();
            }
            System.out.println("生产");
            flag = true;
            b.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }


    }

    public void consume() {
        try {
            lock.lock();
            while (!flag) {
                b.await();
            }
            System.out.println("消费");
            flag = false;
            a.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

}
