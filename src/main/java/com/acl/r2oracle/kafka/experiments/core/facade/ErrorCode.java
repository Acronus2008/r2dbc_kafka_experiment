package com.acl.r2oracle.kafka.experiments.core.facade;

import static java.lang.String.format;
import static java.util.Arrays.stream;

public enum ErrorCode {
    // 1xx Validation errors
    INVALID_REFERENCE(104, "INVALID_REFERENCE", "Invalid reference"),

    // 2xx Security errors

    FORBIDDEN_ACCESS(201, "FORBIDDEN_ACCESS", "Forbidden"),
    GENERAL_SECURITY_ERROR(202, "GENERAL_SECURITY_ERROR", "General Security Error"),

    // 3xx Backend errors
    INTERNAL_ERROR(300, "INTERNAL_ERROR", "Internal Error"),
    NOT_IMPLEMENTED_YET(301, "NOT_IMPLEMENTED_YET", "Not Implemented Yet"),
    REDIS_ERROR(302, "REDIS_ERROR", "Redis error");

    private final int index;
    private final String code;
    private final String msg;

    ErrorCode(int index, String code, String msg) {
        this.index = index;
        this.code = code;
        this.msg = msg;
    }

    public int getIndex() {
        return index;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    /**
     * Whether this error code is in the Error series {@link Series#VALIDATION VALIDATION}.
     * This is a shortcut for checking the value of {@link #series()}.
     */
    public boolean is1xxValidation() {
        return Series.VALIDATION.equals(series());
    }

    /**
     * Whether this error code is in the Error series {@link Series#SECURITY SECURITY}.
     * This is a shortcut for checking the value of {@link #series()}.
     */
    public boolean is2xxSecurity() {
        return Series.SECURITY.equals(series());
    }

    /**
     * Whether this error code is in the Error series {@link Series#BACKEND BACKEND}.
     * This is a shortcut for checking the value of {@link #series()}.
     */
    public boolean is3xxBackEnd() {
        return Series.SECURITY.equals(series());
    }

    /**
     * Returns the Error Code series of this error code.
     *
     * @see Series
     */
    public Series series() {
        return Series.valueOf(this);
    }

    /**
     * Enumeration of error codes series.
     * <p>Retrievable via {@link ErrorCode#series()}.
     */
    public enum Series {
        VALIDATION(1),
        SECURITY(2),
        BACKEND(3);

        private final int value;

        Series(int value) {
            this.value = value;
        }

        /**
         * Return the integer value of this status series. Ranges from 1 to 3.
         */
        public int value() {
            return this.value;
        }

        public static Series valueOf(int status) {
            int seriesCode = status / 100;
            return stream(values())
                    .filter(series -> series.value == seriesCode)
                    .findAny()
                    .orElseThrow(() -> new IllegalArgumentException(format("No matching constant for '%s'", status)));
        }

        public static Series valueOf(ErrorCode code) {
            return valueOf(code.index);
        }
    }
}
