package com.zyl;

public class SubStringTest {

    public static void main(String[] args) {

        String jobCron="00 00 09 ? * * *";
        System.out.println(jobCron.substring(6,8));
        String format = String.format("%02d", 22);
        System.out.println(format);

        System.out.println(60%60);

    }
}
