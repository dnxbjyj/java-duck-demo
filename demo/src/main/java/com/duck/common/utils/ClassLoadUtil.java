package com.duck.common.utils;

import com.duck.archive.securitymanager.DangerousOperationItfc;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * 类加载工具类
 */
public class ClassLoadUtil {
    /**
     * 加载类的实例对象
     *
     * @param jarPath   jar包文件路径
     * @param className 类全限定名
     * @param clazz     类的class
     * @return 类的实例对象
     */
    public static <T> T getClassInstance(String jarPath, String className) {
        try {
            URLClassLoader loader = new URLClassLoader(new URL[]{new File(jarPath).toURI().toURL()});
            T instance = (T) Class.forName(className, true, loader).newInstance();
            return instance;
        } catch (Exception e) {
            PrintUtil.println("getClassInstance failed! msg: {}, stack: {}", e.getMessage(), e.getStackTrace());
            return null;
        }
    }
}
