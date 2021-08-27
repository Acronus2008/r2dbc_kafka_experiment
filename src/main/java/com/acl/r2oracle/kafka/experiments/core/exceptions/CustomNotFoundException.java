package com.acl.r2oracle.kafka.experiments.core.exceptions;

import static com.acl.r2oracle.kafka.experiments.core.facade.ErrorCode.INVALID_REFERENCE;

public class CustomNotFoundException extends BusinessException {

    public CustomNotFoundException() {
        super(INVALID_REFERENCE, "Element not found");
    }

    public CustomNotFoundException(Throwable e) {
        super(INVALID_REFERENCE, e.getMessage());
    }
}
