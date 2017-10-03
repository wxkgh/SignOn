package org.skynet.web.Utils;

public class StringUtils {
    public static final boolean isEmpty(String str) {
        if (str == null || str.length() == 0) {
            return true;
        }

        return false;
    }
}
