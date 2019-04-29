package org.spaconference.rts.exercises;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.spaconference.rts.runner.ExampleRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.function.IntFunction;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.spaconference.rts.runner.ExampleRunner.Way;


@RunWith(ExampleRunner.class)
public class ExM_Generator {

    @Way
    public static List<Integer> oldWay(int count) {
        List<Integer> result = new ArrayList<>();
        int i1 = 1;
        int i2 = 2;

        for (int i = 0; i < count; i++) {
            int t = i1;
            i1 = i2;
            i2 = t + i2;
            result.add(t);
        }
        return result;
    }

    @Way
    public static List<Integer> newWay(int count) {
        return Stream.generate(new FibonacciGenerator())
                .limit(count)
                .collect(toList());
    }

    static class FibonacciGenerator implements Supplier<Integer> {
        private int i1 = 1;
        private int i2 = 2;

        @Override
        public Integer get() {
            int next = i1;
            i1 = i2;
            i2 += next;
            return next;
        }
    }

    @Test
    public void test(IntFunction<List<Integer>> f) {
        assertThat(f.apply(10), equalTo(asList(1, 2, 3, 5, 8, 13, 21, 34, 55, 89)));
    }

}
