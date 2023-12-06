package com.github.pawelszumny.socialnetwork.utils;

import org.junit.Assert;

public class JsonAssertUtils {
    public static void assertJsonEquals(String message, String expected, String actual) {
        expected = expected.replaceAll("\\s", "");
        actual = actual.replaceAll("\\s", "");
        Assert.assertEquals(message, expected, actual);
    }
}
