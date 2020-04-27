package com.draper;

/**
 * @author draper_hxy
 */
public class AliveMute {

    public static void main(String[] args) {
        Account a = new Account();
        Account b = new Account();

        Thread th1 = new Thread(()->{
            try {
                a.transfer(b, 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread th2 = new Thread(()->{
            try {
                b.transfer(a, 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        th1.start();
        th2.start();

    }

}
