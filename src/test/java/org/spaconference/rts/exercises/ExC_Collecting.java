package org.spaconference.rts.exercises;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.spaconference.rts.runner.ExampleRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.StreamSupport;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static java.util.stream.StreamSupport.stream;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.spaconference.rts.runner.ExampleRunner.Way;


@RunWith(ExampleRunner.class)
public class ExC_Collecting {

    @Way
    public static List<String> oldWay(Iterable<String> things) {
        List<String> result = new ArrayList<>();
        for (String thing : things) {
            result.add(thing);
        }
        return result;
    }

    @Way
    public static List<String> newWay(Iterable<String> things) {
        return stream(things.spliterator(), false)
                .collect(toList());
    }

    @Test
    public void test(Function<Iterable<String>, List<String>> f) {
        assertThat(f.apply(asList("one", "two", "three")), equalTo(asList("one", "two", "three")));
    }
}
