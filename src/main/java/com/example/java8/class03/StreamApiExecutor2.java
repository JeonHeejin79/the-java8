package com.example.java8.class03;

import javax.swing.*;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/***
 * 9. Stream API
 * 걸러내기
 *  - Filter(Predicate)
 *
 * 변경하기
 *  - Map(Function) 또는 FlatMap(Function)
 *  - 예) 각각의 Post인스턴스에서 String title만 새로운 스트림으로
 *
 *  생성하기
 *   - generate(Supplier) 또는 Iterate(T seed, UnaryOperator)
 *   - 예) 10부터 1씩 증가하는 무제한 숫자 스트림
 *
 *  제한하기
 *    - limit(long) 또는 skip(long)
 *    - 예) 최대 5개의 요소가 담긴 스트림을 리턴한다.
 *
 *  스트림을 데이터가 특정 조건을 만족하는지 확인
 *    - anyMatch(), allMatch(), nonMatch()
 *    - 예) 10보다 큰 수의 개수를 센ㅏ.
 *
 *  스트림을 데이터 하나로 뭉치기
 *    - reduce(identity, BiFunction), collect(), sum(), max()
 *    - 예) 모든 숫자 합 구하기
 *    - 예) 모든 데이터를 하나의 List또는 Set 에 옮겨 담기
 */
public class StreamApiExecutor2 {

    public static void main(String[] args) {
        List<OnlineClass> springClass = new ArrayList<>();

        springClass.add(new OnlineClass(1, "spring boot", true));
        springClass.add(new OnlineClass(2, "spring data jpa", true));
        springClass.add(new OnlineClass(3, "spring mvc", false));
        springClass.add(new OnlineClass(4, "spring core", false));
        springClass.add(new OnlineClass(5, "rest api development", false));

        List<OnlineClass> javaClass = new ArrayList<>();

        javaClass.add(new OnlineClass(6, "The Java, Test", true));
        javaClass.add(new OnlineClass(7, "The Java, Test, Code manipulation", true));
        javaClass.add(new OnlineClass(8, "The Java, 8 to 11", true));

        List<List<OnlineClass>> keesunEvents = new ArrayList<>();

        keesunEvents.add(springClass);
        keesunEvents.add(javaClass);

        System.out.println("spring 으로 시작하는 수업");
        // TODO : filter - operation -> instance -> type ->
        List<OnlineClass> rsList1 = springClass.stream().filter(s -> {
            return s.getTitle().startsWith("spring");
        }).collect(Collectors.toList());
        rsList1.forEach(System.out::println);

        springClass.stream()
                .filter(s ->s.getTitle().startsWith("spring"))
                .forEach(System.out::println);

        System.out.println("close 되지 않은 수업");
        // TODO : Predicate.not(Instance::method) 메소드 레퍼런스를 사용하는경우 부정
        List<OnlineClass> rsList2 = springClass.stream().filter(s -> {
            return s.isClosed() == false;
        }).collect(Collectors.toList());
        rsList2.forEach(System.out::println);

        springClass.stream()
                .filter(Predicate.not(OnlineClass::isClosed))
                .forEach(oc-> System.out.println(oc.getId()));

        System.out.println("수업 이름만 모아서 스트림 만들기");
        // TODO : map -> OC ---- MAP ----> 다른타입으로 생성가능
        springClass.stream()
                .map(OnlineClass::getTitle)     // oc type (s-> s.getTitle)
                .forEach(System.out::println);  // string type

        System.out.println("두 수업 목록에 들어있는 모든 수업 아이디 출력");
        // TODO : flatMap 안에 있는 요소들을 꺼내서 병렬화 한다. 리스트 타입이 들어온다.
        // operator 을 작성할때마다 무슨 타입이 들어오는지 계산을 해야 한다.
        keesunEvents.stream().flatMap(v -> v.stream()).map(a-> a.getId()).forEach(System.out::println);
        keesunEvents.stream().flatMap(Collection::stream).map(oc-> oc.getId()).forEach(System.out::println);

        System.out.println("10 부터 1씩 증가하는 무제한 스트림 중에서 앞에 10개 빼고 최대 10개 까지만");
        // TODO : Stream 에서 무한 스트림을 생성하는방법 1.Stream.generate, 2.Stream.iterate : 중계형 Operator
        // forEach : Termianl Operator
        Stream.iterate(10, a -> a+1).skip(10).limit(10).forEach(System.out::println);

        System.out.println("자바 수업 중에 Test가 드렁있는 수업이 있는지 확인");
        // TODO : anyMatch
        boolean test = javaClass.stream().anyMatch(oc -> oc.getTitle().contains("test"));
        System.out.println(test);

        System.out.println("스프링 수업 중에 제목에 spring 이 들어간 것만 모아서 List로 만들기");
        // TODO
        springClass.stream().filter(v -> v.getTitle().contains("spring")).map(v -> v.getTitle()).forEach(System.out::println);

        List<String> spring = springClass.stream()
                .filter(v -> v.getTitle().contains("spring"))
                .map(v -> v.getTitle())
                .collect(Collectors.toList());
    }
}
