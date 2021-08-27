package com.zyl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *json去空格换行
 */
public class JsonTest {

    public static void main(String[] args) {
        String jsonStr="[{\"applicant\": \"合肥华凌股份有限公司\"}, {\"applicant\": \"合肥美的电冰箱有限公司\"}, {\"applicant\": \"美的集团股份有限公司\"}]";

        Object jsonObject=   JSON.parse(jsonStr);

//        System.out.println(jsonStr);
//String match="[//s//p{Zs}]";
String match="\\s*|\t|\r|\n";
        Pattern pattern = Pattern.compile(match);
        Matcher re = pattern.matcher(jsonStr);
        jsonStr=re.replaceAll("");

        System.out.println(jsonObject);
    }



}
