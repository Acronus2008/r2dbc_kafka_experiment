package com.acl.r2oracle.kafka.experiments.core.exceptions;

import com.acl.r2oracle.kafka.experiments.core.facade.ErrorCode;

public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final String errorCode;
    private final String errorMsg;

    public BusinessException(ErrorCode code) {
        this.errorMsg = code.getMsg();
        this.errorCode = code.getCode();
    }

    public BusinessException(String code, String message) {
        this.errorMsg = message;
        this.errorCode = code;
    }

    public BusinessException(ErrorCode code, String v) {
        this.errorMsg = String.format(code.getMsg(), v);
        this.errorCode = code.getCode();
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }
}
