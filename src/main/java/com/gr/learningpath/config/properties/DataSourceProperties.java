package com.gr.learningpath.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

@Getter
@Setter
public class DataSourceProperties {
    private String driverClassName;
    private String protocol;
    private String host;
    private String port;
    private String database;
    private String parameters;
    private String username;
    private String password;
    private Integer maximumPoolSize;

    public String getUrl() {
        return protocol + "//" + host + ":" + port + "/" + database
                + (StringUtils.isNotEmpty(parameters) ? ("?" + parameters) : "");
    }
}
