package com.example.java8.class05;

import java.sql.SQLOutput;
import java.sql.Time;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * Date/Time API
 *
 * 지금 이 순간을 기계 시간으로 표현하는 방법
 * Instant.now()
 *
 * */
public class DateTimeApiExecutor2 {
    public static void main(String[] args) {
        // 1. Machine 용
        Instant isntant = Instant.now(); // 지금 시간을 기계식으로 찍어준다.
        System.out.println("Instant.now : " + isntant); // 기준시 UTC, GMT
        System.out.println("isntant.atZone : " + isntant.atZone(ZoneId.of("UTC")));

        ZoneId zone = ZoneId.systemDefault();
        System.out.println(zone); // 어느 기준 zone 으로 시스템 시간을 볼것이냐

        ZonedDateTime zonedDateTime = isntant.atZone(zone);
        System.out.println("ZonedDateTime : " + zonedDateTime);
        
        
        // 2. human 용
        LocalDateTime now = LocalDateTime.now();
        System.out.println("LocalDateTime.now : " + now);

        LocalDateTime birthDate =
                LocalDateTime.of(1982, Month.JULY, 15, 0, 0, 0);

        // ZonedDateTime : 특정 ZONE 의 시간을 보고 싶을 떄
        ZonedDateTime nowInKorea = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
        System.out.println("ZonedDateTime.now : " + nowInKorea);

        Instant nowInstant = Instant.now();
        ZonedDateTime zonedDateTime1 = nowInstant.atZone(ZoneId.of("Asia/Seoul"));
        System.out.println("nowInstant.atZone : " + zonedDateTime1);

        // 3. 기간을 포현하는 방법
        // 3_1 human 용 : LocalDate / Period / ChronoUnit
        LocalDate today = LocalDate.now();
        LocalDate thisYearBirthDay = LocalDate.of(2022, Month.JULY, 18);

        // 참고 : src/main/java/com/skmns/b2b/scheduler/monitor/odc/service/MonitoringSystemService.java
        Period period = Period.between(today, thisYearBirthDay);
        System.out.println("period : " + period.getDays());

        Period until = today.until(thisYearBirthDay);
        System.out.println("until : " + until.get(ChronoUnit.DAYS));

        // 3_2 machine 용 : Instant / Duration
        Instant now1 = Instant.now();
        Instant plus1 = now1.plus(10, ChronoUnit.SECONDS); //ChronoUnit : 특성상 immutable 하고 새로운 객체가 만들어진다.

        Duration between = Duration.between(now1, plus1);
        System.out.println(between.getSeconds());

        // 4. 피싱 또는 포매팅
        // 4_1 formatting : DateTimeFormatter.ofPattern
        LocalDateTime now2 = LocalDateTime.now();
        System.out.println("LocalDateTime : " + now2);

        DateTimeFormatter MMddyyyy = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        System.out.println("DateTimeFormatter.ofPattern : " + now2.format(MMddyyyy));

        // 4_2 parsing : LocalDate.parse
        LocalDate parse = LocalDate.parse("07/15/1982", MMddyyyy);
        System.out.println("LocalDate.parse : " + parse);

        // 5. 레거시 API 지원
        // 우리가 사용하는 API 들이 예전의 API 들과 호환이 돈다.

        // 5_1 Date -> Instant
        Date date = new Date();
        Instant instant5 = date.toInstant();

        // 5_2 Instant -> Date
        Date newDate = Date.from(instant5);


        GregorianCalendar gregorianCalendar = new GregorianCalendar();

        // 5_3 GregorianCalendar -> LocalDateTime
        LocalDateTime dateTime5 = gregorianCalendar
                                        .toInstant()
                                        .atZone(ZoneId.systemDefault())
                                        .toLocalDateTime();

        // 5_4 GregorianCalendar -> ZonedDateTime
        ZonedDateTime dateTime5_1 = gregorianCalendar.toInstant().atZone(ZoneId.systemDefault());

        // 5_5 ZonedDateTime -> GregorianCalendar
        GregorianCalendar from = GregorianCalendar.from(dateTime5_1);

        // ZoneId : 새로운 API
        // TimeZone : 레거시 API (java.util)
        ZoneId zoneId = TimeZone.getTimeZone("PST").toZoneId(); // PST pacific time zone
        TimeZone timeZone = TimeZone.getTimeZone(zoneId);

        // 새로운 인스턴스가 나오므로 무조건 받아야한다.
        LocalDateTime now5 = LocalDateTime.now();
        LocalDateTime now5_1 = now5.plus(10, ChronoUnit.DAYS);
    }

}
