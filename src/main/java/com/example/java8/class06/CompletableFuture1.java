package com.example.java8.class06;

import java.sql.SQLOutput;

/**
 * 자바 Concurrent 프로그래밍 소개
 * 1. Cuncurrent 소프트웨어
 *  - 동시에 여러 작업을 할 수 있느 소프트웨어
 *  - 예) 웹 브라우저
 * */
public class CompletableFuture1 {

    static class MyThread extends Thread {
        @Override
        public void run() {
            System.out.println("Thread : " + Thread.currentThread().getName());
        }
    }

    public static void main(String[] args) throws InterruptedException {
        /** 스레드 실행방법 1*/
        MyThread myThread = new MyThread();
        myThread.start(); // + 스레드와 프로세스의 차이

        System.out.println("Hello : " +  Thread.currentThread().getName());

        /** 스레드 실행방법 2*/
        Thread myThread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Thread2: " + Thread.currentThread().getName());
            }
        });

        myThread2.start();

        System.out.println("Hello2 : " + Thread.currentThread().getName());

        /** 스레드 실행방법 3 */
        Thread myThread3 = new Thread(() -> {
            try {
                 Thread.sleep(1000L);
            } catch (InterruptedException e) {
                // InterruptedException sleep 동안에 누군가 꺠우면 동작
                e.printStackTrace();
            }
            System.out.println("Thread3: " + Thread.currentThread().getName());
        });

        myThread3.start();

        /**
         * 스레드의 주요기능 : sleep, interrupt
         * - sleep : 스레드 대기, 잠재움
         * - interrupt : 스레드 끼어들기, 다른 쓰레드를 깨우는 방법
         * public void run()
         */
        Thread myThread4 = new Thread(() -> {
            while(true) {
                System.out.println("Thread4: " + Thread.currentThread().getName());
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    // InterruptedException sleep 동안에 누군가 꺠우면 동작
                    System.out.println("exit!");
                    return; // 종료 : return 을 안하면 종료되지 않는다.
                }
            }
        });
        myThread4.start();

        // 스레드의 순서는 보장을 못한다.
        System.out.println("Hello4" + Thread.currentThread().getName());

        Thread.sleep(3000L);
        myThread4.interrupt(); // InterruptedException 발생

        /**
         * 스레드의 주요기능
         * - join : 이전 스레드가 종료될떄까지 기다렸다가 다음스레드를 깨움
         */
        Thread myThread5 = new Thread(() -> {
            System.out.println("Hello5: " + Thread.currentThread().getName());
            try {
                Thread.sleep(3000L);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
        });
        myThread5.start();

        System.out.println("Hello5:" + Thread.currentThread().getName());

        try {
            myThread5.join(); // 메인 스레드가 끝날때까지 기다렸다가 실행
        } catch (InterruptedException e) {
            e.printStackTrace();
            // 다른 스레드가 끼어드는경우 exception 발생 -> 수백개의 스레드가 동작하는 경우 코딩으로 관리가 어렵다. -> executor 로 관리할 수 있다.
        }

        // 스레드가 수십개.. 수백개가 들어왔을때 개발자가 스레드를 관리하기 어렵기 떄문에
        // executor 가 나오게 된다.
        // executor > future > completable future
        System.out.println(myThread5 + "is finished");
    }
}
