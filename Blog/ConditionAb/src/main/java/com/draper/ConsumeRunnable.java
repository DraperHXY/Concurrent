package com.draper;

/**
 * @author draper_hxy
 */
public class ConsumeRunnable implements Runnable {

    private final Factory factory;

    public ConsumeRunnable(Factory factory) {
        this.factory = factory;
    }

    public void run() {
        while (true) {
            factory.consume();
        }
    }

}
