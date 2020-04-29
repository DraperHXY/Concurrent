package com.draper.n;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author draper_hxy
 */
public class BlockingQueueBatchExecUtil<T> {

    private final int execBatchSize;
    private final ExecutorService es;

    private final BlockingQueue<T> blockingQueue;
    private final BatchTaskListener<T> listener;

    public BlockingQueueBatchExecUtil(int maxThreadSize, int execBatchSize, BatchTaskListener<T> listener) {
        if (execBatchSize <= 0) {
            throw new IllegalArgumentException();
        }
        this.execBatchSize = execBatchSize;
        this.es = new ThreadPoolExecutor(maxThreadSize, maxThreadSize, 3, TimeUnit.HOURS, new LinkedBlockingDeque<>(10));
        this.listener = listener;
        this.blockingQueue = new LinkedBlockingDeque<>(execBatchSize * 2);

        init(maxThreadSize);
    }

    private void init(int taskSize) {
        for (int i = 0; i < taskSize; i++) {
            es.execute(() -> {
                while (true) {
                    List<T> taskList = pollBatchTask();
                    execBatchTask(taskList);
                }
            });
        }
    }

    public void addTask(T t) {
            blockingQueue.offer(t);
    }

    private List<T> pollBatchTask() {
        List<T> taskList = new ArrayList<>();

            while (taskList.size() != execBatchSize) {
                T t;
                try {
                    t = blockingQueue.take();
                    taskList.add(t);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        return taskList;
    }

    private void execBatchTask(List<T> taskList) {
        listener.call(taskList);
    }

    public interface BatchTaskListener<T> {
        void call(List<T> taskList);
    }

}
