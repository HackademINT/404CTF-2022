package org.hallebarde.recrutement.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StringUtilTest {

    private static final String ALPHA_CHARS = "azertyuiopqsdfghjklmwxcvbnAZERTYUIOPQSDFGHJKLMWXCVBN";
    private static final String NON_ALPHA_CHARS = "0123456789 \t\n!\"#$%&'()*+-./:;<=>?@[\\]^_`{}|~";
    private static final String NUMERIC_CHARS = "0123456789";
    private static final String NON_NUMERIC_CHARS = "azertyuiopqsdfghjklmwxcvbnAZERTYUIOPQSDFGHJKLMWXCVBN \t\n!\"#$%&'()*+-./:;<=>?@[\\]^_`{}|~";

    @Test
    public void canRecognizeAlphaChars() {
        for (int i = 0; i < ALPHA_CHARS.length(); i++) {
            assertTrue(StringUtil.isAlpha(ALPHA_CHARS.substring(i, i + 1)));
        }
    }

    @Test
    public void doesNotMistakeAlphaChars() {
        for (int i = 0; i < NON_ALPHA_CHARS.length(); i++) {
            assertFalse(StringUtil.isAlpha(NON_ALPHA_CHARS.substring(i, i + 1)));
        }
    }

    @Test
    public void canRecognizeNumericChars() {
        for (int i = 0; i < NUMERIC_CHARS.length(); i++) {
            assertTrue(StringUtil.isNumeric(NUMERIC_CHARS.substring(i, i + 1)));
        }
    }

    @Test
    public void doesNotMistakeNumericChars() {
        for (int i = 0; i < NON_NUMERIC_CHARS.length(); i++) {
            assertFalse(StringUtil.isNumeric(NON_NUMERIC_CHARS.substring(i, i + 1)));
        }
    }

    @Test
    public void canFindLongestCommonSubscript() {
        assertEquals("", StringUtil.longestCommonSubscript("atcg", "uvnb"));
        assertEquals("a", StringUtil.longestCommonSubscript("atcg", "gcta"));
        assertEquals("atta", StringUtil.longestCommonSubscript("gattaca", "acattag"));
        assertEquals("gac", StringUtil.longestCommonSubscript("actggact", "tagacggt"));
    }

}
