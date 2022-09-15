package com.example.java8.class07;

import java.util.Arrays;
import java.util.List;

/**
 * 8부 그밖에..
 *
 * 19 애노테이션의 변화
 * 애노테이션 관련 두가지 큰 변화
 *  - 자바 8부터 애노테이션을 타입 선언부에도 사용할 수 있게된
 *  - 자바 8부터 애노테이션을 중복해서 사용할 수 있게 됨
 *
 *  타입 선언 부
 *   - 제네릭 타입
 *   - 변수 타입
 *   - 매개변수 타입
 *   - 예외 타입
 *   - ...
 *
 *  타입에 사용할 수 있으려면
 *   - TYPE_PARAMETER : 타입 변수에만 사용할 수 있다.
 *   - TYPE_USE : 타입 변수를 포함해서 모든 타입 선언부에 사용할 수 있다.
 *
 *  중복 사용할 수 있는 애노테이션을 만들기
 *   - 중복 사용할 애노테이션 만들기
 *   - 중복 애노테이션 컨테이너 만들기
 *      ㄴ 컨테이너 애노테이션은 중복 애노테이션과 @Retention 및 @Target 이 같거나 더 넓어야 한다.
 */
@AnnotationChicken("양념")
@AnnotationChicken("마늘간장") // 여러개의 애노테이션을 충복해서 한꺼번에 사용
public class AnnotationApp {

    public static void main(@AnnotationDefault String[] args) throws @AnnotationDefault RuntimeException {
        // 타입에 사용하는 애노테이션
        List<@AnnotationDefault String> names = Arrays.asList("keesun");

        // 중복 사용할 수 있는 애노테이션
        // - 1. class 에서 getAnnotationsByType 으로 치킨타입으로 바로 읽어오는 방법
        //  이 타입에 해당하는 애노테이션이 배열로 온다.
        AnnotationChicken[] annotationChickens = AnnotationApp.class.getAnnotationsByType(AnnotationChicken.class);
        Arrays.stream(annotationChickens).forEach(c -> {
            System.out.println(c.value());
        });

        // 2. 컨테이너 타입으로 가져오는 방법
        ContainerChicken containerChicken = AnnotationApp.class.getAnnotation(ContainerChicken.class);
        Arrays.stream(containerChicken.value()).forEach(c -> {
            System.out.println(c.value());
        });
    }
    
    static class FeelsLikeChicken<@AnnotationDefault T> { // T : 타입파라미터 제네릭

        // 타입 파라미터를 지정할때는 리턴타입 전에 위치해야 한다.
        // <C> : 타입파라미터 : 애노테이션 Target 을 TYPE_PARAMETER 지정했을경우 여기에 사용 가능하다.
        // (C c) : 타입
        public static <@AnnotationDefault C> void print (@AnnotationDefault C c) {
            System.out.println(c);
        };
    }
}
