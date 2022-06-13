package org.hallebarde.recrutement.util;

import java.util.Arrays;

public final class StringUtil {

    public static boolean isValidId(String id) {
        return !isNullBlankOrEmpty(id) &&
                isAlpha(id.substring(0, 1)) &&
                isSnakeCaseAlphaNumeric(id);
    }

    public static boolean isNullBlankOrEmpty(String str) {
        return str == null || str.isBlank();
    }

    public static boolean isAlpha(String str) {
        int strLength = str.length();
        for (int i = 0; i < strLength; i++) {
            char c = str.charAt(i);
            if (!((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z'))) return false;
        }
        return true;
    }

    public static boolean isNumeric(String str) {
        int strLength = str.length();
        for (int i = 0; i < strLength; i++) {
            char c = str.charAt(i);
            if (!(c >= '0' && c <= '9')) return false;
        }
        return true;
    }

    public static boolean isAlphaNumeric(String str) {
        int strLength = str.length();
        for (int i = 0; i < strLength; i++) {
            char c = str.charAt(i);
            if (!((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z') || (c >= '0' && c <= '9'))) return false;
        }
        return true;
    }

    public static boolean isSnakeCaseAlphaNumeric(String str) {
        return Arrays.stream(str.split("_")).allMatch(StringUtil::isAlphaNumeric);
    }

    public static String longestCommonSubscript(String str1, String str2) {
        String best = "";
        for (int i = 0; i < str1.length(); i++) {
            for (int j = 0; j < str2.length(); j++) {
                StringBuilder current = new StringBuilder();
                for (int k = 0; i + k < str1.length() && j + k < str2.length(); k++) {
                    char char1 = str1.charAt(i + k);
                    if (char1 !=  str2.charAt(j + k)) break;
                    current.append(char1);
                }
                if (current.length() > best.length()) {
                    best = current.toString();
                }
            }
        }
        return best;
    }

}
