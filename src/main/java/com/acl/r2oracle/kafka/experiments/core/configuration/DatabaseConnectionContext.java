package com.acl.r2oracle.kafka.experiments.core.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import static java.lang.String.format;

@Configuration
@ConfigurationProperties(prefix = "app.oracle.connection")
public class DatabaseConnectionContext {
    private long timeout;
    private String user;
    private String password;
    private String host;
    private int port;
    private String instance;

    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getInstance() {
        return instance;
    }

    public void setInstance(String instance) {
        this.instance = instance;
    }

    public String getDatabaseUrl() {
        return format("r2dbc:oracle://%s:%d/%s", this.getHost(), this.getPort(), this.getInstance());
    }
}
