package com.draper;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author draper_hxy
 */
public class Main {


    public static void main(String[] args) throws InterruptedException {
        Lock lock = new ReentrantLock();

        Thread th1 = new Thread(new Add10K(lock));
        Thread th2 = new Thread(new Add10K(lock));
        th1.start();
        th2.start();

        th1.join();
        th2.join();

        System.out.println(Add10K.get());




    }

}
