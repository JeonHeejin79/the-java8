package com.example.java8.class06;

import java.util.Locale;
import java.util.concurrent.*;

/**
 * CompletableFuture 1
 * 자바에서 비동기 (Asynchronous) 프로그래밍을 가능케 하는 인터페이스
 * - Future 를 사용해서도 어느정도 가능했짐나 하기 힘들 일들이 많았다.
 *
 * Future 로는 하기 어렵던 작업들
 * - Future 를 외부에서 완료 시킬 수 없다. 취소하거나 , get()에 타임아웃을 설정할 수는 있다.
 * - 블록킹 코드(get()) 를 사용하지 않고서는 작업이 끝났을 때 콜백을 실행할 수 없다.
 * - 여러 Future 를 조합할 수 없다.  예)Event 정보 가져온 다음 Event에 참석하는 회원 목록 가져오기
 * - 예외 처리용 API를 제공하지 않는다.
 *
 * ComplatableFuture : 외부에서 COMPLETE 를 시킬 수 있다.
 * - Implements Future
 * - Implements CompletionStage
 *
 * 비동기로 작업 실행하기
 * - 리턴값이 없는 경우 : runAsync()
 * - 리턴값이 있는 경우 : spplyAsync()
 * - 원하는 Executor(쓰레드풀)를 사용해서 실행할 수도 있다. (기본은 ForkJoinPool.commonPool())
 *
 * 콜백 제공하기
 * - thenApply(Function) : 리턴값을 받아서 다른 값으로 바꾸는 콜백
 * - thenAccept(Consumer) : 리턴값을 또 다른 작업을 처리하는 콜백 (리턴없이)
 * - thenRun(Runnable) : 리턴값 받지 다른 작업을 처리하는 콜백
 * - 콜백 자체를 또 다른 쓰레드에서 실행할 수 있다.
 */
public class CompletableFuture4 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        Future<String> future = executorService.submit(() -> "hello");
        // TODO -  get 을 하기 전까지는 어떤것도 할 수 없다.  받아서 하는 작업은 이 뒤에 와야 한다.
        future.get(); // blocking call

        // java5 에서 제공하던 Future 만으로는 자바스크립트에서 제공하던, 비동기작업, 콜백 등의 작업이 어려웠었다.
        // java8 에서 ComplatableFuture 에서 새로 제공하고 있다. Future / CompletionStage 를 둘다 구현하고 있다.
        // CompletableFuture
        // 1. 외부에서 complete 를 시킬 수 있다.
        //  ex) 외부에서 몇초이내에 응답이 안오면 특정한 값으로 리턴 하는 경우
        // 2. executor 가 필요 없다. : CompletableFuture 자체만을 가지고 비동기적으로 작업들을 실행할 수 있다.
        /** CompletableFuture : complete */
        CompletableFuture<String> completableFuture = new CompletableFuture<>();
        completableFuture.complete("task"); // 기본값 정의
        System.out.println("CompletableFuture > complete > get : " + completableFuture.get()); // 가져오기

        /** 비동기로 작업 실행하기 */
        // CompletableFuture : completedFuture
        CompletableFuture<String> completableFuture2 = CompletableFuture.completedFuture("static factory method");
        System.out.println("CompletableFuture.completedFuture : " + completableFuture2.get());

        // CompletableFuture : runAsync ->  리턴이 없는경우
        CompletableFuture<Void> runAsync = CompletableFuture.runAsync(() -> {
            System.out.println("runAsync " + Thread.currentThread().getName());
        });

        runAsync.get(); // get을 했을때 작업이 실행된다.

        // CompletableFuture : supplyAsync -> 리턴이 있는경우
        CompletableFuture<String> supplyAsync1 = CompletableFuture.supplyAsync(() -> {
            System.out.println("supplyAsync " + Thread.currentThread().getName());
            return "supplyAsync";
        });
        supplyAsync1.get(); // get을 했을때 작업이 실행된다.

        /** 콜백 제공하기 */
        // - thenApply : 결과값 존재 , 리턴값 존재 : 결과값을 사용해서 후처리, 리턴값도 필요한경우
        CompletableFuture<String> supplyAsync2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("supplyAsync2 " + Thread.currentThread().getName());
            return "supplyAsync2";
        }).thenApply((s) -> {
            System.out.println(Thread.currentThread().getName());
            return s.toLowerCase();
        });

        System.out.println(supplyAsync2.get());

        // thenAccept - 결과값 존재, 리턴값 미존재 : 결과값을 사용해서 후처리가 필요한경우
        CompletableFuture<Void> supplyAsync3 = CompletableFuture.supplyAsync(() -> {
            System.out.println("supplyAsync3 " + Thread.currentThread().getName());
            return "supplyAsync3";
        }).thenAccept((s) -> {
            System.out.println(Thread.currentThread().getName());
            System.out.println(s.toUpperCase());
        });
        supplyAsync3.get();

        // thenRun - 결과값 미존재, 리턴값 미존재 : 결과값을 사용할 필요없는경우
        CompletableFuture<Void> supplyAsync4 = CompletableFuture.supplyAsync(() -> {
            System.out.println("supplyAsync4 " + Thread.currentThread().getName());
            return "supplyAsync4";
        }).thenRun(() -> {
            System.out.println(Thread.currentThread().getName());
        });
        supplyAsync4.get();


        // ForkJoinPool : Executor 를 구현한 구현체중에 한개, Java7 에서 들어옴
        // dequeue 를 사용하여 자기가 할일을 가져와서 처리하는 방식의 프레임워크이다.
        // 작업단위를 자기가 파생시키는 서브 task 가 있다면
        // 서브 task 를 분산처리하고 결과값을 모아서 처리하는 Pool 이다.
        // 기본적으로 ForkJoinPool 를 사용하게되나
        // 원한다면 Thread Pool 을 만들어서  줄 수 있다.
        ExecutorService executorService1 = Executors.newFixedThreadPool(4);
        CompletableFuture<Void> future1 = CompletableFuture.supplyAsync(() -> {
            System.out.println("future1");
            return "future1";
        }, executorService1).thenRunAsync(() -> { // thenApply, thenAccept
            System.out.println(Thread.currentThread().getName());
        }, executorService1);
        future1.get(); // 실행
        executorService1.shutdown();
    }
}
