package com.example.java8.class06;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;


/**
 * Callable 과 Future
 *
 * Callable
 *  - Runnable 과 유사하지만 결과값을 리턴할 수 있다.
 *  - 리턴하려는 타입을 제네릭 타입으로 준다. ex) Callable<ReturnType>
 */
public class CompletableFuture3 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Callable<String> hello = () -> {
            Thread.sleep(2000L);
            return "Hello";
        };

        Future<String> helloFuture = executorService.submit(hello);

        /** isDone : 상태확인 */
        // 상태확인 : isDone() -> 블록킹 되는동안 작업이 끝났는지에 대한 상태를 확인할 수 있다.
        //           끝낫으면 true, 안끝났으면 false 를 리턴
        helloFuture.isDone();
        System.out.println("Started!");

        /** cancel : 작업취소 */
        // 작업취소 : cancel() -> 작업을 취소 한다.  취소 이후에는 가져올 수 없다.
        //  - cancel(true) : 현재 실행중인 작업을 interrupt 하면서 종료한다.
        //  - cancel(false) : 현재 실행중인 작업을 interrupt 하지 않고 기다린 후에 종료한다.
        //                    일단 cancel 을 하면 get() 으로 가져오기는 불가능하다.
        // helloFuture.cancel(false);

        // *** 블록킹콜 : get 은 결과를 다 가져올때까지 기다린다 (delay). 이전까지는 실행완료를 기다리지 않고 실행한다.
        helloFuture.get();

        System.out.println("End!");

        executorService.shutdown();

        /** InvokeAll : 모두가 다 끝나면 호출
         * - 1. 모두가 끝날떄 까지 기다린다.
         * - 2. 이 세개의 RESULT 를 가져온다.
         * EX) 주신의 모든 종가를 가져와서 계산해야하는경우
         */
        ExecutorService executorService2 = Executors.newSingleThreadExecutor();

        Callable<String> hello2 = () -> {
            Thread.sleep(2000L);
            return "hello2";
        };

        Callable<String> hello3 = () -> {
            Thread.sleep(3000L);
            return "hello3";
        };

        Callable<String> hello4 = () -> {
            Thread.sleep(1000L);
            return "hello4";
        };

        List<Future<String>> futures = executorService2.invokeAll(Arrays.asList(hello2, hello3, hello4));

        for (Future<String> future : futures) {
            System.out.println(future.get());
        }

        executorService2.shutdown();

        /**
         * Invoke Any : 제일 빠른것이 리턴된다.
         * - BLOCKING CALL 이다.
         * - newFixedThreadPool 를 사용한다.
         * EX) 가장 빠른 서버로 부터, 각각의 스레드 풀로부터 결과만 받으면 되는 경우
         */
        ExecutorService executorService3 = Executors.newFixedThreadPool(4);
        Callable<String> hello5 = () -> {
            Thread.sleep(4000L);
            return "hello5";
        };
        Callable<String> hello6 = () -> {
            Thread.sleep(3000L);
            return "hello6";
        };
        Callable<String> hello7 = () -> {
            Thread.sleep(2000L);
            return "hello7";
        };
        String s = executorService3.invokeAny(Arrays.asList(hello5,hello6,hello7));
        System.out.println(s); //hello7 이 출력된다.

        executorService3.shutdown();
    }
}
