package com.xwq.demo;

import java.io.File;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xuwenqing
 * Date: 2021-01-26
 * Time: 22:27:57
 */
public class FileToMeta {
    public static void main(String[] args) {
        File file = new File("F:\\本地搜索工具准备\\mysearcher\\mysearcher\\mysearcher.db");
        System.out.println(file.getName());
        System.out.println(file.getAbsoluteFile());
        System.out.println(file.isDirectory());
        System.out.println(file.length());
        System.out.println(file.lastModified());

        Date date = new Date(file.lastModified());
        System.out.println(date);
    }
}
