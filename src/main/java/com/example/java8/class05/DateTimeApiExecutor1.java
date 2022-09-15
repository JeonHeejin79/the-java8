package com.example.java8.class05;

import java.sql.SQLOutput;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Date 와 Time API
 * 12 Date 와 Time API 소개
 * 자바 8에 새로운 날짜와 시간 API 가 생긴 이유
 *  - 그전까지 사용하던 java.util.Date 클래스는 mutable 하기 때문에 thread safe 하지 않다.
 *  - 클래스 이름이 명확하지 않다. Date 인테 시간까지 다룬다.
 *  - 버그 발생할 여지가 많다. (타입 안정성이 없고, 월이 0부터 시작한다거나..)
 *  - 날짜 시간 처리가 복잡한 애플리케이션에서 보통 Joda Time 을 쓰곤 했다.
 *
 * 자바8에서 제공하는 Date-Time API
 *  - JSR-310 스팩의 구현체를 제공한다.
 *  - 디자인 철학
 *    - Clear
 *    - Fluent
 *    - Immutable
 *    - Extensible
 *
 *  주료 API
 *   - 기계용 시간(machine time)과 인류용 시간 (human time) 으로 나눌 수 있다.
 *   - 기계용 시가은 EPOCK (1970년 1월 1일 0시 0분 0초)부터 현재까지의 타임스탬프를 표현한다.
 *   - 인류용 시간은 우리가 흔히 사용하는 연, 워리,일,시,분,초 등을 표현한다.
 *   - 타임스탬프는 Instant 를 사용한다.
 *   - 특정 날짜 (LocalDate), 시간(LocalTime), 일시(LocalDateTime) 를 사용할 수 있다.
 *   - 기간을 표현할 때는 Duration(시간 기반)과 Period(날짜 기반) 를 사용할 수 있다.
 *   - DateTimeFormatter 를 사용해서 일시를 특정한 문자열로 포매팅 할 수 있다.
 */
public class DateTimeApiExecutor1 {

    public static void main(String[] args) throws Exception {

        /** 1. Date 타입의 불명확성 */
        // 불편한점 : 작업이 잘안되어있다.
        // Date 지만 시간도있고 TimeStamp 도 포함되어있다.
        // Date 이지만 date.getTime() 을할수 있다.
        // 여시 이 시간은 1960, 1, 2 00:00:00 이 기준으로 date second 를 리톤해준다. 이폭타임이라고 한다.

        // 그런데 이 date 는 또 바뀐다.
        Date date = new Date();
        Calendar calendar = new GregorianCalendar();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();

        long time = date.getTime(); // returned epoc time
        System.out.println("Date : " + date);
        System.out.println("Date > getTime : " + time);

        //Date : Mon Aug 01 15:31:03 KST 2022
        //Date > getTime : 1659335463151

        /** 2. Date Type 안성정 X */
        Thread.sleep(1000*3);
        Date after3seconds = new Date();
        System.out.println("after3seconds : " + after3seconds);
        after3seconds.setTime(time);
        // 객체 상태를 바꿀수 있다. mutable 하다. 이런 mutable 한 객체는 멀티스레드에서 안전하게 쓰기 어렵다.
        // 버그 발생할 여지가 많다.
        System.out.println("after3seconds : " + after3seconds);

        //after3seconds : Mon Aug 01 15:31:06 KST 2022
        //after3seconds : Mon Aug 01 15:31:03 KST 2022

        Calendar keesunBirthDay1 = new GregorianCalendar(1982, 7, 15);
        // 이렇게 사용하면 버그가 있다. month 에 7-1 을 해줘야한다. month 는 0 부터 시작한다.
        // 실수할 여지가 많다.
        // 특정한 Enum 만 받을수 있게끔 안한것을 Type safety 가 보장되지 않는다.
        // 아무값이나 들어올 수 있다.
        Calendar birthDay = new GregorianCalendar(1982, Calendar.JULY, 15);
        System.out.println("birthDay : " + birthDay.getTime());
        birthDay.add(Calendar.DAY_OF_YEAR, 1);
        System.out.println("birthDay : " + birthDay.getTime());

        //birthDay : Thu Jul 15 00:00:00 KST 1982
        //birthDay : Fri Jul 16 00:00:00 KST 1982

        // 이런 문제가 있기 때문에 Joda-Time 을 썻었다.
        // 이게 Java 쪽에 표준으로 들어오게 되면서 java.time 패키지 않에 들어오게 됐다.
        // 특징 :
        // clear : 명확해야한다.
        // fluent : 유연해졌다.
        // immutable : 기존에있는 인스턴스를 리턴하는게아니라 새로운 인스턴스를 리턴한다.
        // extensible :

        Date date2 = new Date();
        long time2 = date2.getTime();
        System.out.println(time2);

        // 시간자체를 2가지로 나눌 수 있다.
        // 기계용시간 (machine time) : 기계용 시간은 (1970년 1월 1일 0시 0분 0초) 부터 현재까지 타임스탬프를 표현한다.
        // 인류용 시간 (human time) : 인류용시간은 우리가 흔히 사용하는 연, 월, 일, 시 분, 초  등을 표현한다.
        // 타임스탬프는 Instant 를 표헌한다.
    }
}
