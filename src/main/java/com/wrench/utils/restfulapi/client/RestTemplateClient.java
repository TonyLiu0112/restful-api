package com.wrench.utils.restfulapi.client;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wrench.utils.restfulapi.response.RestResponse;
import jodd.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.http.*;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

/**
 * {@link RestTemplate} 包装类
 * <p>
 * 当前版本已废弃, 请使用{@link RestTemplateWrapper}
 *
 * @author Tony
 */
@Deprecated
public class RestTemplateClient {

    private final RestTemplate restTemplate;
    private final HttpOps ops;

    public RestTemplateClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        ops = new HttpOps();
    }

    // get

    public RestResponse get(String uri) throws Exception {
        return ops.get(uri, null, null);
    }

    public <T> RestResponse<T> get(String uri, Class<T> dataClazz) throws Exception {
        return ops.get(uri, dataClazz, new Parser<T, T>() {
            @Override
            public T parser(String dataText, Class<T> clazz) {
                return JSONObject.parseObject(dataText, clazz);
            }
        });
    }

    public <T> RestResponse<List<T>> getList(String uri, Class<T> dataClazz) throws Exception {
        return ops.get(uri, dataClazz, new Parser<T, List<T>>() {
            @Override
            public List<T> parser(String dataText, Class<T> clazz) {
                return JSONObject.parseArray(dataText, clazz);
            }
        });
    }

    // post

    public RestResponse post(String uri, Object req) throws Exception {
        return ops.post(uri, req, null, null);
    }

    public <T> RestResponse<T> post(String uri, Object req, Class<T> dataClazz) throws Exception {
        return ops.post(uri, req, dataClazz, new Parser<T, T>() {
            @Override
            public T parser(String dataText, Class<T> clazz) {
                return JSONObject.parseObject(dataText, clazz);
            }
        });
    }

    public <T> RestResponse<List<T>> postList(String uri, Object req, Class<T> dataClazz) throws Exception {
        return ops.post(uri, req, dataClazz, new Parser<T, List<T>>() {
            @Override
            public List<T> parser(String dataText, Class<T> clazz) {
                return JSONObject.parseArray(dataText, clazz);
            }
        });
    }

    // delete

    public RestResponse delete(String uri) throws Exception {
        return ops.delete(uri, null);
    }

    public RestResponse delete(String uri, Object req) throws Exception {
        return ops.delete(uri, req);
    }

    // put

    public RestResponse put(String uri, Object req) throws Exception {
        return ops.put(uri, req, null, null);
    }

    public <T> RestResponse<T> put(String uri, Object req, Class<T> dataClazz) throws Exception {
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

        <T, R> RestResponse<R> get(String uri, Class<T> dataClazz, Parser<T, R> parser) throws Exception {
            RestResponse<R> restResponse = new RestResponse<>();
            try {
                log.debug("Start requesting(GET) remote resources: {}", uri);
                ResponseEntity<RestResponse> responseEntity = restTemplate.getForEntity(uri, RestResponse.class);
                log.debug("Http response: {}", JSON.toJSONString(responseEntity, true));
                restResponse = responseHandler(responseEntity, dataClazz, parser);
            } catch (HttpStatusCodeException e) {
                log.error("Server 4xx/5xx response. <{}>", e.getResponseBodyAsString());
                failedHandler(restResponse, e.getResponseBodyAsString());
            } catch (Exception e) {
                log.error("Process request error.", e);
                restResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
                restResponse.setMessage(e.getMessage());
            }
            return restResponse;
        }

        <T, R> RestResponse<R> post(String uri, Object req, Class<T> dataClazz, Parser<T, R> parser) throws Exception {
            RestResponse<R> restResponse = new RestResponse<>();
            try {
                log.debug("Start requesting(POST) remote resources: {}", uri);
                ResponseEntity<RestResponse> responseEntity = restTemplate.postForEntity(new URI(uri), req, RestResponse.class);
                log.debug("Http response: {}", JSON.toJSONString(responseEntity, true));
                restResponse = responseHandler(responseEntity, dataClazz, parser);
            } catch (HttpStatusCodeException e) {
                log.error("Server 4xx/5xx response. <{}>", e.getResponseBodyAsString());
                failedHandler(restResponse, e.getResponseBodyAsString());
            } catch (Exception e) {
                log.error("Process request error.", e);
                restResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
                restResponse.setMessage(e.getMessage());
            }
            return restResponse;
        }

        RestResponse<Object> delete(String uri, Object req) throws Exception {
            RestResponse<Object> restResponse = new RestResponse<>();
            try {
                log.debug("Start requesting(DELETE) remote resources: {}", uri);
                HttpEntity httpEntity = new RequestEntity<>(req, HttpMethod.DELETE, new URI(uri));
                ResponseEntity<RestResponse> responseEntity = restTemplate.exchange(uri, HttpMethod.DELETE, httpEntity, RestResponse.class);
                log.debug("Http response: {}", JSON.toJSONString(responseEntity, true));
                restResponse.setStatus(responseEntity.getStatusCode());
                restResponse.setMessage(responseEntity.getStatusCode().getReasonPhrase());
            } catch (HttpStatusCodeException e) {
                log.error("Server 4xx/5xx response. <{}>", e.getResponseBodyAsString());
                failedHandler(restResponse, e.getResponseBodyAsString());
            } catch (Exception e) {
                log.error("Process request error.", e);
                restResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
                restResponse.setMessage(e.getMessage());
            }
            return restResponse;
        }

        <T, R> RestResponse<R> put(String uri, Object req, Class<T> dataClazz, Parser<T, R> parser) throws Exception {
            RestResponse<R> restResponse = new RestResponse<>();
            try {
                HttpEntity httpEntity = new RequestEntity<>(req, HttpMethod.PUT, new URI(uri));
                ResponseEntity<RestResponse> responseEntity = restTemplate.exchange(uri, HttpMethod.PUT, httpEntity, RestResponse.class);
                log.debug("Http response: {}", JSON.toJSONString(responseEntity, true));
                restResponse = responseHandler(responseEntity, dataClazz, parser);
            } catch (HttpStatusCodeException e) {
                log.error("Server 4xx/5xx response. <{}>", e.getResponseBodyAsString());
                failedHandler(restResponse, e.getResponseBodyAsString());
            } catch (Exception e) {
                log.error("Process request error.", e);
                restResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
                restResponse.setMessage(e.getMessage());
            }
            return restResponse;
        }

        private <T, R> RestResponse<R> responseHandler(ResponseEntity<RestResponse> responseEntity, Class<T> responseClazz, Parser<T, R> parser) {
            RestResponse<R> restResponse = new RestResponse<>();
            RestResponse body = responseEntity.getBody();
            restResponse.setStatus(body.getStatus());
            restResponse.setMessage(body.getMessage());
            if (responseClazz != null) {
                String dataText = JSONObject.toJSONString(responseEntity.getBody().getData());
                if (StringUtil.isNotBlank(dataText) && parser != null)
                    restResponse.setData(parser.parser(dataText, responseClazz));
            }
            return restResponse;
        }

        private void failedHandler(RestResponse restResponse, String errorJson) {
            RestResponse errRes = JSONObject.parseObject(errorJson, RestResponse.class);
            BeanUtils.copyProperties(errRes, restResponse);
        }

    }

}
