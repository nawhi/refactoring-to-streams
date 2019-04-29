package org.spaconference.rts.exercises;

import com.sun.org.apache.bcel.internal.generic.MULTIANEWARRAY;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.spaconference.rts.runner.ExampleRunner;
import org.spaconference.rts.runner.ExampleRunner.Way;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;


@RunWith(ExampleRunner.class)
public class ExL_IgnoreExceptions {

    @Way
    public static List<URL> oldWay(List<String> strings) {
        List<URL> uris = new ArrayList<>();
        for (String string : strings) {
            try {
                uris.add(new URL(string));
            } catch (MalformedURLException ignored) {
            }
        }
        return uris;
    }

    @Way
    public static List<URL> withOptionals(List<String> strings) {
        return strings.stream()
                .map(ExL_IgnoreExceptions::makeUrl)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    private static Optional<URL> makeUrl(String s) {
        try {
            return Optional.of(new URL(s));
        }
        catch (MalformedURLException ignored) {
            return Optional.empty();
        }
    }

    @Test
    public void good_urls_pass(UrlParser f) throws MalformedURLException {
        List<String> good_uris = asList(
                "http://example.com/example_a",
                "http://example.com/example_b",
                "http://example.com/example_c");

        assertThat(f.apply(good_uris), equalTo(asList(
                new URL("http://example.com/example_a"),
                new URL("http://example.com/example_b"),
                new URL("http://example.com/example_c")
        )));
    }

    @Test
    public void bad_urls_are_skipped(UrlParser f) throws MalformedURLException {
        List<String> mixed_uris = asList(
                "http://example.com/good",
                "example.com/bad",
                "http://example.com/good2");

        assertThat(f.apply(mixed_uris), equalTo(asList(
                new URL("http://example.com/good"),
                new URL("http://example.com/good2")
        )));
    }


    @FunctionalInterface
    public interface UrlParser {
        List<URL> apply(List<String> value) throws MalformedURLException;
    }
}
