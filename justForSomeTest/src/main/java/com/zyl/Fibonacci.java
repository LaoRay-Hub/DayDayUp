package com.zyl;

import java.math.BigDecimal;
import java.util.Arrays;

/**
 * 测试斐波那契数列，相邻两束相除是否越来越接近0.618
 */
public class Fibonacci {
    public static void main(String[] args) {
//        BigDecimal[] fib = fib(1000000);
        BigDecimal[] fib = fib2(1000);


//        System.out.println(Arrays.toString(fib));

//        for (int i = 0; i < fib.length-1; i++) {
//            BigDecimal i1 = fib[i].divide(fib[i + 1],1000,BigDecimal.ROUND_HALF_UP);
//            System.out.println(i1);
//        }
//        BigDecimal i1 = fib[fib.length-2].divide(fib[fib.length-1],1000,BigDecimal.ROUND_HALF_UP);
        BigDecimal i1 = fib[0].divide(fib[1],1000,BigDecimal.ROUND_HALF_UP);
        System.out.println(i1);

    }

    /**
     * 生成斐波那契数列
     * @param n
     * @return
     */
    public static BigDecimal [] fib(int n){
        BigDecimal [] f=new BigDecimal[n];

        for (int i = 0; i < f.length; i++) {
            if(i==0){
                f[0]=new BigDecimal(1);
            }else if(i==1){
                f[1]=new BigDecimal(1);
            }else {
                f[i]=f[i-1].add(f[i-2]);
            }

        }

        return f;
    }

    /**
     * 只保留斐波那契数列后两位
     * @param n
     * @return
     */
    public static BigDecimal [] fib2(int n){
        BigDecimal a=new BigDecimal(1);
        BigDecimal b=new BigDecimal(1);
        BigDecimal c;

        BigDecimal [] f1=new BigDecimal[2];
        for (int i = 0; i < n; i++) {
            c=a.add(b);
            a=b;
            b=c;
        }
        f1[0]=a;
        f1[1]=b;
        return f1;
    }


}
