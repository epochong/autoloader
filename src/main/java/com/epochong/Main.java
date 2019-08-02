package com.epochong;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;

/**
 * @author epochong
 * @date 2019/7/12 21:12
 * @email epochong@163.com
 * @blog epochong.github.io
 * @describe
 */
public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        /*
         * 目标：找到com.epochong.cases下的所有类
         * 转换：能找到存放com.epochong.cases这个包的目录
         *       扫描这个目录下所有*.class
         */

        /*
         * 如何找到这个目录
         * 类都是从类加载器来的
         */
        ClassLoader classLoader = Main.class.getClassLoader();
        Enumeration <URL> resources = classLoader.getResources("com/epochong/cases");
        //resources：老版迭代器
        while (resources.hasMoreElements()) {
            URL url = resources.nextElement();
            //获取路径
            System.out.println(URLDecoder.decode(url.getPath(),"UTF-8"));
            //只能处理*.class的情况，无法处理打成jar包的情况
            File dir = new File(URLDecoder.decode(url.getPath(),"UTF-8"));
            if (!dir.isDirectory()) {
                continue;
            }
            File[] files = dir.listFiles();
            if (files == null) {
                continue;
            }
            for (File file : files
                 ) {
                String filename = file.getName();
                String classNmae = filename.substring(0,filename.length() - 6);
                //System.out.println(classNmae);
                Class<?> cls = Class.forName("com.epochong.cases." + classNmae);
                //利用Case接口，找出我们需要的class,实现了Case返回true
                Class<?>[] interfaces = cls.getInterfaces();
                for (Class<?> interf : interfaces
                     ) {
                    if (interf == Case.class) {
                        System.out.println(classNmae);
                    }
                }

            }

        }

    }
}
