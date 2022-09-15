package com.example.java8.class01;

import java.util.function.*;

/**
 * 3. 자바에서 제공하는 함수형 인터페이스
 * Java가 기본으로 제공하는 함수형 인터페이스
 *  - java.lang.function 패키지
 *  - 자바에서 미리 정의해둔 자주 사용할만한 함수 인터페이스
 *  - Function<T, R> - T타비을 받아서 R 타입을 리턴하는 함수 인터페이스
 *      - R apply(T t)
 *      - 함수 조합용 메소드 (andThen, compose)
 *  - BiFunction<T, U, R> - 두 개의 값(T, U) 를 받아서 R 타비을 리턴하는 함수 인터페이스
 *      - R apply(T t, U u)
 *  - Comsumer<T> - T타입을 받아서 아무값도 리턴하지 않는 함수 인터페이스
 *      - void Accept(T t)
 *      - 함수 조합용 메소드 (andThen)
 *  - Predicate<T> - T 타입을 받아서 boolean 을 리턴하는 함수 인터페이스
 *  - UnaryOperator<T>
 *  - BinaryOperator<T>
 * */

public class RamdaExecutor2_FunctionalInterface {
    public static void main(String[] args) {
        Plus10 plus10 = new Plus10();
        System.out.println(plus10.apply(10));

        Function<Integer, Integer> functionPlus10 = (i) -> i + 10;
        System.out.println(functionPlus10.apply(10));

        Function<Integer, Integer> multiply = (i) -> i * 2;
        System.out.println(multiply.apply(1));

        // 함수 조합 : function1.compose(function2);
        // do -> function2
        // do -> function1
        Function<Integer, Integer> multiplyAndPlus =  functionPlus10.compose(multiply);
        System.out.println(multiplyAndPlus.apply(3));

        // 조합 : function1.andThen(function2);
        // do -> function1
        // do -> function2
        Function<Integer, Integer> plusAndThenMultiply = functionPlus10.andThen(multiply);
        System.out.println(plusAndThenMultiply.apply(3));

        // Comsumer<T> : T타입을 받아서 아무것도 리턴하지 않는 함수 인터페이스
        Consumer<Integer> printT = (i) -> System.out.println(i);
        printT.accept(10);

        // Supplier<T> : T타입의 값을 제공하는 함수 인터페이스, 받아올 값의 타입을 정의
        Supplier<Integer> get10 = () -> 10;
        System.out.println(get10.get());

        // Predicate : 어떠한 인자값을 받아서 true, false 를 리턴해주는 함수형 인터페이스이다.
        Predicate<String> startsWithKeesun = (s) -> s.startsWith("keesun");
        Predicate<Integer> isEven = (i) -> i % 2 == 0;

        // UnaryOperator : 입력값하고 리턴값의 타입이 같은경우 ex) Function<Integer, Integer>
        UnaryOperator<Integer> plus100 = (i) -> i + 100;

        // BiFunction : 값을 2개 받음 , 리턴값 1개
        // BinaryOperation : BiFunction 의의 특수항 형태, 3개의 모든 타입이 같을거라고 가정
        BinaryOperator<Integer> sum = (a, b) -> a+b;

        // 변수캡처

    }
}
