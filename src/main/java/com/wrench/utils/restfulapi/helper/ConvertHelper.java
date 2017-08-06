package com.wrench.utils.restfulapi.helper;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

public class ConvertHelper {

    public static <T> T convert(Object data, Class<T> targetClazz) {
        return JSONObject.parseObject(JSONObject.toJSONString(data), targetClazz);
    }

    public static <T> List<T> convert2List(Object data, Class<T> targetClazz) {
        return JSONObject.parseArray(JSONObject.toJSONString(data), targetClazz);
    }

}
