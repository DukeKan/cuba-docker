package com.github.dukekan.cubadocker.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;

@NamePattern("%s|name")
@Table(name = "CUBADOCKER_CONNECTION_PARAMS")
@Entity(name = "cubadocker$ConnectionParams")
public class ConnectionParams extends StandardEntity {
    private static final long serialVersionUID = 7440827138277026916L;

    @Column(name = "NAME")
    protected String name;

    @Column(name = "HOST", length = 500)
    protected String host;

    @Column(name = "PORT")
    protected Integer port;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getHost() {
        return host;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Integer getPort() {
        return port;
    }


}