package com.yx.test.thread;

/**
 * @author zhengbinMac
 */
public class ClassLoaderTest {

    public static void main(String[] args) {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        System.out.println("current loader:" + loader);
        System.out.println("parent loader:" + loader.getParent());
        System.out.println("grandparent loader:" + loader.getParent().getParent());
    }
    /*
     * output:
     *    current loader:sun.misc.Launcher$AppClassLoader@1b6d3586
     *    parent loader:sun.misc.Launcher$ExtClassLoader@1540e19d 
     *    grandparent loader:null // 因为根类装载器在Java中访问不到，所以返回null
     */
}