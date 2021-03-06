package com.pivovarit.stream;

import org.junit.jupiter.api.Test;

import java.util.Collections;

import static com.pivovarit.stream.WindowSpliterator.windowed;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

class WindowStreamTest {

    @Test
    void applySlidingWindow() {
        var source = asList(1, 2, 3, 4);

        var result = windowed(source.stream(), 3)
          .map(s -> s.collect(toList()))
          .collect(toList());

        assertThat(result)
          .containsExactly(asList(1, 2, 3), asList(2, 3, 4));
    }

    @Test
    void applySlidingWindowToStreamSmallerThanWindow() {
        var source = asList(1, 2);

        var result = windowed(source.stream(), 3)
          .map(s -> s.collect(toList()))
          .collect(toList());

        assertThat(result)
          .containsExactly(asList(1, 2));
    }

    @Test
    void applySlidingWindowToEmptyStream() {
        var source = Collections.emptyList();

        var result = windowed(source.stream(), 3)
          .map(s -> s.collect(toList()))
          .collect(toList());

        assertThat(result).isEmpty();
    }

    @Test
    void applyZeroSlidingWindow() {
        var source = asList(1, 2, 3, 4);

        var result = windowed(source.stream(), 0)
          .map(s -> s.collect(toList()))
          .collect(toList());

        assertThat(result).isEmpty();
    }
}
