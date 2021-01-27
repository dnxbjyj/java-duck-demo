package com.duck.archive.securitymanager;

import com.duck.common.utils.ClassLoadUtil;
import com.duck.common.utils.PrintUtil;

/**
 * 测试Java安全管理器用法
 * <p>
 * 目标：仅对从外部加载的不可信代码开启安全管理器，对项目内的可信代码不开启安全管理器
 * <p>
 * 启动JVM安全管理器参数：
 * - 只指定policy文件：-Djava.security.policy="d:/tmp/mypolicy.policy"
 * - 既指定policy文件、又开启安全管理器：-Djava.security.manager -Djava.security.policy="d:/tmp/mypolicy.policy"
 */
public class MainTest {
    public static void main(String[] args) throws Exception {
        // - 通过反射获取outer.jar中的DangerousOperation类
        String outerJarPath = "d:/tmp/outer-1.0-SNAPSHOT.jar";
        String dangerousOperationClassName = "com.outer.OuterDangerousOperation";
        final DangerousOperationItfc oper = ClassLoadUtil.getClassInstance(outerJarPath, dangerousOperationClassName);

        // - 通过DangerousOperation类执行危险命令，同时在执行前开启安全管理器、执行后关闭安全管理器，判断安全管理器是否生效
        if (oper != null) {
            final String cmd = "D:/program/Git/usr/bin/touch.exe d:/tmp/test.txt";

            // - 根据JVM参数中配置的policy文件路径，开启安全管理器
            System.setSecurityManager(new SecurityManager());

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

            // - 关闭安全管理器，注意：在policy文件中一定要配置这个权限：
            System.setSecurityManager(null);

            // - 关闭安全管理器之后，再执行另外一个不同的命令，看能否执行成功（经验证，可以）
            final String cmd2 = "rm -f d:/tmp/test.txt";
            new Thread(new Runnable() {
                public void run() {
                    try {
                        oper.executeDangerCmd(cmd2);
                    } catch (Exception e) {
                        PrintUtil.println("executeDangerCmd failed! msg: {}, stack: {}", e.getMessage(), e.getStackTrace());
                    }
                }
            }).start();
        }
    }
}
