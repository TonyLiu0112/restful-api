package com.wrench.utils.restfulapi;

import com.wrench.utils.restfulapi.response.RestResponse;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface ExceptionMatcher {

    Optional<ResponseEntity<RestResponse>> matchException(Exception e);

}
