package com.zyl.jvm;

/**栈溢出测试
 * -XX:ThreadStackSize=1m
 */
public class JvmDemo6 {
    public static long count=0;

    public static void main(String[] args) {
        work();
    }
    public  static void work(){
        System.out.println("调用方法次数："+(++count));
        work();
    }

}
