package com.example.java8.class02;

/**
 * 6 인터페이스 기본 메소드와 스태택 매소드
 *
 * 기본 메소드 (Default Methods)
 * - 인터페이스에 메소드 선언이 아니라 구현체를 제공하는 방법
 * - 해당 인터페이스를 구현한 클래스를 깨트리지 않고 새 기능을 추가할 수 있다.
 * - 기본 메소드는 구현체가 모르게 추가된 기능으로 그만큼 리스크가 있다.
 *    > 컴파일 에러는 아니지만 구현체에 따라 런타임 에러가 발생할 수 있다.
 *    > 반드시 문서화 할 것. (@implSpec 자바독 태그 사용)
 *  - Object 가 제공하는 기능 (equals, hasCode) 는 기본 메소드로 제공할 수 없다.
 *    > 구현체가 재정의해야한다.
 *  - 본인이 수정할 수 있는 인터페이스에만 기본 메소드를 제공할 수 있다.
 *  - 인터페이스를 상속받는 인터페이스에서 다시 추상 메소드로 변경할 수 있다.
 *  - 인터페이스 구현체가 재정의 할 수도 있다.
 *
 *  스태틱 메소드
 *   - 해당 타입 관련 헬터 또는 유틸리티 메소드를 제공할 때 인터페이스에 스태틱 메소드를
 *   제공할 수 있다.
 *
 */
public class InterfaceFlow1Executor {

    public static void main(String[] args) {
        String name = "heejin";
        Foo foo = new FooDefault(name);
        System.out.println(foo.getName());
    }
}
