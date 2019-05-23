package com.github.dukekan.cubadocker.entity;

import com.haulmont.chile.core.annotations.MetaClass;
import com.haulmont.chile.core.annotations.MetaProperty;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.BaseUuidEntity;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;

import javax.persistence.ElementCollection;
import java.util.Arrays;
import java.util.List;

@NamePattern("%s|containerId")
@MetaClass(name = "cubadocker$Container")
public class Container extends BaseUuidEntity {
    private static final long serialVersionUID = 8184088161249351300L;

    @MetaProperty
    protected String containerId;

    @MetaProperty
    protected String state;

    @MetaProperty
    protected String image;

    @MetaProperty
    protected String imageId;

    @MetaProperty
    protected String status;

    @ElementCollection
    protected String[] names;

    @MetaProperty
    @Lookup(type = LookupType.DROPDOWN, actions = {"open"})
    protected ConnectionParams connectionParams;

    public void setState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }


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

    public String[] getNames() {
        return names;
    }

    public void setNames(String[] names) {
        this.names = names;
    }

    public List<String> getNamesList(){
        return Arrays.asList(names);
    }

    @MetaProperty
    public String getNamesString() {
        return String.join(",", names);
    }

    public ConnectionParams getConnectionParams() {
        return connectionParams;
    }

    public void setConnectionParams(ConnectionParams connectionParams) {
        this.connectionParams = connectionParams;
    }
}