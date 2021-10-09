package com.zyl.jvm;

import java.util.ArrayList;
import java.util.List;

/**堆内存溢出测试
 *  -Xms10m -Xmx10m
 */
public class JvmDemo7 {
    public static void main(String[] args) {
        long count=0;
        List<Object> list = new ArrayList<>();
        while (true){
            list.add(new Object());
            System.out.println("创建对象数："+(++count));
        }
    }
}
