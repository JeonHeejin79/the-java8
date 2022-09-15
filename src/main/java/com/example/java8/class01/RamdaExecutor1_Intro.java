package com.example.java8.class01;

/*
* 함수형 인터페이스
* 1.함수형 인터페이스
*
* 2.람다 표현식
*
* 3.자바에서 함수형 프로그래밍
* */
@FunctionalInterface // 함수형 인터페이스임을 명시, 위반을하면 컴파일할때 에러가난다.
public interface RamdaExecutor1_Intro {

    // abstract 생각가능 : 추상메서드
    // 2개있으면 함수형 인터페이스가 아니다
    // 한수형인터페이스는 추상메서드를 한개만 갖고있다.
    // abstract : 자식에서 무조건 overriding 해야함
    // 다른 형태의 메서드가 있더라도 추상메서드가 1개이면 무조건 함수형 인터페이스이다.
    abstract void doIt();

    // 인터페이스에 static 메서드 정의 가능
    // 추상메서드가 1개면 Functional Interface 이다.
    static void printName() {
        System.out.println("name");
    }

    default void pringAge() {
        System.out.println("33");
    }

}
