package com.draper;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author draper_hxy
 */
public class Account {

    private int balance = 1999;

    private final Lock lock = new ReentrantLock();

    void transfer(Account tar, int amt) throws InterruptedException {
        while (true) {
            if (this.lock.tryLock()) {
                try {
                    if (tar.lock.tryLock()) {
                        try {
                            this.balance -= amt;
                            tar.balance += amt;
                        } finally {
                            tar.lock.unlock();
                        }
                    } else {
                        System.out.println("获取失败");
                    }
                }finally {
                    this.lock.unlock();
                    System.out.println("转账成功");
                }
                Thread.sleep(1000);
            } else{
                System.out.println("获取失败");
            }
        }
    }

}
