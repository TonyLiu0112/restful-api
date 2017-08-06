package com.wrench.utils.restfulapi;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wrench.utils.restfulapi.response.RestfulResponse;
import jodd.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

public class RestTemplateWrapper {

    private Logger logger = LoggerFactory.getLogger(RestTemplateWrapper.class);

    private final RestTemplate restTemplate;
    private final HttpOps ops;

    public RestTemplateWrapper(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        ops = new HttpOps();
    }

    // get

    public RestfulResponse get(String uri) throws Exception {
        return ops.get(uri, null, null);
    }

    public <T> RestfulResponse<T> getOne(String uri, Class<T> dataClazz) throws Exception {
        return ops.get(uri, dataClazz, new Parser<T, T>() {
            @Override
            public T parser(String dataText, Class<T> clazz) {
                return JSONObject.parseObject(dataText, clazz);
            }
        });
    }

    public <T> RestfulResponse<List<T>> get4List(String uri, Class<T> dataClazz) throws Exception {
        return ops.get(uri, dataClazz, new Parser<T, List<T>>() {
            @Override
            public List<T> parser(String dataText, Class<T> clazz) {
                return JSONObject.parseArray(dataText, clazz);
            }
        });
    }

    // post

    public RestfulResponse post(String uri, Object req) throws Exception {
        return ops.post(uri, req, null, null);
    }

    public <T> RestfulResponse<T> post4One(String uri, Object req, Class<T> dataClazz) throws Exception {
        return ops.post(uri, req, dataClazz, new Parser<T, T>() {
            @Override
            public T parser(String dataText, Class<T> clazz) {
                return JSONObject.parseObject(dataText, clazz);
            }
        });
    }

    public <T> RestfulResponse<List<T>> post4List(String uri, Object req, Class<T> dataClazz) throws Exception {
        return ops.post(uri, req, dataClazz, new Parser<T, List<T>>() {
            @Override
            public List<T> parser(String dataText, Class<T> clazz) {
                return JSONObject.parseArray(dataText, clazz);
            }
        });
    }

    // delete

    public RestfulResponse delete(String uri) throws Exception {
        return ops.delete(uri, null);
    }

    public RestfulResponse delete(String uri, Object req) throws Exception {
        return ops.delete(uri, req);
    }

    // put

    public RestfulResponse put(String uri, Object req) throws Exception {
        return ops.put(uri, req, null, null);
    }

    public <T> RestfulResponse<T> put(String uri, Object req, Class<T> dataClazz) throws Exception {
        return ops.put(uri, req, dataClazz, new Parser<T, T>() {
            @Override
            public T parser(String dataText, Class<T> clazz) {
                return JSONObject.parseObject(dataText, clazz);
            }
        });
    }

    private interface Parser<I, O> {
        O parser(String data, Class<I> clazz);
    }

    private class HttpOps {

        private Logger log = LoggerFactory.getLogger(HttpOps.class);

        <T, R> RestfulResponse<R> get(String uri, Class<T> dataClazz, Parser<T, R> parser) throws Exception {
            RestfulResponse<R> restResponse = new RestfulResponse<>();
            try {
                log.debug("Start requesting(GET) remote resources: {}", uri);
                ResponseEntity<RestfulResponse> responseEntity = restTemplate.getForEntity(uri, RestfulResponse.class);
                log.debug("Http response: {}", JSON.toJSONString(responseEntity, true));
                restResponse = responseHandler(responseEntity, dataClazz, parser);
            } catch (HttpStatusCodeException e) {
                log.error("Server response 4xx/5xx response. <{} {}>", e.getStatusCode(), e.getMessage());
                restResponse.setStatus(e.getStatusCode());
                restResponse.setMessage("Success to remote resources.");
            } catch (Exception e) {
                log.error("Process request error.", e);
                restResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
                restResponse.setMessage(e.getMessage());
            }
            return restResponse;
        }

        <T, R> RestfulResponse<R> post(String uri, Object req, Class<T> dataClazz, Parser<T, R> parser) throws Exception {
            RestfulResponse<R> restResponse = new RestfulResponse<>();
            try {
                log.debug("Start requesting(POST) remote resources: {}", uri);
                ResponseEntity<RestfulResponse> responseEntity = restTemplate.postForEntity(new URI(uri), req, RestfulResponse.class);
                log.debug("Http response: {}", JSON.toJSONString(responseEntity, true));
                restResponse = responseHandler(responseEntity, dataClazz, parser);
            } catch (HttpStatusCodeException e) {
                log.error("Server response 4xx/5xx response. <{} {}>", e.getStatusCode(), e.getMessage());
                restResponse.setStatus(e.getStatusCode());
                restResponse.setMessage(e.getMessage());
            } catch (Exception e) {
                log.error("Process request error.", e);
                restResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
                restResponse.setMessage(e.getMessage());
            }
            return restResponse;
        }

        RestfulResponse delete(String uri, Object req) throws Exception {
            RestfulResponse restResponse = new RestfulResponse();
            try {
                log.debug("Start requesting(DELETE) remote resources: {}", uri);
                HttpEntity httpEntity = new RequestEntity<>(req, HttpMethod.DELETE, new URI(uri));
                ResponseEntity<RestfulResponse> responseEntity = restTemplate.exchange(uri, HttpMethod.DELETE, httpEntity, RestfulResponse.class);
                log.debug("Http response: {}", JSON.toJSONString(responseEntity, true));
                restResponse.setStatus(responseEntity.getStatusCode());
                restResponse.setMessage(responseEntity.getStatusCode().getReasonPhrase());
            } catch (HttpStatusCodeException e) {
                log.error("Server response 4xx/5xx response. <{} {}>", e.getStatusCode(), e.getMessage());
                restResponse.setStatus(e.getStatusCode());
                restResponse.setMessage(e.getMessage());
            } catch (Exception e) {
                log.error("Process request error.", e);
                restResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
                restResponse.setMessage(e.getMessage());
            }
            return restResponse;
        }

        <T, R> RestfulResponse<R> put(String uri, Object req, Class<T> dataClazz, Parser<T, R> parser) throws Exception {
            RestfulResponse<R> restResponse = new RestfulResponse<>();
            try {
                HttpEntity httpEntity = new RequestEntity<>(req, HttpMethod.PUT, new URI(uri));
                ResponseEntity<RestfulResponse> responseEntity = restTemplate.exchange(uri, HttpMethod.PUT, httpEntity, RestfulResponse.class);
                log.debug("Http response: {}", JSON.toJSONString(responseEntity, true));
                restResponse = responseHandler(responseEntity, dataClazz, parser);
            } catch (HttpStatusCodeException e) {
                log.error("Server response 4xx/5xx response. <{} {}>", e.getStatusCode(), e.getMessage());
                restResponse.setStatus(e.getStatusCode());
                restResponse.setMessage(e.getMessage());
            } catch (Exception e) {
                log.error("Process request error.", e);
                restResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
                restResponse.setMessage(e.getMessage());
            }
            return restResponse;
        }

        private <T, R> RestfulResponse<R> responseHandler(ResponseEntity<RestfulResponse> responseEntity, Class<T> responseClazz, Parser<T, R> parser) {
            RestfulResponse<R> restResponse = new RestfulResponse<>();
            RestfulResponse body = responseEntity.getBody();
            restResponse.setStatus(body.getStatus());
            restResponse.setMessage(body.getMessage());
            if (responseClazz != null) {
                String dataText = JSONObject.toJSONString(responseEntity.getBody().getData());
                if (StringUtil.isNotBlank(dataText) && parser != null)
                    restResponse.setData(parser.parser(dataText, responseClazz));
            }
            return restResponse;
        }

    }

}
