package com.acl.r2oracle.kafka.experiments.core.facade;

import com.acl.r2oracle.kafka.experiments.core.Error;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import static java.util.Objects.requireNonNull;

/**
 * Represents the result the <b>facade</b> layer emits from its business operations. Encapsulates a detailed response
 * ready to be served for any distribution mode.
 *
 * @param <R> The type of the val wrapped wothin the result.
 * @author Alejandro
 * @since 0.1.1
 */
public class Result<R> {
    private List<Error> errors;
    private R val;
    private boolean success;

    public Result() {
    }

    public static <O> Result<O> successful() {
        return successful(null);
    }

    public static <O> Result<O> successful(O result) {
        return new Result<O>().success(result);
    }

    public static <O> Result<O> failed(Error error) {
        List<Error> errors = new ArrayList<>();
        errors.add(error);
        return failed(errors);
    }

    public static <O> Result<O> failed(List<Error> errors) {
        return new Result<O>().failure(errors);
    }

    public void ifSuccess(Consumer<R> successCallback) {
        if (success) {
            successCallback.accept(val);
        }
    }

    public void ifSuccess(Consumer<R> successCallback, Consumer<List<Error>> errorCallback) {
        if (success) {
            successCallback.accept(val);
        } else {
            errorCallback.accept(errors);
        }
    }

    public <V> Result<V> map(Function<R, V> mappingFunction) {
        requireNonNull(mappingFunction, "The mapping function must not be null");
        if (!success)
            throw new IllegalStateException("The result isn't success");
        Result<V> result = new Result<>();
        result.val = mappingFunction.apply(this.val);
        return result;
    }

    //<editor-fold desc="Support Methods">
    private Result<R> success(R val) {
        this.val = val;
        this.success = true;
        return this;
    }

    private Result<R> failure(List<Error> errors) {
        this.errors = errors;
        this.success = false;
        return this;
    }
    //</editor-fold>

    //<editor-fold desc="Encapsulation">
    public List<Error> getErrors() {
        return errors;
    }

    public R getVal() {
        return val;
    }

    public boolean isSuccess() {
        return success;
    }
    //</editor-fold>
}
