package com.wrench.utils.restfulapi;

import com.alibaba.fastjson.JSONObject;
import com.wrench.utils.restfulapi.response.RestfulResponse;
import jodd.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

public class RestTemplateWrapper {

    private Logger logger = LoggerFactory.getLogger(RestTemplateWrapper.class);

    public final RestTemplate restTemplate;

    public RestTemplateWrapper(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public RestfulResponse get(String uri) throws Exception {
        return get(uri, null);
    }

    public RestfulResponse get(String uri, Class responseClazz) throws Exception {
        RestfulResponse restResponse = new RestfulResponse();
        try {
            ResponseEntity<RestfulResponse> responseEntity = restTemplate.getForEntity(uri, RestfulResponse.class);
            restResponse = responseEntity.getBody();
            if (responseClazz != null) {
                String dataText = JSONObject.toJSONString(responseEntity.getBody().getData());
                if (StringUtil.isNotBlank(dataText))
                    restResponse.setData(JSONObject.parseObject(dataText, responseClazz));
            }
        } catch (HttpStatusCodeException e) {
            logger.error("", e);
            restResponse.setStatus(e.getStatusCode());
            restResponse.setError(e.getMessage());
        } catch (Exception e) {
            logger.error("", e);
            restResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            restResponse.setError(e.getMessage());
        }
        return restResponse;
    }

    public RestfulResponse getList(String uri, Class responseClazz) throws Exception {
        RestfulResponse restResponse = new RestfulResponse();
        try {
            ResponseEntity<RestfulResponse> responseEntity = restTemplate.getForEntity(uri, RestfulResponse.class);
            restResponse = responseEntity.getBody();
            if (responseClazz != null) {
                String dataText = JSONObject.toJSONString(responseEntity.getBody().getData());
                if (StringUtil.isNotBlank(dataText))
                    restResponse.setData(JSONObject.parseArray(dataText, responseClazz));
            }
        } catch (HttpStatusCodeException e) {
            logger.error("", e);
            restResponse.setStatus(e.getStatusCode());
            restResponse.setError(e.getMessage());
        } catch (Exception e) {
            logger.error("", e);
            restResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            restResponse.setError(e.getMessage());
        }
        return restResponse;
    }

    public RestfulResponse post(String uri, Object req) throws Exception {
        return this.post(uri, req, null);
    }

    public RestfulResponse post(String uri, Object req, Class responseClazz) throws Exception {
        RestfulResponse restResponse = new RestfulResponse();
        try {
            ResponseEntity<RestfulResponse> responseEntity = restTemplate.postForEntity(new URI(uri), req, RestfulResponse.class);
            restResponse = responseEntity.getBody();
            if (responseClazz != null) {
                String dataText = JSONObject.toJSONString(responseEntity.getBody().getData());
                if (StringUtil.isNotBlank(dataText))
                    restResponse.setData(JSONObject.parseObject(dataText, responseClazz));
            }
        } catch (HttpStatusCodeException e) {
            logger.error("", e);
            restResponse.setStatus(e.getStatusCode());
            restResponse.setError(e.getMessage());
        } catch (Exception e) {
            logger.error("", e);
            restResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            restResponse.setError(e.getMessage());
        }
        return restResponse;
    }

    public RestfulResponse postList(String uri, Object req, Class responseClazz) throws Exception {
        RestfulResponse restResponse = new RestfulResponse();
        try {
            ResponseEntity<RestfulResponse> responseEntity = restTemplate.postForEntity(new URI(uri), req, RestfulResponse.class);
            restResponse = responseEntity.getBody();
            if (responseClazz != null) {
                String dataText = JSONObject.toJSONString(responseEntity.getBody().getData());
                if (StringUtil.isNotBlank(dataText))
                    restResponse.setData(JSONObject.parseArray(dataText, responseClazz));
            }
        } catch (HttpStatusCodeException e) {
            logger.error("", e);
            restResponse.setStatus(e.getStatusCode());
            restResponse.setError(e.getMessage());
        } catch (Exception e) {
            logger.error("", e);
            restResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            restResponse.setError(e.getMessage());
        }
        return restResponse;
    }

    public RestfulResponse delete(String uri) throws Exception {
        RestfulResponse restResponse = new RestfulResponse();
        try {
            ResponseEntity<RestfulResponse> responseEntity = restTemplate.execute(uri, HttpMethod.DELETE, null, null);
            restResponse = responseEntity.getBody();
        } catch (HttpStatusCodeException e) {
            logger.error("", e);
            restResponse.setStatus(e.getStatusCode());
            restResponse.setError(e.getMessage());
        } catch (Exception e) {
            logger.error("", e);
            restResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            restResponse.setError(e.getMessage());
        }
        return restResponse;
    }

    public RestfulResponse delete(String uri, Object reqBody) throws Exception {
        RestfulResponse restResponse = new RestfulResponse();
        try {
            HttpEntity httpEntity = new RequestEntity<>(reqBody, HttpMethod.DELETE, new URI(uri));
            ResponseEntity<RestfulResponse> responseEntity = restTemplate.exchange(uri, HttpMethod.DELETE, httpEntity, RestfulResponse.class);
            restResponse = responseEntity.getBody();
        } catch (HttpStatusCodeException e) {
            e.printStackTrace();
            restResponse.setStatus(e.getStatusCode());
            restResponse.setError(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            restResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            restResponse.setError(e.getMessage());
        }
        return restResponse;
    }

    public RestfulResponse put(String uri, Object requestBody) {
        RestfulResponse restResponse = new RestfulResponse();
        try {
            HttpEntity httpEntity = new RequestEntity<>(requestBody, HttpMethod.PUT, new URI(uri));
            ResponseEntity<RestfulResponse> responseEntity = restTemplate.exchange(uri, HttpMethod.PUT, httpEntity, RestfulResponse.class);
            restResponse = responseEntity.getBody();
        } catch (HttpStatusCodeException e) {
            e.printStackTrace();
            restResponse.setStatus(e.getStatusCode());
            restResponse.setError(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            restResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            restResponse.setError(e.getMessage());
        }
        return restResponse;
    }

}
