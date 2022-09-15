package com.example.java8.class07;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME) // 컨테이너의 애노테이션전략은 적용범위 하위 애노테이션 전략보다 같거나 넓어야한다.
@Target(ElementType.TYPE_USE)
public @interface ContainerChicken {

    // 자기자신을 감싸는 애노테이션들을 배열로 갖고있어야 한다.
    AnnotationChicken[] value();
}
