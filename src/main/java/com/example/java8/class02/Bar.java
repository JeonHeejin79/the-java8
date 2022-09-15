package com.example.java8.class02;

// 인터페이스를 상속받는 인터페이스에서 다시 추상 메소드로 변경할 수 있다.
public interface Bar extends Foo {

    // 추상 메서드로 선언해주면 된다.
    void printNameUpperCase(); // 미선언시 기본 구현체로 제공이 된다.
}
