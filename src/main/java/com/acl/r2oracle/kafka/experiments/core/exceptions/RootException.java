package com.acl.r2oracle.kafka.experiments.core.exceptions;

import static com.acl.r2oracle.kafka.experiments.util.message.MessageContextHolder.msg;

/**
 * General system exception to describe custom error behaviors. It's at the root of the microservice's exceptions
 * hierarchy.
 *
 * @author Alejandro
 * @since 0.1.0
 */
public class RootException extends RuntimeException {
    private static final String MESSAGE_CODE = "seed.exceptions.root_exception";
    private static final long serialVersionUID = -1892656883543404076L;

    public RootException() {
        this(msg(MESSAGE_CODE));
    }

    public RootException(String message) {
        this(message, null);
    }

    public RootException(Throwable cause) {
        this(msg(MESSAGE_CODE), cause);
    }

    public RootException(String message, Throwable cause) {
        super(message, cause);
    }
}
