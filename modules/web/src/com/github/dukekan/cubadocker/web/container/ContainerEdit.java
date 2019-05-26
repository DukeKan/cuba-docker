package com.github.dukekan.cubadocker.web.container;

import com.github.dukekan.cubadocker.entity.Container;
import com.github.dukekan.cubadocker.service.ContainerService;
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
    private ResizableTextArea containerInfoTextArea;
    @Inject
    private ResizableTextArea topTextArea;
    @Inject
    private ResizableTextArea statsTextArea;
    @Inject
    protected ContainerService containerService;

    @Override
    protected void postInit() {
        initNamesOptionList();
        initLogsTextArea();
        initContainerInfoTextArea();
    }

    protected void initStatsTextArea() {
        if (getItem().getConnectionParams() != null && getItem().getContainerId() != null) {
            statsTextArea.setValue(containerService.getContainerStats(getItem()));
        }
    }

    protected void initContainerTopProcessesTextArea() {
        if (getItem().getConnectionParams() != null && getItem().getContainerId() != null) {
            topTextArea.setValue(containerService.topContainerProcesses(getItem()));
        }
    }

    protected void initContainerInfoTextArea() {
        if (getItem().getConnectionParams() != null && getItem().getContainerId() != null) {
            containerInfoTextArea.setValue(containerService.inspectContainer(getItem()));
        }
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

    public void refreshTopProcesses() {
        initContainerTopProcessesTextArea();
    }

    public void refreshStatistics() {
        initStatsTextArea();
    }
}