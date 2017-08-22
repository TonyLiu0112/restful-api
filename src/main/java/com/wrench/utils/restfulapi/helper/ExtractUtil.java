package com.wrench.utils.restfulapi.helper;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wrench.utils.restfulapi.response.RestfulResponse;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExtractUtil {

    public static <T> T extractData(ResponseEntity<RestfulResponse> responseEntity, Class<T> targetClazz) {
        return JSON.parseObject(JSON.toJSONString(responseEntity.getBody().getData()), targetClazz);
    }

    public static <T> List<T> extractList(ResponseEntity<RestfulResponse> responseEntity, Class<T> targetClazz) {
        return JSON.parseArray(JSON.toJSONString(responseEntity.getBody().getData()), targetClazz);
    }

}
