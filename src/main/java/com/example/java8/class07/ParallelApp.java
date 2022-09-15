package com.example.java8.class07;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

/**
 * 20. 배열 Parallel 정렬
 * Arrays.parallelSort()
 *  - Fork/Join 프레임워크를 사용해서 배열을 병렬로 정렬하는 기능을 제공한다.
 * 병렬 정렬 알고리듬
 *  - 배열을 둘로 계속 쪼갠다.
 *  - 합치면서 정렬한다.
 */
public class ParallelApp {

    public static void main(String[] args) {
        int size = 1500;
        int[] numbers = new int[size];
        Random random = new Random();

        IntStream.range(0, size).forEach(i  -> numbers[i] = random.nextInt());
        long start = System.nanoTime();

        /** quick sort */
        // java 는 기본적으로 quick sort 를 쓴다. Dual-Pivot Quicksort
        // O(n log(n)) 으로 비교적 빠른 알고리즘을 사용하나
        // 싱글스레드를 사용하기 떄문에 비교적으로 제한이 있다.
        Arrays.sort(numbers);
        System.out.println("serial sorting took "  + (System.nanoTime() - start));

        /** parallel sort */
        // 쪼개면서 정렬하고 합친다.
        // 이런 분산 정렬을 pork join 프레임워크에 제공받은 여러 스레드에 분산해서 처리한다.
        // 자원에 따라 시간이 달라질 수 있다.
        IntStream.range(0, size).forEach(i -> numbers[i] = random.nextInt());
        start = System.nanoTime();
        Arrays.parallelSort(numbers);
        System.out.println("parallel sorting took " + (System.nanoTime() - start));

        /* result
         * serial sorting took 855400
         * parallel sorting took 388900
         *
         * 알고리즘 효율성은 같다.
         * 시간 O(n logN)
         * 공간 O(n)
         */
    }
}
