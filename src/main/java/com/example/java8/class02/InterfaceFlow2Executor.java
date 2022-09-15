package com.example.java8.class02;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Spliterator;
import java.util.stream.Collectors;

/**
 * 7. 인터페이스 기본 메소드와 스태틱 메소드2
 * 자바 8에서 추가한 기본 메소드로 인한 API 변화
 *
 * Iterable 의 기본 메소드
 * - forEach()
 * - spliterator() : 쪼갤수있는 기능르 갖고잇는 iterator
 *
 * Collection의 기본 메소드
 *  - stream() / parallelStream()
 *  - removeIf(Predicate)
 *  - spliterator()
 *
 * Comparator 의 기본 메소드 및 스태틱 메소드
 *  - reversed();
 *  - thenComparing();
 *  - static reverseOrder() / n aturalOrder();
 *  - static nullsFirst() / nullsLast()
 *  - static comparing();
 *  
 */
public class InterfaceFlow2Executor {

    public static void main(String[] args) {
        List<String> names = new ArrayList<>();
        names.add("keesun");
        names.add("whiteship");
        names.add("toby");
        names.add("foo");

        // 1. orEach
        System.out.println("======forEach================");
        names.forEach(System.out::println); // 메서드 레퍼런트
        System.out.println("======for================");
        for (String n : names)  {
            System.out.println(n);
        }

        // 2. spliterator : 쪼갤수 있는 기능을 갖고잇는 iterator, 순환하는데 사용
        // 순회 : tryAdvance()
        // 쪼갬 : trySplit(); 두개로 쪼갬
        System.out.println("======spliterator================");
        Spliterator<String> spliterator = names.spliterator(); // iterator
        Spliterator<String> trySplit = spliterator.trySplit(); // iterator
        System.out.println("======================");
        while(spliterator.tryAdvance(System.out::println));
        System.out.println("======================");
        while(trySplit.tryAdvance(System.out::println));
        System.out.println("======================");

        // 3. stream : 엘리먼트들을 stream 으로만들어서 functional 하게 처리 가능
        // 자바스크립트 객체와 유사하다. ex) underscore
        // 함수형 프로그램의 고차함수 사용
        List<String> name2 = new ArrayList<>();
        name2.add("keesun");
        name2.add("whiteship");
        name2.add("toby");
        name2.add("foo");

        // count
        name2.stream().map(String::toUpperCase)
                .filter(s -> s.startsWith("K"))
                .count(); // 갯수를셈

        // collect
        name2.stream().map(String::toUpperCase)
                .filter(s -> s.startsWith("K"))
                .collect(Collectors.toSet()); // set 으로 모아옴  + ex) toList()

        // removeIf
        List<String> name3 = new ArrayList<>();
        name3.add("keesun");
        name3.add("whiteship");
        name3.add("toby");
        name3.add("foo");

        name3.removeIf(s -> s.startsWith("k")); // removeIf(predicate)

        // 4. compareator : 정렬할때 주로사용,
        // functional interface 이다. : 입력값 하나를주면 한개를 리턴한다. : 추상메서드가 1개
        List<String> name4 = new ArrayList<>();
        name4.add("keesun");
        name4.add("whiteship");
        name4.add("toby");
        name4.add("foo");
        // name4.sort(String :: compareToIgnoreCase); // 정렬

        Comparator<String> compareToIgnoreCase = String :: compareToIgnoreCase;
        name4.sort(compareToIgnoreCase.reversed());// 역순정렬

        // name4.sort(compareToIgnoreCase.reversed().thenComparing());// thenComparing : 정렬조건 추가
        name4.forEach(System.out::println);

        //WebMvcConfigurer -> WebMvcConfigureAdapter

        // 5. API 의 변화
        /**
         * [Interface]  추상메서드 a() b() c()
         *     ^
         * [Abstract Class] 추상클래스  a() b() c)
         *  ^       ^      ^
         *  |       |      |  extends
         *  a()    b()    c()  -> 이 인터페이스를 구현할 클래스들에게 편의성 제공
         *
         * =====java 8 이후부터는 인터페이스가 제공할 수 있도록 된다.==========
         *
         * [Interface]  기본메서드 a() b() c()
         * ^    ^    ^
         * |    |    |  implements
         * a()  b()  c()  -> 상속으로부터 자유롭다. 상속이 강제되지않는다. -> 스프링에서는 이런방법의 접근성을 좋아한다.
         *
         * ex) WebMvcConfigurer <- WebMvcConfigurerAdapter (Deprecated)
         * -> : 인터페이스에서 default 로 제공하고있다.
         * */

    }
}
