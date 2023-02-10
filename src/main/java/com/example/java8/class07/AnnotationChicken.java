package com.example.java8.class07;

import java.lang.annotation.*;

// Retention 전략
//@Target(ElementType.TYPE_PARAMETER) // 어디에 애노테이션을 사용할 것인가 ( TYPE_PARAMETER : 타입파라미터 에 선언가능)
@Retention(RetentionPolicy.RUNTIME) // 애노테이션 정보를 언제까지 유지할 것인가.
@Target(ElementType.TYPE_USE) // 타입파라미터를 포함해서 타입을 선언하는 모든곳에 쓸 수 있게 된다., 타입선언하는곳 모든곳
@Repeatable(ContainerChicken.class) // 여러개의 애노테이션을 감싸고 있을 컨테이너 애노테션 타입을 선언해줘야한다.
public @interface AnnotationChicken {

    String value();
}
