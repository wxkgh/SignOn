package org.skynet.web.Helper;

import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Helper {

    public static String encodeByMd5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        BASE64Encoder base64Encoder = new BASE64Encoder();
        String md5str = base64Encoder.encode(md5.digest(str.getBytes("utf-8")));
        return md5str;
    }

    public static boolean checkPassword(String inputPassword, String getPassword) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        if (encodeByMd5(inputPassword).equals(encodeByMd5(getPassword))) {
            return true;
        } else {
            return false;
        }
    }
}

