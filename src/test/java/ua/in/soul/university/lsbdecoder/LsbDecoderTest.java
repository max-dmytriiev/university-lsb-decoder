package ua.in.soul.university.lsbdecoder;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class LsbDecoderTest {

    @Test
    public void testWord01() {
        // WHEN
        assertThat(LsbDecoder.decode("words/word1.lsbmap"), is("test12"));
    }

    @Test
    public void testWord02() {
        // WHEN
        assertThat(LsbDecoder.decode("words/word2.lsbmap"), is("telugu"));
    }

    @Test
    public void testWord03() {
        // WHEN
        assertThat(LsbDecoder.decode("words/word3.lsbmap"), is("aegina"));
    }

    @Test
    public void testWord04() {
        // WHEN
        assertThat(LsbDecoder.decode("words/word4.lsbmap"), is("Hello!"));
    }
}