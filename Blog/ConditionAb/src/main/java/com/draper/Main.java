package com.draper;

/**
 * @author draper_hxy
 */
public class Main {

    public static void main(String[] args) {
        Factory factory = new Factory();
        new Thread(new ProduceRunnable(factory)).start();
        new Thread(new ConsumeRunnable(factory)).start();
    }


}
