package com.draper;

/**
 * @author draper_hxy
 */
public class ProduceRunnable implements Runnable {

    private final Factory factory;

    public ProduceRunnable(Factory factory) {
        this.factory = factory;
    }

    @Override
    public void run() {
        while (true) {
            factory.produce();
        }

    }
}
