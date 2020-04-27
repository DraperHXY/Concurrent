package com.draper;

import java.util.concurrent.locks.Lock;

/**
 * @author draper_hxy
 */
public class Add10K implements Runnable {

    private final Lock lock;

    private static int i = 0;

    public Add10K(Lock lock) {
        this.lock = lock;
    }

    public void run() {
        int idx = 0;
        while (idx++ < 10000) {
            lock.lock();
            try {
                i++;
            } finally {
                lock.unlock();
            }
        }
    }

    public static int get() {
        return i;
    }

}
