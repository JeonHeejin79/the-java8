package com.example.java8.class06;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 2. Executors
 * 자바 Concurrent 프로그래밍 소개
 * Executors
 * 스레드를 만들고 관리하는 작업을 고수준의 API 로 관리한다.
 *
 * 고수준 (High-Level) Concurrency 프로그래밍
 * - 쓰레드를 만들고 관리하느 작업을 애플리케이션에서 분리
 * - 그런 기능을 Excutors 에게 위임
 *
 * Executors 가 하는일
 * - 쓰레드 만들기 : 애플리케이션이 사용할 쓰레드 풀을 만들어 관리한다.
 * - 쓰레드 관리 : 쓰레드 생명 주기를 관리한다.
 * - 작업 처리 및 실행 : 쓰레드로 실행할 작업을 제공할 수 있는 API 를 제공한다.
 *
 * Executor > ExecutorService > ScheduledExecutorService
 * */
public class CompletableFuture2 {

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

//        executorService.execute(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("Thread " + Thread.currentThread().getName());
//            }
//        });

        // executorService 는 다음작업이 들어올떄까지 걔속 스레드를 실행시키기떄문에 종료시켜야 한다.
        executorService.submit(() -> {
            System.out.println("Thread " + Thread.currentThread().getName());
        });

        executorService.shutdown(); // 현재작업은 끝까지 맞추고 shutdown 한다. -> graceful shutdown

        // executorService.shutdownNow(); // 지금돌고있는 스레드가 다 종료되는지 여부와 상관없이 shutdown 한다.

        /**
         * newFixedThreadPool : 내부에 스레드 ful 이 존재
         * 스레드를 2개 갖고있는 execute service 생성
         * -> 2개의 스레드를 번갈아가면서 실행
         */
        ExecutorService executorService2 = Executors.newFixedThreadPool(2);
        executorService2.submit(getRunnable("executorService2 : Hello "));
        executorService2.submit(getRunnable("executorService2 : The "));
        executorService2.submit(getRunnable("executorService2 : Java8 "));
        executorService2.submit(getRunnable("executorService2 : Thread "));

        executorService2.shutdown();

        /**
         * newSingleThreadScheduledExecutor : 스레드 시간을 조정할 수 있다.
         * - schedule
         * - scheduleAtFixedRate
         */
        ScheduledExecutorService executorService3 = Executors.newSingleThreadScheduledExecutor();

        // schedule : 3초 뒤에 실행
        executorService3.schedule(getRunnable("ScheduledExecutorService : Hello"), 3, TimeUnit.SECONDS);

        // scheduleAtFixedRate : 1기다렷다가 실행, 이후에는 2초 간격씩 실행
        executorService3.scheduleAtFixedRate(getRunnable("ScheduledExecutorService : Hello"), 1, 2, TimeUnit.SECONDS); // 3초 뒤에 실행

        executorService3.shutdown();
        // Callable : 스데르 실행 결과 타입을 받고싶은경우 사용 가능
        // Runnable 과 같으나 차이가 이싸면 return 을 할 수 있다.
    }

    private static Runnable getRunnable(String message) {
        return () -> System.out.println(message + Thread.currentThread().getName());
    }
}
