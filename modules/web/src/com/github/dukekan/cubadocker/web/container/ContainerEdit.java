package com.github.dukekan.cubadocker.web.container;

import com.github.dukekan.cubadocker.entity.Container;
import com.github.dukekan.cubadocker.service.ContainerService;
import com.github.dukekan.cubadocker.service.DockerService;
import com.haulmont.cuba.gui.components.AbstractEditor;
import com.haulmont.cuba.gui.components.OptionsList;
import com.haulmont.cuba.gui.components.ResizableTextArea;
import com.haulmont.cuba.gui.components.Timer;

import javax.inject.Inject;

public class ContainerEdit extends AbstractEditor<Container> {
    @Inject
    protected OptionsList names;
    @Inject
    protected ResizableTextArea logsTextArea;
    @Inject
    protected ContainerService containerService;
    @Inject
    protected DockerService dockerService;

    @Override
    protected void postInit() {
        initNamesOptionList();
        initLogsTextArea();
    }

    protected void initLogsTextArea() {
        if (getItem().getConnectionParams() != null && getItem().getContainerId() != null) {
            logsTextArea.setValue(containerService.getContainerLogs(getItem()));
        }
    }

    protected void initNamesOptionList() {
        names.setNullOptionVisible(false);
        names.setOptionsList(getItem().getNamesList());
    }


    public void refreshContainerLogs(Timer source) {
        initLogsTextArea();
    }
}