package com.yx.test.annotation;

import com.yx.util.ClassUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

/**
 * User: NMY
 * Date: 16-9-4
 */
public class AnnotationTest {
    public static void main(String[] args) throws IOException {
        List<Class> classes = ClassUtil.getClasses(AnnotationTest.class.getPackage().getName());
        for (Class clazz : classes) {
            if (clazz.isAnnotationPresent(ConfigAnnotation.class)) {
                ConfigAnnotation annotation = (ConfigAnnotation) clazz.getAnnotation(ConfigAnnotation.class);
                String configFilePath = annotation.value();
                Properties properties = new Properties();
                InputStream inputStream = AnnotationTest.class.getClassLoader().getResourceAsStream(configFilePath);
                properties.load(inputStream);
                Enumeration<?> names = properties.propertyNames();
                while (names.hasMoreElements()) {
                    String name = (String) names.nextElement();
                    System.out.println(name + "---------" + properties.get(name));
                }
            }
        }
    }
}
