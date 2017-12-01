package com.yx.plugin;

import com.yx.util.ClassUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * User: LiWenC
 * Date: 16-10-12
 */
public class Test {
    public static void main(String[] args) throws IllegalAccessException, InstantiationException, InvocationTargetException, InterruptedException {
        List<Class> classes = ClassUtil.getClasses("com.yx.service");
        for (Class clazz : classes) {
            if (clazz.isAnnotationPresent(AutoTest.class)) {
                System.out.println("-------------" + clazz.getName() + "-----------------");
                Object obj = clazz.newInstance();
                Method[] methods = clazz.getDeclaredMethods();
                for (Method method : methods) {
                    Class<?>[] types = method.getParameterTypes();
                    List<Object> params = new ArrayList<Object>();
                    if (types != null && types.length > 0) {//组装参数
                        for (Class c : types) {
                            if (c == int.class || c == double.class || c == float.class || c == long.class) {
                                params.add(0);
                            } else {
                                params.add(c.newInstance());
                            }
                        }
                    }
                    System.out.println(method.getName() + "------return-------" + method.invoke(obj, params.toArray()));
                    Thread.sleep(100);
                }
            }
        }
    }
}
