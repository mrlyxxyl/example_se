package com.yx.test.common;

import java.util.Arrays;
import java.util.Random;

/**
 * 各种排序算法,都是按升序排的
 *
 * @author YSH
 * @Title: SortingAlgorithm.java
 * @Package com.ysh
 * @date 2014年2月24日 下午1:34:32
 */
public class SortingAlgorithm {
    /**
     * 随机产生一个长度为length的整形数组
     *
     * @param length
     * @return
     */
    public int[] getArray(int length) {
        Random random = new Random();
        int[] array = new int[length];
        for (int i = 0; i < length; i++) {
            array[i] = random.nextInt(length);
        }
        return array;
    }

    /**
     * 直接插入排序
     *
     * @param array
     */
    public void DirectInsertSort(int[] array) {
        // 从1开始,就是把小的值往前面放
        for (int i = 1; i < array.length; i++) {
            // 拿到索引为i的值,依次前面的所有的值比较
            for (int j = i; j > 0; j--) {
                // 当后面的值小于前面的值的时候,2个数交换
                if (array[j] < array[j - 1]) {
                    // 交换2个数的位置
                    int temp = array[j];
                    array[j] = array[j - 1];
                    array[j - 1] = temp;
                }
            }
        }
    }

    /**
     * 冒泡排序
     *
     * @param array
     */
    public void MaoPaoSort(int[] array) {
        for (int i = 0; i < array.length; i++) {
            // 索引i的值依次与后面所有的值比较,找出最小的值
            for (int j = i + 1; j < array.length; j++) {
                if (array[i] > array[j]) {
                    // 交换2个数的位置
                    int temp = array[i];
                    array[i] = array[j];
                    array[j] = temp;
                }
            }
        }
    }

    /**
     * 选择排序
     *
     * @param array
     */
    public void ChooseSort(int[] array) {
        for (int i = 0; i < array.length; i++) {
            // 保存最小值的索引
            int minIndex = i;
            for (int j = i + 1; j < array.length; j++) {
                if (array[j] < array[minIndex]) {
                    // 找到最小值的索引
                    minIndex = j;
                }
            }
            // 把最小值放到前面去
            int temp = array[i];
            array[i] = array[minIndex];
            array[minIndex] = temp;
        }
    }

    /**
     * 快速排序 X为假定的中间值 从j开始向左搜索,即由右开始向左搜索j--,找到第一个小于X的值,两者交换
     * 从i开始向右搜索,即由左开始向右搜索i++,找到第一个大于X的值,两者交换
     *
     * @param array
     * @param left
     * @param right
     */
    public void QuickSort(int[] array, int left, int right) {
        int i = left;
        int j = right;
        // 用来标记是i++还是j--
        boolean flag = true;
        while (i != j) {
            // 初始假定数组的第一个值为中间值
            if (array[i] > array[j]) {
                // 交换2个值
                int temp = array[i];
                array[i] = array[j];
                array[j] = temp;
                // 交换一次值后,改变搜索方向
                // j：找到第一个小于中间值的索引位置
                // i：找到第一个大于中间值的索引位置
                // 交换值后,中间值的索引位置也跟着改变(假定的中间值在一次循环排序中不会改变,索引位置会变)
                flag = (flag == true) ? false : true;
            }
            if (flag == true) {
                j--;
            } else {
                i++;
            }
        }
        // 一次排序后,中间值左边的都比中间值小,右边的都比中间值大
        // 递归排序中间值两边的数组
        if (left < i - 1) {
            QuickSort(array, left, i - 1);
        }
        if (i + 1 < right) {
            QuickSort(array, i + 1, right);
        }
    }

    /**
     * 希尔排序
     *
     * @param array
     */
    public void XiErSort(int[] array) {
        // 循环增量(就是每个子数组的个数,当子数组个数为1时,排序就完成)
        for (int increment = array.length / 2; increment > 0; increment = increment / 2) {
            // 对increment个子数组进行排序
            for (int i = 0; i < increment; i++) {
                // 对子数组进行直接插入排序,子数组也可以进行冒泡排序
                for (int j = i; j < array.length; j = j + increment) {
                    // 拿到索引为j的值,依次前面的所有的值比较
                    for (int k = j; k > 0; k = k - increment) {
                        // 当后面的值小于前面的值的时候,2个数交换
                        if (array[k] < array[k - 1]) {
                            // 交换2个数的位置
                            int temp = array[k];
                            array[k] = array[k - 1];
                            array[k - 1] = temp;
                        }
                    }
                }
            }
        }
    }

    public static void main(String[] args) {

        SortingAlgorithm sortingAlgorithm = new SortingAlgorithm();
        int[] array = sortingAlgorithm.getArray(1000000);

        int[] a = Arrays.copyOf(array, array.length);
        long start = System.currentTimeMillis();
        sortingAlgorithm.XiErSort(a);
        long end = System.currentTimeMillis();
        System.out.println("希尔排序消耗时间 = " + (end - start) + "毫秒");

        int[] b = Arrays.copyOf(array, array.length);
        start = System.currentTimeMillis();
        sortingAlgorithm.QuickSort(b, 0, b.length - 1);
        end = System.currentTimeMillis();
        System.out.println("快速排序消耗时间 = " + (end - start) + "毫秒");

        int[] c = Arrays.copyOf(array, array.length);
        start = System.currentTimeMillis();
        sortingAlgorithm.ChooseSort(c);
        end = System.currentTimeMillis();
        System.out.println("选择排序消耗时间 = " + (end - start) + "毫秒");

        int[] d = Arrays.copyOf(array, array.length);
        start = System.currentTimeMillis();
        sortingAlgorithm.DirectInsertSort(d);
        end = System.currentTimeMillis();
        System.out.println("插入排序消耗时间 = " + (end - start) + "毫秒");

        int[] e = Arrays.copyOf(array, array.length);
        start = System.currentTimeMillis();
        sortingAlgorithm.MaoPaoSort(e);
        end = System.currentTimeMillis();
        System.out.println("冒泡排序消耗时间 = " + (end - start) + "毫秒");
    }
}
