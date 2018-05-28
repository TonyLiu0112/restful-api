package com.wrench.utils.restfulapi;

import com.github.pagehelper.PageInfo;
import com.wrench.utils.restfulapi.response.AdditionPayload;
import com.wrench.utils.restfulapi.response.EmptyResponse;
import com.wrench.utils.restfulapi.response.Page;
import com.wrench.utils.restfulapi.response.RestResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.wrench.utils.restfulapi.helper.DefaultErrorMsg.DEFAULT_SERVER_ERROR;
import static com.wrench.utils.restfulapi.helper.DefaultErrorMsg.DEFAULT_SERVER_NOT_IMPLEMENT;
import static org.springframework.http.HttpStatus.*;

public class RestBuilder {

    public static <R> ResponseEntity<R> ok4Fallback() {
        return new ResponseEntity<>(OK);
    }

    /**
     * 服务器成功返回用户请求的数据，该操作是幂等的
     *
     * @return
     */
    public static ResponseEntity<RestResponse> ok() {
        return ok(new EmptyResponse());
    }

    /**
     * 服务器成功返回用户请求的数据，该操作是幂等的
     *
     * @param data
     * @return
     */
    public static ResponseEntity<RestResponse> ok(Object data) {
        return ok(data, null);
    }

    /**
     * 服务器成功返回用户请求的数据，该操作是幂等的
     *
     * @param data
     * @return
     */
    public static ResponseEntity<RestResponse> ok(Object data, String message) {
        if (data instanceof AdditionPayload)
            return ok(new EmptyResponse(), (AdditionPayload) data, message);
        return ok(data, null, message);
    }

    public static ResponseEntity<RestResponse> ok(Object data, AdditionPayload errRes, String message) {
        return emitter(data, errRes, message, OK);
    }

    public static ResponseEntity<RestResponse> ok4Page() {
        return ok4Page(new PageInfo<>());
    }

    public static ResponseEntity<RestResponse> ok4Page(PageInfo<?> pageInfo) {
        RestResponse restResponse = new RestResponse();
        Page page = new Page();
        BeanUtils.copyProperties(pageInfo, page);
        if (pageInfo.getList() != null && pageInfo.getList().size() > 0)
            restResponse.setData(pageInfo.getList());
        else
            restResponse.setData(new ArrayList());
        restResponse.setPage(page);
        return restResponse.send(OK);
    }

    public static ResponseEntity<RestResponse> ok4List() {
        return ok4List(Collections.emptyList());
    }

    public static ResponseEntity<RestResponse> ok4List(List list) {
        RestResponse restResponse = new RestResponse();
        restResponse.setData(list);
        return restResponse.send(OK);
    }

    public static <R> ResponseEntity<R> created4Fallback() {
        return new ResponseEntity<>(CREATED);
    }

    /**
     * 新建或修改数据成功
     *
     * @return
     */
    public static ResponseEntity<RestResponse> created() {
        return created(null);
    }

    /**
     * 新建或修改数据成功
     *
     * @param data
     * @return
     */
    public static ResponseEntity<RestResponse> created(Object data) {
        return created(data, null);
    }

    public static ResponseEntity<RestResponse> created(Object data, String message) {
        if (data instanceof AdditionPayload)
            return created(null, (AdditionPayload) data, message);
        return created(data, null, message);
    }

    public static ResponseEntity<RestResponse> created(Object data, AdditionPayload errRes, String message) {
        return emitter(data, errRes, message, CREATED);
    }

    public static <R> ResponseEntity<R> accepted4Fallback() {
        return new ResponseEntity<>(ACCEPTED);
    }

    /**
     * 表示一个请求已经进入后台排队（异步任务）
     *
     * @return
     */
    public static ResponseEntity<RestResponse> accepted() {
        return accepted(null);
    }

    public static ResponseEntity<RestResponse> accepted(Object data) {
        return accepted(data, null);
    }

    /**
     * 表示一个请求已经进入后台排队（异步任务）
     *
     * @param data
     * @return
     */
    public static ResponseEntity<RestResponse> accepted(Object data, String message) {
        return emitter(data, null, message, ACCEPTED);
    }

    public static <R> ResponseEntity<R> noContent4Fallback() {
        return new ResponseEntity<>(NO_CONTENT);
    }

    /**
     * 用户删除数据成功
     *
     * @return
     */
    public static ResponseEntity<RestResponse> noContent() {
        return noContent(null);
    }

    /**
     * 用户删除数据成功
     *
     * @param data
     * @return
     */
    public static ResponseEntity<RestResponse> noContent(Object data) {
        return noContent(data, null);
    }

    /**
     * 用户删除数据成功
     *
     * @param data
     * @return
     */
    public static ResponseEntity<RestResponse> noContent(Object data, String message) {
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
    public static ResponseEntity<RestResponse> badRequest() {
        return badRequest(null);
    }

    /**
     * 错误的请求
     *
     * @param data
     * @return
     */
    public static ResponseEntity<RestResponse> badRequest(Object data) {
        return badRequest(data, null);
    }

    /**
     * 错误的请求
     *
     * @param data
     * @return
     */
    public static ResponseEntity<RestResponse> badRequest(Object data, String message) {
        if (data instanceof AdditionPayload)
            return badRequest(null, (AdditionPayload) data, message);
        return badRequest(data, null, message);
    }


    public static ResponseEntity<RestResponse> badRequest(Object data, AdditionPayload errRes, String message) {
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
    public static ResponseEntity<RestResponse> unauthorized() {
        return unauthorized(null);
    }

    public static ResponseEntity<RestResponse> unauthorized(Object data) {
        return unauthorized(data, null);
    }

    public static ResponseEntity<RestResponse> unauthorized(Object data, String message) {
        if (data instanceof AdditionPayload)
            return unauthorized(null, (AdditionPayload) data, message);
        return unauthorized(data, null, message);
    }

    /**
     * 用户访问未授权
     *
     * @param data
     * @return
     */
    public static ResponseEntity<RestResponse> unauthorized(Object data, AdditionPayload errRes, String message) {
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
    public static ResponseEntity<RestResponse> forbidden() {
        return forbidden(null);
    }

    /**
     * 用户访问获得授权，但无访问权限
     *
     * @param data
     * @return
     */
    public static ResponseEntity<RestResponse> forbidden(Object data) {
        return forbidden(data, null);
    }

    /**
     * 用户访问获得授权，但无访问权限
     *
     * @param data
     * @return
     */
    public static ResponseEntity<RestResponse> forbidden(Object data, String message) {
        if (data instanceof AdditionPayload)
            return forbidden(null, (AdditionPayload) data, message);
        return forbidden(data, null, message);
    }

    /**
     * 用户访问获得授权，但无访问权限
     *
     * @param data
     * @return
     */
    public static ResponseEntity<RestResponse> forbidden(Object data, AdditionPayload errRes, String message) {
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
    public static ResponseEntity<RestResponse> notFound() {
        return notFound(null);
    }

    /**
     * 资源未找到
     *
     * @param data
     * @return
     */
    public static ResponseEntity<RestResponse> notFound(Object data) {
        return notFound(data, null);
    }

    /**
     * 资源未找到
     *
     * @param data
     * @return
     */
    public static ResponseEntity<RestResponse> notFound(Object data, String message) {
        if (data instanceof AdditionPayload)
            return notFound(null, (AdditionPayload) data, message);
        return notFound(data, null, message);
    }

    /**
     * 资源未找到
     *
     * @param data
     * @return
     */
    public static ResponseEntity<RestResponse> notFound(Object data, AdditionPayload errRes, String message) {
        return emitter(data, errRes, message, NOT_FOUND);
    }

    public static <R> ResponseEntity<R> methodNotAllowed4Fallback() {
        return new ResponseEntity<>(METHOD_NOT_ALLOWED);
    }

    /**
     * 资源未找到
     *
     * @return
     */
    public static ResponseEntity<RestResponse> methodNotAllowed() {
        return methodNotAllowed(null);
    }

    /**
     * 资源未找到
     *
     * @param data
     * @return
     */
    public static ResponseEntity<RestResponse> methodNotAllowed(Object data) {
        return methodNotAllowed(data, null);
    }

    /**
     * 资源未找到
     *
     * @param data
     * @return
     */
    public static ResponseEntity<RestResponse> methodNotAllowed(Object data, String message) {
        if (data instanceof AdditionPayload)
            return methodNotAllowed(null, (AdditionPayload) data, message);
        return methodNotAllowed(data, null, message);
    }

    /**
     * 资源未找到
     *
     * @param data
     * @return
     */
    public static ResponseEntity<RestResponse> methodNotAllowed(Object data, AdditionPayload errRes, String message) {
        return emitter(data, errRes, message, METHOD_NOT_ALLOWED);
    }

    public static <R> ResponseEntity<R> unprocesableEntity4Fallback() {
        return new ResponseEntity<R>(UNPROCESSABLE_ENTITY);
    }

    /**
     * POST PUT PATCH 请求对象参数验证错误
     *
     * @return
     */
    public static ResponseEntity<RestResponse> unprocesableEntity() {
        return unprocesableEntity(null);
    }

    /**
     * POST PUT PATCH 请求对象参数验证错误
     *
     * @param data
     * @return
     */
    public static ResponseEntity<RestResponse> unprocesableEntity(Object data) {
        return unprocesableEntity(data, null);
    }

    /**
     * POST PUT PATCH 请求对象参数验证错误
     *
     * @param data
     * @return
     */
    public static ResponseEntity<RestResponse> unprocesableEntity(Object data, String message) {
        if (data instanceof AdditionPayload)
            return unprocesableEntity(null, (AdditionPayload) data, message);
        return unprocesableEntity(data, null, message);
    }

    /**
     * POST PUT PATCH 请求对象参数验证错误
     *
     * @param data
     * @return
     */
    public static ResponseEntity<RestResponse> unprocesableEntity(Object data, AdditionPayload errRes, String message) {
        return emitter(data, errRes, message, UNPROCESSABLE_ENTITY);
    }


    public static <R> ResponseEntity<R> locked4Fallback() {
        return new ResponseEntity<>(HttpStatus.LOCKED);
    }

    public static ResponseEntity<RestResponse> locked() {
        return locked(null);
    }

    /**
     * 请求被锁定 不可用
     *
     * @param data
     * @return
     */
    public static ResponseEntity<RestResponse> locked(Object data) {
        return locked(data, null);
    }

    /**
     * 请求被锁定 不可用
     *
     * @param data
     * @return
     */
    public static ResponseEntity<RestResponse> locked(Object data, String message) {
        if (data instanceof AdditionPayload)
            return locked(null, (AdditionPayload) data, message);
        return locked(data, null, message);
    }

    /**
     * 请求被锁定 不可用
     *
     * @param data
     * @return
     */
    public static ResponseEntity<RestResponse> locked(Object data, AdditionPayload errRes, String message) {
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
    public static ResponseEntity<RestResponse> serverError() {
        return serverError(new AdditionPayload(DEFAULT_SERVER_ERROR));
    }

    /**
     * 内部服务错误
     *
     * @param data
     * @return
     */
    public static ResponseEntity<RestResponse> serverError(Object data) {
        return serverError(data, null);
    }

    /**
     * 内部服务错误
     *
     * @param data
     * @return
     */
    public static ResponseEntity<RestResponse> serverError(Object data, String message) {
        if (data instanceof AdditionPayload)
            return serverError(null, (AdditionPayload) data, message);
        return serverError(data, null, message);
    }

    /**
     * 内部服务错误
     *
     * @param data
     * @return
     */
    public static ResponseEntity<RestResponse> serverError(Object data, AdditionPayload errRes, String message) {
        return emitter(data, errRes, message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 服务未实现
     *
     * @return
     */
    public static ResponseEntity<RestResponse> notImplemented() {
        return notImplemented(new AdditionPayload(DEFAULT_SERVER_NOT_IMPLEMENT));
    }

    /**
     * 服务未实现
     *
     * @param data
     * @return
     */
    public static ResponseEntity<RestResponse> notImplemented(Object data) {
        return notImplemented(data, null);
    }

    /**
     * 服务未实现
     *
     * @param data
     * @return
     */
    public static ResponseEntity<RestResponse> notImplemented(Object data, String message) {
        if (data instanceof AdditionPayload)
            return notImplemented(null, (AdditionPayload) data, message);
        return notImplemented(data, null, message);
    }

    /**
     * 服务未实现
     *
     * @param data
     * @return
     */
    public static ResponseEntity<RestResponse> notImplemented(Object data, AdditionPayload errRes, String message) {
        return emitter(data, errRes, message, HttpStatus.NOT_IMPLEMENTED);
    }

    /**
     * 自定义错误匹配
     *
     * @param exceptionMatcher 异常匹配器
     * @param e                异常信息
     * @return 响应信息
     */
    public static ResponseEntity<RestResponse> errorResponse(ExceptionMatcher exceptionMatcher, Exception e) {
        if (exceptionMatcher != null) {
            Optional<ResponseEntity<RestResponse>> matched = exceptionMatcher.matchException(e);
            if (matched.isPresent())
                return matched.get();
        }
        return serverError();
    }

    public static ResponseEntity<RestResponse> errorResponse(Exception e) {
        return errorResponse(null, e);
    }


    @SuppressWarnings("unchecked")
    private static ResponseEntity<RestResponse> emitter(Object data, AdditionPayload errRes, String message, HttpStatus httpStatus) {
        return new RestResponse(data, errRes, message).send(httpStatus);
    }

}
