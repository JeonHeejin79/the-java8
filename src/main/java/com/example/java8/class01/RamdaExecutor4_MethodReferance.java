package com.example.java8.class01;

import java.util.Arrays;
import java.util.Comparator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

/**
 * 5. 메소드 레퍼런스
 * 람다가 하는 일이 기존 메소드 또는 생성자를 호출하는 거라면, 메소드 레퍼런스를 사용해서
 * 매우 간결하게 표현할 수 있다.
 *
 * 메소드 참조하는 방법
 * - 스태틱 메소드 참조 : 타입::스태틱메소드
 * - 특정 객체의 인스턴스 메소드 참조 : 객체 레퍼런스::인스턴스 메소드
 * - 임의 객체의 인스턴스 메소드 참조 : 타입::인스턴스메소드
 * - 생성자 참조 : 타입::new
 *
 * -> 메소드 또는 생성자의 매개변수로 람다의 입력값을 받는다.
 * -> 리턴값 또는 생성한 객체의 람다의 리턴값이다.
 */
public class RamdaExecutor4_MethodReferance {

    public static void main(String[] args) {
        // 기존에 구현되어 잇는 메소드 자체를 참조하는 것
        // 메소드 자체를 Functional 인터페이스의 구현체로 쓰게 된다.
        Function<Integer, String> intToString = (i) -> {
           return  "number";
        };

        // 1. static 메소드를 사용하는경우
        UnaryOperator<String> hi1 = (s) -> "hi " + s;
        // static 한 메서드를 사용하겠다.
        UnaryOperator<String> hi2 = Greeting::hi;


        // 2. 특정객체의 인스턴스 메소드를 사용하는경우 -> 메소드레퍼런스이다.
        Greeting greeting = new Greeting();
        // apply 를해야 greeting 인스턴스에 있는 hello 에 전달이 되고 값을 출력한다.
        UnaryOperator<String> hello = greeting::hello;
        System.out.println(hello.apply("heejin")); // "heejin"

        // 3. 생성자를 참조하는경우
        // 생성자의 리턴값은 객체의 타입이다.
        // 입력값은 없는데 결과값이 있는것 : Supplier
        Supplier<Greeting> supplier = Greeting::new; // 기본생성자를 참조
        Greeting newGreeting = supplier.get();

        // 입력값이 있고 결과가 객체타입인경우
        Function<String, Greeting> greeting2 = Greeting::new; // 문자열을 받는 생성자를 참조

        Greeting heejin = greeting2.apply("heejin");
        System.out.println(heejin.getName()); // "heejin"

        Supplier<Greeting> newGreeting2 = Greeting::new;

        //4. 입의의 객체의 인스턴스를 참조하는 방법
        String[] names = {"keesun", "whiteship", "toby"};
        // Comparator 가 java8 부터는 FunctionnalInterface 로 변경됨. -> @FunctionalInterface
        // 여러개의 default static 한 메소드들이 잇다.
        Arrays.sort(names, new Comparator<String>() {  // Comparator : 함수형 인터페이스
            @Override
            public int compare(String o1, String o2) {
                return 0;
            }
        });

        String[] names2 = {"keesun", "whiteship", "toby"};
        // 임의의 객체의 인스턴스 메소드를 참조한 것이다.
        Arrays.sort(names2, String::compareToIgnoreCase);
        System.out.println(Arrays.toString(names2)); //  [keesun, toby, whiteship]


    }
}
