package com.draper;


/**
 * @author draper_hxy
 */
public class Computer {

    private static final Object lock = new Object();

    private static Integer i = 0;

    public void add() {
        synchronized (lock) {
            i++;
        }
    }

    public int get() {
        return i;
    }

}
