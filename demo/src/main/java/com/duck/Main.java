package com.duck;

import com.duck.archive.securitymanager.DangerousOperationItfc;
import com.duck.common.utils.ClassLoadUtil;
import com.duck.common.utils.PrintUtil;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * 测试Java安全管理器基本用法
 * 启动JVM安全管理器参数：
 */
public class Main {
    public static void main(String[] args) throws Exception {
        // - 通过反射获取outer.jar中的DangerousOperation类
//        URLClassLoader loader = new URLClassLoader(new URL[]{new File("d:/tmp/outer-1.0-SNAPSHOT.jar").toURI().toURL()});
//        final DangerousOperationItfc oper = (DangerousOperationItfc) Class.forName("com.outer.OuterDangerousOperation", true, loader).newInstance();

        String outerJarPath = "d:/tmp/outer-1.0-SNAPSHOT.jar";
        String dangerousOperationClassName = "com.outer.OuterDangerousOperation";
        final DangerousOperationItfc oper = ClassLoadUtil.getClassInstance(outerJarPath, dangerousOperationClassName);

        // - 通过DangerousOperation类执行危险命令，判断安全管理器是否生效
        if (oper != null) {
            final String cmd = "D:/program/Git/usr/bin/touch.exe d:/tmp/test.txt";

            // - 在线程中执行危险操作
            new Thread(new Runnable() {
                public void run() {
                    try {
                        oper.executeDangerCmd(cmd);
                    } catch (Exception e) {
                        PrintUtil.println("executeDangerCmd failed! msg: {}, stack: {}", e.getMessage(), e.getStackTrace());
                    }
                }
            }).start();

        }
    }
}
