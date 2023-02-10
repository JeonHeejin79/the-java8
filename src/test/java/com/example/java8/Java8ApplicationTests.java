package com.example.java8;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
class Java8ApplicationTests {

	@Test
	void contextLoads() {
		String a = null;
		String s = Optional.ofNullable(a).orElse("");

		System.out.println("s = " + a);
		System.out.println("s = " + s);

		int i = Integer.parseInt("");
		System.out.println("i = " + i);

	}


}
