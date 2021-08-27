package com.zyl;


import com.alibaba.druid.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormat {
    public static void main(String[] args) throws ParseException {

        Date parse = null;
        String dateStr = "2019-11-28T05:00:00.000+08:00";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        String format = null;
        String subType = "DATE";
        if (subType.equals("DATETIME")) {
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            parse = sdf.parse(dateStr);
            format = sdf2.format(parse);
        } else if (subType.equals("DATE")&& !StringUtils.isEmpty(dateStr)) {
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
            parse = sdf.parse(dateStr);
            format = sdf2.format(parse);
        }
        System.out.println(format);


        double a=2.7+4.6+3.5+0.3+0.1+5+2.8+0.4+2+6;
        System.out.println(a);
    }
}
