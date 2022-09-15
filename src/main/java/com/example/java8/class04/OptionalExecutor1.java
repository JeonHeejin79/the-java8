package com.example.java8.class04;

import com.example.java8.class03.OnlineClass;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 5부 Optional
 *
 * 10. Optional 소개
 * 자바 프로그래밍에서 NullPointException 을 종종 보게 되는 이유
 *  - null 을 리턴하니까! && null 체크를 깜빡했으니까!
 *  
 *  메소드에서 작업 중 특별한 상황에서 값을 제대로 리턴할 수 없는 경우 선택할 수 있는 방법
 *   - 예외를 던진다. (비싸다. 스택트레이스를 찍어두니까.)
 *   - null 을 리턴한다. (비용 문제가 없지만 그 코드를 사용하는 클라이언트 코드가 주의해야 한다.)
 *   - 자바 8부터 Optional 을 리턴한다. (클라이언트에 코등게 명시적으로 빈 값일 수도
 *     있다는걸 알려주고, 빈 값인 경우에 대한 처리를 강제한다. )
 *
 *  Optional
 *   - 오직 값 한 개가 들어있을 수도 없을 수도 있는 컨테이너
 *   - Optional.of ( ) 객체가 null 이 아닌경우
 *   - Optional.ofNullable () 객체가 null 일수 있는 경우
 *
 *  주의할 것
 *   - 리턴값으로만 쓰기를 권장한다. (메소드 매개변수 타입,맵의 키 타입, 인스턴스 필드 타입으로 쓰지 말자)
 *   - Optional 을 리턴하는 메소드에서 null 을 리턴하지 말자
 *   - 프리미티브 타입용 Optional 은따로 있다. OptionalInt, OptionalLong...
 *   - Collection, Map, Stream Array, Optional은 Optional 로 감싸지 말것
 */
public class OptionalExecutor1 {

    public static void main(String[] args) {
        List<OnlineClass> springClass = new ArrayList<>();

        springClass.add(new OnlineClass(1, "spring boot", true));
        springClass.add(new OnlineClass(2, "spring data jpa", true));
        springClass.add(new OnlineClass(3, "spring mvc", false));
        springClass.add(new OnlineClass(4, "spring core", false));
        springClass.add(new OnlineClass(5, "rest api development", false));

        OnlineClass spring_boot = new OnlineClass(1, "spring boot", true);
        Optional<Progress> progress = spring_boot.getProgress();
//        if (progress != null) { // 문제 1. 매번 null 체크를 하지 않는다. 2. null 을 리턴하는것이 문제다
//            System.out.println(progress.m);
//        }
    }
}
