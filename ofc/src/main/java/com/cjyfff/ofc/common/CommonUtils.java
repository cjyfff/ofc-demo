package com.cjyfff.ofc.common;

/**
 * Created by jiashen on 19-9-20.
 */
public class CommonUtils {
    public static String lpad(String s, int n, String replace) {
        StringBuilder sBuilder = new StringBuilder(s);
        while (sBuilder.length() < n) {
            sBuilder.insert(0, replace);
        }
        s = sBuilder.toString();
        return s;
    }
}
