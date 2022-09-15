package com.example.java8.class01;

/**
 * 일급 함수 (First-Class Function)
 * 함수를 다른 변수와 동일하게 다루는 언어는 일급 함수를 가졌다고 표현합니다.
 * 예를 들어, 일급 함수를 가진 언어에서는 함수를 다른 함수에 매개변수로 제공하거나,
 * 함수가 함수를 반환할 수 있으며,
 * 변수에도 할당할 수 있습니다.
 *
 * 고차 함수(Higher-Order Function)
 * 고차 함수는 함수를 인자로 받거나 또는 함수를 반환함으로써 작동 하는 함수를 말합니다.
 * 간단히 말하자면, 고차 함수는 함수를 인자로 받거나 함수를 출력(output)으로 반환하는(return) 함수를 말합니다.
 *
 * 순수함수
 * 입력받은값이 동일한경우 결과가 같아야 한다.
**/

public class RamdaExecutor1_Executor {

    public static void main(String[] args) {
        // 1. 익명 내부 클래스 anonymous inner class
        RamdaExecutor1_Intro runSomething = new RamdaExecutor1_Intro() {
            @Override
            public void doIt() {
                System.out.println("Hello");
            }
        };

        // 람다 표현식 (Lambda Expression) > 내부적으로는 익명 내부 클래스와 동일
        RamdaExecutor1_Intro runSomething2 = () -> System.out.println("Hello");
        RamdaExecutor1_Intro runSomething3 = () -> {
            System.out.println("Hello");
            System.out.println("Hello2");
        };

        // 실행
        runSomething2.doIt();

        // 2. 함수 : 입력받은 값 이 동일한경우 결과가 같아야한다.
        RunSomething runSomething4 = (number) -> {
            return number + 10;
        };

        System.out.println(runSomething4.doIt(1));
        System.out.println(runSomething4.doIt(1));
        System.out.println(runSomething4.doIt(1));
        System.out.println(runSomething4.doIt(2));
        System.out.println(runSomething4.doIt(2));
        System.out.println(runSomething4.doIt(2));

        // 익명 내부 클래스
        // 함수안에서 함수 밖에 값을 참조해서 쓰는 경우
        // 외부에 있는 값을 변경하려고 한는경우
        // 함수형 프로그래밍을 하려는경우 주의
        RunSomething runSomething5 = new RunSomething() {
            int baseNumber = 10;
            @Override
            public int doIt(int number) {
                baseNumber++;
                return number + baseNumber;
            }
        };
    }
}
