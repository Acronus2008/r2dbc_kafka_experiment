package com.acl.r2oracle.kafka.experiments.core.facade.controllers;

import com.acl.r2oracle.kafka.experiments.core.facade.ErrorCode;
import com.acl.r2oracle.kafka.experiments.core.Error;
import com.acl.r2oracle.kafka.experiments.core.facade.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.Function;

import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;
import static org.springframework.web.util.UriComponentsBuilder.fromUri;

public class Controller<F> {
    private static final ResponseEntity NOT_CONTENT = noContent().build();
    private static final ResponseEntity NOT_FOUND = notFound().build();
    private static final ResponseEntity INTERNAL_ERROR = status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    private static final ResponseEntity FORBIDDEN = status(HttpStatus.FORBIDDEN).build();
    protected @Autowired
    F facade;

    protected <V> Mono<ResponseEntity> create(Function<F, Mono<Result<V>>> function, ServerWebExchange exchange) {
        return function.apply(this.facade)
                .map(result -> result.isSuccess() ? created(fromUri(exchange.getRequest().getURI()).path("/{id}").build(result.getVal())).build() : this.getErrorResponse(result.getErrors()));
    }

    protected <V> Mono<ResponseEntity> perform(Function<F, Mono<Result<V>>> function) {
        return function.apply(facade).map(result -> result.isSuccess() ? NOT_CONTENT : this.getErrorResponse(result.getErrors()));
    }

    protected <V> Mono<ResponseEntity> get(Function<F, Mono<Result<V>>> function) {
        return function.apply(facade).map(result -> result.isSuccess() ? ok(result.getVal()) : this.getErrorResponse(result.getErrors()));
    }

    //<editor-fold desc="Support methods">
    protected ResponseEntity getErrorResponse(List<Error> errors) {
        ErrorCode code = errors.stream()
                .map(Error::getCode)
                .reduce(ErrorCode.INTERNAL_ERROR, this::fold);
        switch (code) {
            case FORBIDDEN_ACCESS:
                return FORBIDDEN;
            case INVALID_REFERENCE:
                return NOT_FOUND;
            default:
                return code.is1xxValidation() ? badRequest().body(errors) : INTERNAL_ERROR;
        }
    }

    private ErrorCode fold(ErrorCode left, ErrorCode right) {
        if (ErrorCode.FORBIDDEN_ACCESS == left || ErrorCode.FORBIDDEN_ACCESS == right) {
            return ErrorCode.FORBIDDEN_ACCESS == left ? left : right;
        }
        if (ErrorCode.INVALID_REFERENCE == left || ErrorCode.INVALID_REFERENCE == right) {
            return ErrorCode.INVALID_REFERENCE == left ? left : right;
        }
        if (left.is1xxValidation() || right.is1xxValidation()) {
            return left.is1xxValidation() ? left : right;
        }
        return left;
    }
    //</editor-fold>
}
