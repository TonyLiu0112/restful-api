package com.wrench.utils.restfulapi;

import com.wrench.utils.restfulapi.response.RestResponse;
import org.springframework.core.ParameterizedTypeReference;

/**
 * Restful操作基础模块
 *
 * @author Tony
 */
public interface RestOps {

    <T> RestResponse<T> get(String uri, ParameterizedTypeReference<RestResponse<T>> responseType) throws Exception;

    RestResponse post(String uri, Object requestEntity) throws Exception;

    <T> RestResponse<T> post(String uri, Object requestEntity, ParameterizedTypeReference<RestResponse<T>> responseType) throws Exception;

    RestResponse put(String uri, Object requestEntity) throws Exception;

    <T> RestResponse<T> put(String uri, Object requestEntity, ParameterizedTypeReference<RestResponse<T>> responseType) throws Exception;

    RestResponse delete(String uri, Object requestEntity) throws Exception;

}
