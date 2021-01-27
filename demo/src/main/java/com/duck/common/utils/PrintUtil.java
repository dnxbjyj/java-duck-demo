package com.duck.common.utils;

/**
 * 打印工具类
 */
public class PrintUtil {
    /**
     * 打印字符串，带可变参数，带换行
     *
     * @param msg  带占位符的字符串
     * @param args 可变参数
     */
    public static void println(String msg, Object... args) {
        msg = msg.replace("{}", "%s");
        System.out.println(String.format(msg, args));
    }
}
