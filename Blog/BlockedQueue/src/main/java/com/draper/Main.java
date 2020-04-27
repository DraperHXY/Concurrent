package com.draper;

import java.time.Instant;

/**
 * @author draper_hxy
 */
public class Main {

    private static int i = 0;

    public static void main(String[] args) {
        BlockQueue<String> queue = new BlockQueue<>();

        new Thread(() -> produce(queue)).start();

        new Thread(() -> consume(queue)).start();
    }

    private static void produce(BlockQueue<String> queue) {
        while (true) {
            Instant instant = Instant.now();
            queue.enq("hello", () -> System.out.println("生产"));
        }
    }

    private static void consume(BlockQueue<String> queue) {
        while (true) {
            queue.deq(() -> System.out.println("消费" + i++));
        }
    }

}
