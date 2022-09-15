package com.example.java8.class02;

import java.util.Locale;

public interface Foo {

    void printName();

    /**
     * 기본 메서드 (Default Method)
     * @implSpe
     *  - java8 에 도입된 자바독 이 구현체가 어떤일을 하는건지 적어두면 좋다.
     * - 이 구현체는 getName() 으로 가져온 문자열을 대문자로 바꿔 출력한다.
     * - 구현하는 쪽에서 재정의 가능
     * */
    default void printNameUpperCase() {
        System.out.println(getName().toUpperCase());
    }

    String getName();

    // Object 에 관한 메서드는 구현 불가능
    // default String toString() {} -> toString 는 Object 의 메서드이다.

    /**
     * 스태틱 메서드 (static method)
     * 타입을 갖고 메소드 기능 제공 가능
     */
    static void printAnything() {
        System.out.println("aaaaaa");
    }
}
