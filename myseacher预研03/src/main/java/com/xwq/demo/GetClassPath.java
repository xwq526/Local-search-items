package com.xwq.demo;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xuwenqing
 * Date: 2021-01-26
 * Time: 21:59:00
 */
public class GetClassPath {
    public static void main(String[] args) throws UnsupportedEncodingException {
        String classesPath = GetClassPath.class.getProtectionDomain()
                .getCodeSource().getLocation().getFile();
        System.out.println(classesPath);
        String decode = URLDecoder.decode(classesPath,"UTF-8");
        System.out.println(decode);
        File classesDir = new File(decode);
        System.out.println(classesDir.getAbsoluteFile());

    }
}
