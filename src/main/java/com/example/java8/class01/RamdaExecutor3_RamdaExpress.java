package com.example.java8.class01;

import java.util.function.*;

/**
 * 4. 람다 표현식
 * 람다
 *  - (인자 리스트) -> {바디}
 *
 * 인자 리스트
 *  - 인자가 없을 때 : ()
 *  - 인자가 한개일 때 : (one) 또는 one
 *  - 인자가 여러개 일떄 : (one, two)
 *  - 인자의 타입은 생략 가능, 컴파일러가 추론(infer)하지만 명시할 수도 있다. (Integer one, Integer two)
 *
 *  바디
 *   - 화살표 오른쪽에 함수 본문을 정의한다.
 *   - 어려 줄인 경우에 {} 를 사용해서 묶는다.
 *   - 한 줄인 경우에 생략 가능, return 도 생략 가능
 *
 *   변수 캡처 (Varialbe Capture)
 *   - 로컬 변수 캡처
 *     - final 이거나 effective final 인 경우에만 참조할 수 있다.
 *     - 그렇지 않은경우 concurrency 문제가 생길 수 있어서 컴파일이 방지한다.
 *   - effective final
 *     - 이것도 역시 자바 8부터 지원하는 기능을로 "사실상" final 인 변수
 *     - final 키워드를 사용하지 않은 변수를 익명 클래스 구현체 또는 람다에서 참조할 수 있다.
 *   - 익명 클래스 구현체와 달리 '쉐도잉' 하지 않는다.
 *     - 익명 클래스는 새로 스콥을 만들지만, 람다는 람다를 감싸고 있는 스콥과 같다.
 *
 */
public class RamdaExecutor3_RamdaExpress {

    public static void main(String[] args) {
        Supplier<Integer> get10 = () -> 10;

        UnaryOperator<Integer> plus10 = (i) -> i + 10;
        UnaryOperator<Integer> multiply2 = (i) -> i * 2;

        BiFunction<Integer, Integer, Integer> get11 = (i, j) -> i + j + 11;
        BinaryOperator<Integer> get12 = (i, j) -> i + j + 12;

        // 변수캡쳐
        RamdaExecutor3_RamdaExpress re = new RamdaExecutor3_RamdaExpress();
        //re.run1();
        re.run2();
    }

    private void run1() {
        int baseNumber = 10; // local variable - 기존에는 final 이 있어야만

        // 1. LocalClass
        class LocalClass {
            void printNumber() {
                int baseNumber = 11;
                System.out.println("LocalClass : " + baseNumber); //11  가린다.
            }
        }

        // 2. 익명 클래스
        Consumer<Integer> integerConsumer = new Consumer<Integer>() {
            int baseNumber = 11;
            @Override
            public void accept(Integer baseNumber) { // baseNumber 위의 baseNumber 을 참조하지 않는다.
                // 이번수가 위의 변수를 가린다. -> 쉐도잉이 일어난다.
                System.out.println("익명 클래스 : " + baseNumber); // 익명클래스에서 local variable 찹조
            }
        };

        // 3. 람다
        IntConsumer printInt = (i) -> { // baseNumber 로 파라미터 선언 불가능
            System.out.println("람다 : " + i);
        };

        printInt.accept(10);

        // java 8 부터는 fianl 을 생략할 수 있는 경우가 있다.
        // fianl : 변경을 못함
        // 사실상 fianl : final 키워드는 없지만 키워드를 변경하지 않는 경우
        // effective final : final 키워드가 없어도 사실상 참조가 가능하다.
        //    만약 baseNumber++; 을 선언하면 람다내에서 사용 불가능 하다.
        //    final 처럼 사용하고있는데 이후에 변경이되면 effective final 처럼 사용할 수 없기떄문에 오류가난다.
        // 쉐도잉 :
        // 로컬클래스, 익명클래스는 쉐도잉이 되지만 -> 별도의 스코프 이기 때문에 쉐도잉이된다.
        // 람다는 웨도잉이 되지 않는다. -> 같은 스코프이기 떄문에 변경이 일어난다. 동일한 변수의 이름을 정의할 수 없다.
        // 내부에 같은명의 변수명이 있을경우 쉐도잉이된다.
    }

    private void run2() {
        // 변수 캡쳐 : 이 변수가 사실상 final 인경우 참조가능
        // 사실상 final 인경우 : 다른대에서 변경이 안된 변수인경우
        // 쉐도잉 :
        int baseNumber = 10;

        // 로컬클래스 : 쉐도잉이 된다.
        class LocalClass {

            void printBaseNumber() {
                int baseNumber = 11;

                System.out.println("로컬클래스 : " + baseNumber); // 11
            }
        }
        LocalClass localClass = new LocalClass();
        localClass.printBaseNumber();

        // 익명 클래스 : 쉐도잉이 된다.
        Consumer<Integer> integerConsumer = new Consumer<Integer>() {
            int baseNumber = 11;

            void accept2() {
                System.out.println("익명 클래스 accept2 : " + baseNumber);
            }

            @Override
            public void accept(Integer baseNumber) {
                System.out.println("익명 클래스 accept : " + baseNumber);
                accept2();//11
            }
        };
        integerConsumer.accept(baseNumber);

        // 람다 : 쉐도잉이 안된다. -> 쉐도잉이 일어나지 않는다.
        // 람다는 같은 스코프이다.
        // 익명 함수로 다른객체에 적용 가능한 연산이 모두 수행될 수 있는 일급 객체를 의미한다.
        // ※람다의 변수에는 final변수나 effective final 변수만 올 수 있다.
        IntConsumer printInt = (i) -> {
            // int baseNumber = 11; // Variable 'baseNumber' is already defined in the scope

            System.out.println("람다 : " + baseNumber);
        };

        // baseNumber++;

        printInt.accept(10);
    }
}
