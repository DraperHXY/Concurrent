package com.draper;

/**
 * @author draper_hxy
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {

        Computer c = new Computer();

        Thread th1 = new Thread(() -> {
            int i = 0;
            while (i++ < 10000) {
                c.add();
            }
        });

        Thread th2 = new Thread(() -> {
            int i = 0;
            try {
                Thread.sleep(100L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            while (i++ < 10000) {
                c.add();
            }
        });

        th1.start();
        th2.start();

//        th2.join();
        th1.join();

        System.out.println(c.get());
    }

}
