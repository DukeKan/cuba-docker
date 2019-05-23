package com.github.dukekan.cubadocker.entity;

import com.haulmont.chile.core.annotations.MetaClass;
import com.haulmont.cuba.core.entity.BaseUuidEntity;
import com.haulmont.chile.core.annotations.MetaProperty;
import com.haulmont.chile.core.annotations.NamePattern;

@NamePattern("%s|containerId")
@MetaClass(name = "cubadocker$Container")
public class Container extends BaseUuidEntity {
    private static final long serialVersionUID = 8184088161249351300L;

    @MetaProperty
    protected String containerId;

    @MetaProperty
    protected String imageId;

    @MetaProperty
    protected String status;

    public void setContainerId(String containerId) {
        this.containerId = containerId;
    }

    public String getContainerId() {
        return containerId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getImageId() {
        return imageId;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }


}