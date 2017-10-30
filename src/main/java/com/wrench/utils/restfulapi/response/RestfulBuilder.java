package com.wrench.utils.restfulapi.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.springframework.http.HttpStatus.*;

public class RestfulBuilder {

    public static <R> ResponseEntity<R> ok4Fallback() {
        return new ResponseEntity<>(OK);
    }

    /**
     * 服务器成功返回用户请求的数据，该操作是幂等的
     *
     * @return
     */
    public static ResponseEntity<RestfulResponse> ok() {
        return ok(null);
    }

    /**
     * 服务器成功返回用户请求的数据，该操作是幂等的
     *
     * @param data
     * @return
     */
    public static ResponseEntity<RestfulResponse> ok(Object data) {
        return ok(data, null);
    }

    /**
     * 服务器成功返回用户请求的数据，该操作是幂等的
     *
     * @param data
     * @return
     */
    public static ResponseEntity<RestfulResponse> ok(Object data, String message) {
        return emitter(data, null, message, OK);
    }

    public static <R> ResponseEntity<R> created4Fallback() {
        return new ResponseEntity<>(CREATED);
    }

    /**
     * 新建或修改数据成功
     *
     * @return
     */
    public static ResponseEntity<RestfulResponse> created() {
        return created(null);
    }

    /**
     * 新建或修改数据成功
     *
     * @param data
     * @return
     */
    public static ResponseEntity<RestfulResponse> created(Object data) {
        return created(data, null);
    }

    public static ResponseEntity<RestfulResponse> created(Object data, String message) {
        return emitter(data, null, message, CREATED);
    }

    public static <R> ResponseEntity<R> accepted4Fallback() {
        return new ResponseEntity<>(ACCEPTED);
    }

    /**
     * 表示一个请求已经进入后台排队（异步任务）
     *
     * @return
     */
    public static ResponseEntity<RestfulResponse> accepted() {
        return accepted(null);
    }

    public static ResponseEntity<RestfulResponse> accepted(Object data) {
        return accepted(data, null);
    }

    /**
     * 表示一个请求已经进入后台排队（异步任务）
     *
     * @param data
     * @return
     */
    public static ResponseEntity<RestfulResponse> accepted(Object data, String message) {
        return emitter(data, null, message, ACCEPTED);
    }

    public static <R> ResponseEntity<R> deleted4Fallback() {
        return new ResponseEntity<>(NO_CONTENT);
    }

    /**
     * 用户删除数据成功
     *
     * @return
     */
    public static ResponseEntity<RestfulResponse> deleted() {
        return deleted(null);
    }

    /**
     * 用户删除数据成功
     *
     * @param data
     * @return
     */
    public static ResponseEntity<RestfulResponse> deleted(Object data) {
        return deleted(data, null);
    }

    /**
     * 用户删除数据成功
     *
     * @param data
     * @return
     */
    public static ResponseEntity<RestfulResponse> deleted(Object data, String message) {
        return emitter(data, null, message, NO_CONTENT);
    }

    public static <R> ResponseEntity<R> badRequest4Fallback() {
        return new ResponseEntity<>(BAD_REQUEST);
    }

    /**
     * 错误的请求
     *
     * @return
     */
    public static ResponseEntity<RestfulResponse> badRequest() {
        return badRequest(null);
    }

    /**
     * 错误的请求
     *
     * @param data
     * @return
     */
    public static ResponseEntity<RestfulResponse> badRequest(Object data) {
        return badRequest(data, null);
    }

    /**
     * 错误的请求
     *
     * @param data
     * @return
     */
    public static ResponseEntity<RestfulResponse> badRequest(Object data, String message) {
        if (data != null && data instanceof ErrorResponse)
            return badRequest(null, (ErrorResponse) data, message);
        return badRequest(data, null, message);
    }


    public static ResponseEntity<RestfulResponse> badRequest(Object data, ErrorResponse errRes, String message) {
        return emitter(data, errRes, message, BAD_REQUEST);
    }

    public static <R> ResponseEntity<R> unauthorized4Fallback() {
        return new ResponseEntity<>(UNAUTHORIZED);
    }

    /**
     * 用户访问未授权
     *
     * @return
     */
    public static ResponseEntity<RestfulResponse> unauthorized() {
        return unauthorized(null);
    }

    public static ResponseEntity<RestfulResponse> unauthorized(Object data) {
        return unauthorized(data, null);
    }

    public static ResponseEntity<RestfulResponse> unauthorized(Object data, String message) {
        if (data != null && data instanceof ErrorResponse)
            return unauthorized(null, (ErrorResponse) data, message);
        return unauthorized(data, null, message);
    }

    /**
     * 用户访问未授权
     *
     * @param data
     * @return
     */
    public static ResponseEntity<RestfulResponse> unauthorized(Object data, ErrorResponse errRes, String message) {
        return emitter(data, errRes, message, UNAUTHORIZED);
    }

    public static <R> ResponseEntity<R> forbidden4Fallback() {
        return new ResponseEntity<>(FORBIDDEN);
    }

    /**
     * 用户访问获得授权，但无访问权限
     *
     * @return
     */
    public static ResponseEntity<RestfulResponse> forbidden() {
        return forbidden(null);
    }

    /**
     * 用户访问获得授权，但无访问权限
     *
     * @param data
     * @return
     */
    public static ResponseEntity<RestfulResponse> forbidden(Object data) {
        return forbidden(data, null);
    }

    /**
     * 用户访问获得授权，但无访问权限
     *
     * @param data
     * @return
     */
    public static ResponseEntity<RestfulResponse> forbidden(Object data, String message) {
        if (data != null && data instanceof ErrorResponse)
            return forbidden(null, (ErrorResponse) data, message);
        return forbidden(data, null, message);
    }

    /**
     * 用户访问获得授权，但无访问权限
     *
     * @param data
     * @return
     */
    public static ResponseEntity<RestfulResponse> forbidden(Object data, ErrorResponse errRes, String message) {
        return emitter(data, errRes, message, FORBIDDEN);
    }

    public static <R> ResponseEntity<R> notFound4Fallback() {
        return new ResponseEntity<>(NOT_FOUND);
    }

    /**
     * 资源未找到
     *
     * @return
     */
    public static ResponseEntity<RestfulResponse> notFound() {
        return notFound(null);
    }

    /**
     * 资源未找到
     *
     * @param data
     * @return
     */
    public static ResponseEntity<RestfulResponse> notFound(Object data) {
        return notFound(data, null);
    }

    /**
     * 资源未找到
     *
     * @param data
     * @return
     */
    public static ResponseEntity<RestfulResponse> notFound(Object data, String message) {
        if (data != null && data instanceof ErrorResponse)
            return notFound(null, (ErrorResponse) data, message);
        return notFound(data, null, message);
    }

    /**
     * 资源未找到
     *
     * @param data
     * @return
     */
    public static ResponseEntity<RestfulResponse> notFound(Object data, ErrorResponse errRes, String message) {
        return emitter(data, errRes, message, NOT_FOUND);
    }

    public static <R> ResponseEntity<R> unprocesableEntity4Fallback() {
        return new ResponseEntity<R>(UNPROCESSABLE_ENTITY);
    }

    /**
     * POST PUT PATCH 请求对象参数验证错误
     *
     * @return
     */
    public static ResponseEntity<RestfulResponse> unprocesableEntity() {
        return unprocesableEntity(null);
    }

    /**
     * POST PUT PATCH 请求对象参数验证错误
     *
     * @param data
     * @return
     */
    public static ResponseEntity<RestfulResponse> unprocesableEntity(Object data) {
        return unprocesableEntity(data, null);
    }

    /**
     * POST PUT PATCH 请求对象参数验证错误
     *
     * @param data
     * @return
     */
    public static ResponseEntity<RestfulResponse> unprocesableEntity(Object data, String message) {
        if (data != null && data instanceof ErrorResponse)
            return unprocesableEntity(null, (ErrorResponse) data, message);
        return unprocesableEntity(data, null, message);
    }

    /**
     * POST PUT PATCH 请求对象参数验证错误
     *
     * @param data
     * @return
     */
    public static ResponseEntity<RestfulResponse> unprocesableEntity(Object data, ErrorResponse errRes, String message) {
        return emitter(data, errRes, message, UNPROCESSABLE_ENTITY);
    }


    public static <R> ResponseEntity<R> locked4Fallback() {
        return new ResponseEntity<>(HttpStatus.LOCKED);
    }

    public static ResponseEntity<RestfulResponse> locked() {
        return locked(null);
    }

    /**
     * 请求被锁定 不可用
     *
     * @param data
     * @return
     */
    public static ResponseEntity<RestfulResponse> locked(Object data) {
        return locked(data, null);
    }

    /**
     * 请求被锁定 不可用
     *
     * @param data
     * @return
     */
    public static ResponseEntity<RestfulResponse> locked(Object data, String message) {
        if (data != null && data instanceof ErrorResponse)
            return locked(null, (ErrorResponse) data, message);
        return locked(data, null, message);
    }

    /**
     * 请求被锁定 不可用
     *
     * @param data
     * @return
     */
    public static ResponseEntity<RestfulResponse> locked(Object data, ErrorResponse errRes, String message) {
        return emitter(data, errRes, message, HttpStatus.LOCKED);
    }

    /**
     * 内部错误
     *
     * @param <R>
     * @return
     */
    public static <R> ResponseEntity<R> serverError4Fallback() {
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 内部服务错误
     *
     * @return
     */
    public static ResponseEntity<RestfulResponse> serverError() {
        return serverError(null);
    }

    /**
     * 内部服务错误
     *
     * @param data
     * @return
     */
    public static ResponseEntity<RestfulResponse> serverError(Object data) {
        return serverError(data, null);
    }

    /**
     * 内部服务错误
     *
     * @param data
     * @return
     */
    public static ResponseEntity<RestfulResponse> serverError(Object data, String message) {
        if (data != null && data instanceof ErrorResponse)
            return serverError(null, (ErrorResponse) data, message);
        return serverError(data, null, message);
    }

    /**
     * 内部服务错误
     *
     * @param data
     * @return
     */
    public static ResponseEntity<RestfulResponse> serverError(Object data, ErrorResponse errRes, String message) {
        return emitter(data, errRes, message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 服务未实现
     *
     * @return
     */
    public static ResponseEntity<RestfulResponse> notImplemented() {
        return notImplemented(null);
    }

    /**
     * 服务未实现
     *
     * @param data
     * @return
     */
    public static ResponseEntity<RestfulResponse> notImplemented(Object data) {
        return notImplemented(data, null);
    }

    /**
     * 服务未实现
     *
     * @param data
     * @return
     */
    public static ResponseEntity<RestfulResponse> notImplemented(Object data, String message) {
        if (data != null && data instanceof ErrorResponse)
            return notImplemented(null, (ErrorResponse) data, message);
        return notImplemented(data, null, message);
    }

    /**
     * 服务未实现
     *
     * @param data
     * @return
     */
    public static ResponseEntity<RestfulResponse> notImplemented(Object data, ErrorResponse errRes, String message) {
        return emitter(data, errRes, message, HttpStatus.NOT_IMPLEMENTED);
    }


    private static ResponseEntity<RestfulResponse> emitter(Object data, ErrorResponse errRes, String message, HttpStatus httpStatus) {
        return new RestfulResponse(data, errRes, message).send(httpStatus);
    }

}
