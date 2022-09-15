package com.example.java8.class06;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * CompletableFuture 2
 *
 * 조합하기
 * - thenCompose() : 두 작업이 서로 이어서 실행하도록 조합
 * - thenCombine() : 두 작업을 독립적으로 실행하고 둘 다 종료 했을 대 콜백 실행
 * - allOf() : 여러 작업을 모두 실행하고 모든 작업 결과에 콜백 실행
 * - anyOf() : 여러 작업 중에 가장 빨리 끝난 하나의 결과에 콜백 실행
 *
 * 예외처리
 *  - exeptionally(Function)
 *  - handle(BiFunction)
 * */
public class CompletableFuture5 {

    private static CompletableFuture<String> getWorld(String message) {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println(message + Thread.currentThread().getName());
            return message + "Word";
        });
    }

    public static void main(String[] args) throws Exception {

        /** 비동기 조합 */
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("completableFuture : NAME " + Thread.currentThread().getName());
            return "Hello";
        });

        // thenCompose() : completableFuture.thenCompose(s -> getWorld(s));
        // future 간의 의존성 있는것
        // 작업하나를 먼저해야하고 그다음에 다음작업을 해야하는 경우
        CompletableFuture<String> future = completableFuture.thenCompose(CompletableFuture5::getWorld);
        System.out.println(future.get());

        // thenCombine() : 서로 연관관계가 없는경우 , 따로 실행하는경우
        // 입력값 2개, 결과값 1개
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            System.out.println("future1 : NAME " + Thread.currentThread().getName());
            return "future1";
        });

        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("future2 : NAME " + Thread.currentThread().getName());
            return "future2";
        });

        // bi function , 두개의 결과를 받아서 결과가 다 왔을때 bi function 에 있는 람다가 실행한다.
        CompletableFuture<String> combinedFuture = future1.thenCombine(future2, (h, w) -> h + " " + w);
        System.out.println(combinedFuture.get());

        // allOf() : 두개이상일떄 여러 테스크를 합쳐서 실행할 수 잇다. allOf 에 있는 모든 테스트가 끝났을때 콜백이 실행된다.
        CompletableFuture.allOf(future1, future2).thenAccept((result) -> {
            // result 여러타입의 타입이 동일하다는 보장은 없다. 그중 어떤것을 에러가 날 가능성도 있음
            // 결과는 무의미하다.
            System.out.println(result); // null 이 출력된다.
        });

        // 이 모든 테스크들의 결과값을 받고싶은 경우 아래와같이 처리하면 모든작업이 blocking 되지 않는다.
        List<CompletableFuture> futures = Arrays.asList(future1, future2);

        CompletableFuture[] futuresArray = futures.toArray(new CompletableFuture[(futures.size())]);

        CompletableFuture<List<Object>> futureResult = CompletableFuture.allOf(futuresArray)
                .thenApply(v -> { // 모든 작업이 끝낫을때 발생
                    return futures.stream().map(CompletableFuture::join)
                            .collect(Collectors.toList()); // 결과값을 보아서 리스틀 반환
                });

        futureResult.get().forEach(System.out::println);

        // anyOf : 둘중 아무거나 먼저 오는것이 출결된다.
        // thenAccept : 결과티잆 x
        CompletableFuture<Void> futureAnyOf = CompletableFuture.anyOf(future1, future2).thenAccept((s) -> {
            System.out.println(s);
        });

        futureAnyOf.get();

        /** 비동기 에러처리 */
        // 비동기 호출 에러발생시 : supplyAsync -> exceptionally
        boolean throwError = true;
        CompletableFuture<String> futureSpplyAsync = CompletableFuture.supplyAsync(() -> {
            if (throwError) {
                throw new IllegalArgumentException();
            }
            System.out.println("Hello " + Thread.currentThread().getName());
            return "Hello";
        }).exceptionally(ex -> {
            System.out.println(ex);
            return "Error!";
        });
        System.out.println(futureSpplyAsync.get());

        // handle : 성공할떄 실패할떄 모두 들어온다.
        boolean throwError2 = true;
        CompletableFuture<String> futureSpplyAsync2 = CompletableFuture.supplyAsync(() -> {
            if (throwError2) {
                throw new IllegalArgumentException();
            }
            System.out.println("Hello " + Thread.currentThread().getName());
            return "Hello";
        }).handle((result, ex) -> {
            System.out.println(result); // 성공햇을때 나오는 결과값
            System.out.println(ex); // exception 이 발생했을때 에러
            if (ex != null) {
                System.out.println(ex);
                return "ERROR!";
            }
            return result;
        });
        System.out.println(futureSpplyAsync2.get());
    }
}
