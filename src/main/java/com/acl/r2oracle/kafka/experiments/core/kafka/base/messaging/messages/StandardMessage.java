package com.acl.r2oracle.kafka.experiments.core.kafka.base.messaging.messages;

import com.acl.r2oracle.kafka.experiments.util.json.JsonSerdes;

import java.util.HashMap;
import java.util.Map;

import static com.acl.r2oracle.kafka.experiments.util.json.JsonSerdes.jsonfy;
import static java.util.Objects.requireNonNull;

/**
 * Standard kafka message.
 *
 * @since 0.1.0
 */
public class StandardMessage {
    protected Object content;
    private Map<String, Object> headers;

    public StandardMessage() {
    }

    public StandardMessage(Map<String, Object> headers) {
        this.headers = headers;
    }

    //<editor-fold desc="Initializers">
    static public StandardMessage fromHeaders(Map<String, Object> headers) {
        requireNonNull(headers, "Headers can not be null");
        return new StandardMessage(headers);
    }

    /**
     * Checks whether the given header is present within the headers of this message.
     *
     * @param headerName The name of the header to check.
     * @return <b>True</b> if and only if the header exists with an associated value within the headers of this message,
     * <b>false</b> otherwise.
     */
    public boolean hasHeader(String headerName) {
        return this.getHeaders().containsKey(headerName);
    }

    public Object getHeader(String headerName) {
        requireNonNull(headerName, "Header name can not be null");
        return this.getHeaders().get(headerName);
    }

    public Object getHeaderOrDefault(String headerName, Object defaultValue) {
        requireNonNull(headerName, "Header name can not be null");
        return this.getHeaders().getOrDefault(headerName, defaultValue);
    }

    public void putHeader(String headerName, Object value) {
        requireNonNull(headerName, "Header name can not be null");
        if (null != value) {
            this.getHeaders().put(headerName, value);
            return;
        }
        if (this.hasHeader(headerName)) {
            this.getHeaders().remove(headerName);
        }
    }

    @Override
    public String toString() {
        return JsonSerdes.jsonfy(this);
    }
    //</editor-fold>

    //<editor-fold desc="Fluent Encapsulation">
    public StandardMessage withHeaders(Map<String, Object> headers) {
        this.setHeaders(headers);
        return this;
    }

    public StandardMessage withHeader(String headerName, Object value) {
        this.putHeader(headerName, value);
        return this;
    }

    public StandardMessage withContent(Object content) {
        this.setContent(content);
        return this;
    }
    //</editor-fold>

    //<editor-fold desc="Encapsulation">
    public Map<String, Object> getHeaders() {
        return null == this.headers ? this.headers = new HashMap<>() : this.headers;
    }

    public void setHeaders(Map<String, Object> headers) {
        this.headers = headers;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }
    //</editor-fold>
}
