package com.draper.n;


import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author draper_hxy
 */
@SuppressWarnings("all")
public class Main {

    private static final BlockingQueueBatchExecUtil<String> manager =
            new BlockingQueueBatchExecUtil(5, 3, (BlockingQueueBatchExecUtil.BatchTaskListener<String>) taskList -> {

                if (taskList != null && taskList.size() != 0) {
                    System.out.println("执行 stringList 任务,strList.size " + taskList.size());
                    taskList.forEach(s -> System.out.println(Thread.currentThread().getName() + " output: " + s));
                }
            });

    public static void main(String[] args) {
        ExecutorService es = Executors.newFixedThreadPool(5);
        es.execute(() -> {
            for (int i = 0; i < 10; i++) {
                manager.addTask(System.currentTimeMillis() + "");
            }
        });
        es.execute(() -> {
            for (int i = 0; i < 10; i++) {
                manager.addTask(System.currentTimeMillis() + "");
            }
        });;
        es.execute(() -> {
            for (int i = 0; i < 10; i++) {
                manager.addTask(System.currentTimeMillis() + "");
            }
        });
    }

}
