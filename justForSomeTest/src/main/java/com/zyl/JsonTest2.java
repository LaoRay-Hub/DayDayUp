package com.zyl;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.errorprone.annotations.Var;

public class JsonTest2 {
    public static void main(String[] args) {
        String jsonStr= "[]";


        if(!StringUtils.isEmpty(jsonStr)&&jsonStr.startsWith("[")){

            JSONArray objects = JSONArray.parseArray(jsonStr);
            System.out.println(objects);
        }

        Object parse = JSON.parse(jsonStr);

        JSONArray objects = JSONArray.parseArray(jsonStr);


        System.out.println(parse);

        System.out.println(objects);
    }
}
