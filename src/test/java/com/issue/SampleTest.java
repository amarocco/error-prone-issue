package com.issue;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.time.Period;
import java.util.AbstractMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collector;
import java.util.stream.Stream;

public class SampleTest {

    @Test
    public void failure() {
        Assertions.assertThatExceptionOfType(ClassCastException.class)
                .isThrownBy(() -> getInput().collect(toSortedMap()))
                .as("If Key is not comparable we must provide a comparator")
                .withMessage("java.time.Period cannot be cast to java.lang.Comparable");
    }

    private static Stream<Map.Entry<Period, Integer>> getInput() {
        return Stream.of(
                entry(Period.ofYears(1), 1),
                entry(Period.ofYears(2), 2),
                entry(Period.ofYears(5), 5),
                entry(Period.ofYears(3), 3)
        );
    }

    private static <K, V> Map.Entry<K, V> entry(K key, V value) {
        return new AbstractMap.SimpleEntry<>(key, value);
    }

    private static <K, V> Collector<Map.Entry<K, V>, ?, SortedMap<K, V>> toSortedMap() {
        return java.util.stream.Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> a, TreeMap::new);
    }

}
