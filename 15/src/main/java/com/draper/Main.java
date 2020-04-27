package com.draper;


/**
 * @author draper_hxy
 */
public class Main {

    public static void main(String[] args) {
        BlockedQueue<Main> queue = new BlockedQueue<Main>();

        new Thread(() -> {
            while (true) {
                Main main = new Main();
                queue.enq(main, () -> System.out.println("生产"));
            }
        }).start();

        new Thread(() -> {
            while (true) {
                queue.deq(() -> System.out.println("消费"));
            }

        }).start();


    }


}
