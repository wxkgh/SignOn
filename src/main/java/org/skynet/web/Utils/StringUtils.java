package org.skynet.web.Utils;

public class StringUtils {
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static boolean isNotEmpty(String str) {
        return ! isEmpty(str);
    }
}
