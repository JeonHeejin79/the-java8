package com.example.java8.class03;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 4 부 Stream
 *
 * 8. Stream 소개
 *
 * Stream
 * - sequence of elements (엘리먼트들의 연속 )
 *    supporting sequential and parallel aggregate operations
 * - 데이터를 담고 있는 저장소 (컬렉션)이 아니다.
 * - Functional in nature, 스크림이 처리하는 **데이터 소스를 변경하지 않는다.
 * - 스트림으로 처리하는 데이터는 오직 **한번만 처리한다.
 * - 무제한일 수도 있다. (Short Circuit 메소드를 사용해서 제한할 수 있다.)
 * - 중개 오퍼레이션은 근본적으로 **lazy 하다.
 * - 손쉽게 **병렬 처리할 수 있다.
 *
 * 스크림 파이프라인
 *  - 0또는 다수의 중개 오퍼레이션(intermediate operation)과
 *         한개의 종료 오퍼레이션(terminal operation) 으로 구성한다.
 *  - 스트림의 데이터 소스는 오직 터미널 오퍼레이션을 실행할 때에만 처리한다.
 *
 * 중개 오퍼레이션
 *   - Stream 을 리턴한다.
 *   - terminal operator 이 오기전까지는 실행을 하지 않는다.
 *   - Stateless / Stateful 오퍼레이션으로 더 상세하게 구분할 수도 있다.
 *      ( 대부분은 Stateless 지만 distinct 나 sorted 처럼 이전 이전 소스 데이터를 차몾해야
 *        하는 오퍼레이션은 Stateful 오퍼레이션이다. )
 *   - filter, map, limit, skip, sorted
 *
 * 종료 오퍼레이션
 *    - Stream 을 리턴하지 않는다.
 *    - collect, allMatch, count, forEach, min, max,...
 */
public class StreamApiExecutor1 {

    public static void main(String[] args) {
        List<String> names = new ArrayList<>();
        names.add("keesun");
        names.add("whiteship");
        names.add("toby");
        names.add("foo");

//        Stream<String> stringStream = names.stream()
//                .map(String::toUpperCase);
//        names.forEach(System.out::println);

        List<String> collect = names.stream().map((s) -> {
            System.out.println(s);
            return s.toUpperCase();
        }).collect(Collectors.toList()); // collect : 종료형 operator 리턴타입이 List 이다. =
        System.out.println("==============================");
        names.forEach(System.out::println); // 원본은 바뀌지 않는다.
        System.out.println("==============================");
        collect.forEach(System.out::println);
        System.out.println("==============================");

        // 스트림을 안쓰고 병렬처리
        for (String name: names) {
            if (name.startsWith("K")) {
                System.out.println(name.toUpperCase());
            }
        }

        // 스트림을 사용해서 병렬처리 : 데이터가 방대할경우 parallelStream 이 더 유용할 수 있다.
        List<String> collect2 = names.parallelStream().map(String::toUpperCase)
                .collect(Collectors.toList());
        collect2.forEach(System.out::println);

        List<String> collect3 = names.parallelStream().map((s) -> {
            System.out.println(s + " " + Thread.currentThread().getName());
            return s.toUpperCase();
        }) .collect(Collectors.toList());
        collect3.forEach(System.out::println);
    }
}
