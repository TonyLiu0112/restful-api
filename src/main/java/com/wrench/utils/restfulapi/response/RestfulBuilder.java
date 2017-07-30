package com.wrench.utils.restfulapi.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class RestfulBuilder {

    /**
     * 服务器成功返回用户请求的数据，该操作是幂等的
     *
     * @param data
     * @return
     */
    public static ResponseEntity<RestfulResponse> ok(Object data) {
        return new RestfulResponse(data).send(HttpStatus.OK);
    }

    /**
     * 服务器成功返回用户请求的数据，该操作是幂等的
     *
     * @return
     */
    public static ResponseEntity<RestfulResponse> ok() {
        return RestfulBuilder.ok(null);
    }

    /**
     * 新建或修改数据成功
     *
     * @param data
     * @return
     */
    public static ResponseEntity<RestfulResponse> created(Object data) {
        return new RestfulResponse(data).send(HttpStatus.CREATED);
    }

    /**
     * 新建或修改数据成功
     *
     * @return
     */
    public static ResponseEntity<RestfulResponse> created() {
        return RestfulBuilder.created(null);
    }

    /**
     * 表示一个请求已经进入后台排队（异步任务）
     *
     * @param data
     * @return
     */
    public static ResponseEntity<RestfulResponse> accepted(Object data) {
        return new RestfulResponse(data).send(HttpStatus.ACCEPTED);
    }

    /**
     * 表示一个请求已经进入后台排队（异步任务）
     *
     * @return
     */
    public static ResponseEntity<RestfulResponse> accepted() {
        return RestfulBuilder.accepted(null);
    }

    /**
     * 用户删除数据成功
     *
     * @param data
     * @return
     */
    public static ResponseEntity<RestfulResponse> deleted(Object data) {
        return new RestfulResponse(data).send(HttpStatus.NO_CONTENT);
    }

    /**
     * 用户删除数据成功
     *
     * @return
     */
    public static ResponseEntity<RestfulResponse> deleted() {
        return RestfulBuilder.deleted(null);
    }

    /**
     * 错误的请求
     *
     * @param data
     * @return
     */
    public static ResponseEntity<RestfulResponse> badRequest(Object data) {
        return new RestfulResponse(data).send(HttpStatus.BAD_REQUEST);
    }

    /**
     * 错误的请求
     *
     * @return
     */
    public static ResponseEntity<RestfulResponse> badRequest() {
        return RestfulBuilder.badRequest(null);
    }

    /**
     * 用户访问未授权
     *
     * @param data
     * @return
     */
    public static ResponseEntity<RestfulResponse> unauthorized(Object data) {
        return new RestfulResponse(data).send(HttpStatus.UNAUTHORIZED);
    }

    /**
     * 用户访问未授权
     *
     * @return
     */
    public static ResponseEntity<RestfulResponse> unauthorized() {
        return RestfulBuilder.unauthorized(null);
    }

    /**
     * 用户访问获得授权，但无访问权限
     *
     * @param data
     * @return
     */
    public static ResponseEntity<RestfulResponse> forbidden(Object data) {
        return new RestfulResponse(data).send(HttpStatus.FORBIDDEN);
    }

    /**
     * 用户访问获得授权，但无访问权限
     *
     * @return
     */
    public static ResponseEntity<RestfulResponse> forbidden() {
        return RestfulBuilder.forbidden(null);
    }

    /**
     * 资源未找到
     *
     * @param data
     * @return
     */
    public static ResponseEntity<RestfulResponse> notFound(Object data) {
        return new RestfulResponse(data).send(HttpStatus.NOT_FOUND);
    }

    /**
     * 资源未找到
     *
     * @return
     */
    public static ResponseEntity<RestfulResponse> notFound() {
        return RestfulBuilder.notFound(null);
    }

    /**
     * POST PUT PATCH 请求对象参数验证错误
     *
     * @param data
     * @return
     */
    public static ResponseEntity<RestfulResponse> unprocesableEntity(Object data) {
        return new RestfulResponse(data).send(HttpStatus.UNPROCESSABLE_ENTITY);
    }

    /**
     * POST PUT PATCH 请求对象参数验证错误
     *
     * @return
     */
    public static ResponseEntity<RestfulResponse> unprocesableEntity() {
        return RestfulBuilder.unprocesableEntity(null);
    }

    /**
     * 请求被锁定 不可用
     *
     * @param data
     * @return
     */
    public static ResponseEntity<RestfulResponse> locked(Object data) {
        return new RestfulResponse(data).send(HttpStatus.LOCKED);
    }

    /**
     * 请求被锁定 不可用
     * @return
     */
    public static ResponseEntity<RestfulResponse> locked() {
        return RestfulBuilder.locked(null);
    }

    /**
     * 内部服务错误，请和管理员联系
     *
     * @param data
     * @return
     */
    public static ResponseEntity<RestfulResponse> serverError(Object data) {
        return new RestfulResponse(data).send(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 内部服务错误，请和管理员联系
     *
     * @return
     */
    public static ResponseEntity<RestfulResponse> serverError() {
        return RestfulBuilder.serverError(null);
    }

}
