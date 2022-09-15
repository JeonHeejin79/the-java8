package com.example.java8.class02;

// Foo 와 Bar 를 둘다 implements 하는경우
// 충돌하는 default 메소드가 잇는경우에는
// 직적 overriding 해야한다.  printNameUpperCase
public class FooDefault implements Foo, Bar{

    String name;

    public FooDefault(String name) {
        this.name = name;
    }

    @Override
    public void printName() {
        System.out.println(this.name);
    }

    @Override
    public String getName() {
        return this.name;
    }

    // 재정의 가능 ,
    // Foo, Bar 에 충돌하는 default 메소드가 있는경우 직접 구현을 해야한다.
    @Override
    public void printNameUpperCase() {
        System.out.println(this.name.toUpperCase());
    }
}
