package com.wrench.utils.restfulapi.helper;

import com.alibaba.fastjson.JSON;
import com.wrench.utils.restfulapi.response.RestResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Deprecated
public class ExtractUtil {

    public static <T> T extractData(ResponseEntity<RestResponse> responseEntity, Class<T> targetClazz) {
        return JSON.parseObject(JSON.toJSONString(responseEntity.getBody().getData()), targetClazz);
    }

    public static <T> List<T> extractList(ResponseEntity<RestResponse> responseEntity, Class<T> targetClazz) {
        return JSON.parseArray(JSON.toJSONString(responseEntity.getBody().getData()), targetClazz);
    }

}
