package com.yx.util;

public class BigInteger {
    /**
     * 计算进位
     *
     * @param bit 数组
     * @param pos 用于判断是否是数组的最高位
     */
    private void carry(int[] bit, int pos) {
        int i, carry = 0;
        for (i = 0; i <= pos; i++)//从0到pos逐位检查是否需要进位
        {
            bit[i] += carry;//累加进位
            if (bit[i] <= 9)     //小于9不进位
            {
                carry = 0;
            } else if (bit[i] > 9 && i < pos)//大于9，但不是最高位
            {
                carry = bit[i] / 10;//保存进位值
                bit[i] = bit[i] % 10;//得到该位的一位数
            } else if (bit[i] > 9 && i >= pos)//大于9，且是最高位
            {
                while (bit[i] > 9)//循环向前进位
                {
                    carry = bit[i] / 10;//计算进位值
                    bit[i] = bit[i] % 10;//当前的第一位数
                    i++;
                    bit[i] = carry;//在下一位保存进位值
                }
            }
        }
    }

    /**
     * 大整数阶乘
     *
     * @param bigInteger 所计算的大整数
     */
    private void bigFactorial(int bigInteger) {
        int pos = 0;//
        int digit;//数据长度
        int a, b;
        int m = 0;//统计输出位数
        int n = 0;//统计输出行数
        double sum = 0;//阶乘位数
        for (a = 1; a <= bigInteger; a++)//计算阶乘位数
        {
            sum += Math.log10(a);
        }
        digit = (int) sum + 1;//数据长度

        int[] fact = new int[digit];//初始化一个数组
        fact[0] = 1;//设个位为 1

        for (a = 2; a <= bigInteger; a++)//将2^bigInteger逐个与原来的积相乘
        {
            for (b = digit - 1; b >= 0; b--)//查找最高位{}
            {
                if (fact[b] != 0) {
                    pos = b;//记录最高位
                    break;
                }
            }

            for (b = 0; b <= pos; b++) {
                fact[b] *= a;//每一位与i乘
            }
            carry(fact, pos);
        }

        for (b = digit - 1; b >= 0; b--) {
            if (fact[b] != 0) {
                pos = b;//记录最高位
                break;
            }
        }
        System.out.println(bigInteger + "阶乘结果为：");
        for (a = pos; a >= 0; a--)//输出计算结果
        {
            System.out.print(fact[a]);
            m++;
            if (m % 5 == 0) {
                System.out.print(" ");
            }
            if (40 == m) {
                System.out.println("");
                m = 0;
                n++;
                if (10 == n) {
                    System.out.print("\n");
                    n = 0;
                }
            }
        }
        System.out.println("\n" + "阶乘共有: " + (pos + 1) + " 位");

    }

    public void doBigFactorial(int bigInteger) {
        long start = System.currentTimeMillis();
        this.bigFactorial(bigInteger);
        System.out.println("计算耗时: " + (System.currentTimeMillis() - start) + "毫秒");
    }

    /**
     * 格式化钱币字符串
     *
     * @param num
     * @return
     */
    public static String formatStr(Long num) {
        String str = String.valueOf(num);
        String result;
        int len = str.length();
        if (len == 1) {
            result = "0.0" + str;
        } else if (len == 2) {
            result = "0." + str;
        } else {
            StringBuffer sb = new StringBuffer(str).reverse();
            StringBuffer tmp = new StringBuffer();
            for (int i = 0; i < len; i++) {
                tmp.append(sb.charAt(i));
                if (i == 1) {
                    tmp.append(".");
                } else if ((i - 1) % 3 == 0 && i < len - 1) {
                    tmp.append(",");
                }
            }
            result = tmp.reverse().toString();
        }
        return result + " 元";
    }

    /**
     * 大整数相加
     *
     * @param n
     * @param m
     * @return
     */
    public static String add(String n, String m) {
        if (n.length() > m.length()) {
            String tmp = n;
            n = m;
            m = tmp;
        }
        n = new StringBuffer(n).reverse().toString();
        m = new StringBuffer(m).reverse().toString();
        StringBuffer c = new StringBuffer();
        int tmp = 0;
        int sum;
        int i = 0;
        while (i < n.length()) {
            sum = Integer.parseInt(n.substring(i, i + 1)) + Integer.parseInt(m.substring(i, i + 1));
            c.append((sum + tmp) % 10);
            tmp = (sum + tmp) / 10;
            i++;
        }
        while (i < m.length()) {
            if (tmp > 0) {
                c.append(((Integer.parseInt(m.substring(i, i + 1)) + tmp) % 10));
                tmp = (Integer.parseInt(m.substring(i, i + 1)) + tmp) / 10;
            } else {
                c.append(m.substring(i));
                break;
            }
            i++;
        }
        if (tmp != 0) {
            c.append(tmp);
        }
        return c.reverse().toString();
    }

    public static void main(String[] args) {
       /* BigInteger bi = new BigInteger();
        bi.doBigFactorial(5);*/

        System.out.println(add("1233", "6543"));
    }
}