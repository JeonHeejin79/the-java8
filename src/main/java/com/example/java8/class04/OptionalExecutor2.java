package com.example.java8.class04;

import com.example.java8.class03.OnlineClass;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * 5부 Optional
 *
 * 11. Optional2
 *
 * Optional 만들기
 *  - Optional.of()
 *  - Optional.ofNullable()
 *  - Optional.empty()
 *
 *  Optional에 값이 있는지 없는지 확인하기
 *  - isPresent()
 *  - isEmpty() : java11 부터 제공
 *
 *  Optional에 있는 값 가져오기
 *   - get()
 *   - 만약에 비어있는 Optional 에 무언가를 꺼낸다면
 *
 *  Optional에 값이 있는 경우에 그 값을 가지고 ~~~를 하라.
 *   - ifPresentConsumer)
 *   - 예) Spring 으로 시작하는 수업이 있으면 idfmf 출력하라
 *
 *  Optional 에 값이 있으면 가져오고 없는 경우에 ~~~를 리턴하라.
 *   - orElse(T)
 *   - 예) JPA 로 시작하는 수업이 없다면 새로 만들어서 리턴하라
 *
 *  Optional 에 값이 있으면 가져오고 없는 경우 에러를 던져라
 *   - orElseThrow()
 *
 *  Optional에 들어있는 값 걸러내기
 *   - Optional filter(Predicate)
 *
 *  Optional에 들어있는 값 변환하기
 *   - Optional map(Function)
 *   - Optional flatMap(Function): Optional 안에 들어있는 인스턴스가 Optional 인경우에 사용하면 편리하다.
 *
 */
public class OptionalExecutor2 {

    public static void main(String[] args) {
        List<OnlineClass> springClass = new ArrayList<>();

        springClass.add(new OnlineClass(1, "spring boot", true));
        springClass.add(new OnlineClass(2, "spring data jpa", true));
        springClass.add(new OnlineClass(3, "spring mvc", false));
        springClass.add(new OnlineClass(4, "spring core", false));
        springClass.add(new OnlineClass(5, "rest api development", false));

        // optional 을 리턴하는 오퍼레이션들이 있다. : 종료형 오퍼레이션들
        // findFirst
        Optional<OnlineClass> spring = springClass.stream()
                .filter(oc -> oc.getTitle().startsWith("spring"))
                .findFirst();

        boolean present = spring.isPresent();
        System.out.println(present);

        Optional<OnlineClass> jpa = springClass.stream()
                .filter(oc -> oc.getTitle().startsWith("jpa"))
                .findFirst();

        boolean present2 = spring.isPresent();
        System.out.println(present2);

        // 클래서 다시 꺼내기 : get 으로 꺼낼때 ifPresent 로 처리
        Optional<OnlineClass> optional = springClass.stream()
                .filter(oc -> oc.getTitle().startsWith("spring"))
                .findFirst();

        OnlineClass onlineClass1 = null;

        if (optional.isPresent()) {
            onlineClass1 = optional.get(); // get 으로 꺼냈을때 값이 없을경우 에러 -> isPresent 로 체크
            System.out.println(onlineClass1.getTitle());
        }

        // 수행할떄 - 객체가 존재하면 수행
        optional.ifPresent(oc -> System.out.println(oc.getTitle()));
        // 가져올때 - 있으면 가져오고 수행하고 없으면 수행해라, 잇어도 코드가수행 : orElse
        OnlineClass onlineClass = optional.orElse(createNewClass());
        // 가져올떄 - 있으면 가져오고 없으면 수행해서 가져와라, 잇으면 코드수행안함 : orElseGet
        OnlineClass onlineClass2 = optional.orElseGet(OptionalExecutor2::createNewClass);
        // orElseThrow 에러발행 : 기본적으로 NoSuchElementException 을 던진다. 다른 Exeption 을 던질 수 있다.
        OnlineClass onlineClass3 = optional.orElseThrow(IllegalStateException::new);
        System.out.println(onlineClass3.getTitle());

        // filter 있다는 가정하게 동작, 없으면 동자하지않는다.
        Optional<OnlineClass> onlineClass4 = optional.filter(oc -> !oc.isClosed());
        System.out.println(onlineClass4.isEmpty());

        // map
        Optional<Integer> onlineClass5 = optional.map(OnlineClass::getId);
        System.out.println(onlineClass5.isPresent());

        // flatMap
        Optional<Progress> progress = optional.flatMap(OnlineClass::getProgress);

    }

    private static OnlineClass createNewClass() {
        System.out.println("create new online class");
        return new OnlineClass(10, "New Class", false);
    }
}
