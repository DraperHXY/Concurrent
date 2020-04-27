package com.draper;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author draper_hxy
 */
public class X {

    private final Lock rtl = new ReentrantLock();

    int value;

    public int get() {
        rtl.lock();
        try {
            return value;
        } finally {
            rtl.unlock();
        }
    }

    public void addOne() {
        rtl.lock();
        try {
            value = 1 + get();
        } finally {
            rtl.unlock();
        }
    }


}
