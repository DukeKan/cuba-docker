package com.github.dukekan.cubadocker.web.container;

import com.github.dukekan.cubadocker.web.container.actions.StartContainerAction;
import com.haulmont.cuba.gui.components.AbstractLookup;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.components.Table;

import javax.inject.Inject;

public class ContainerBrowse extends AbstractLookup {

    @Inject
    private Table<com.github.dukekan.cubadocker.entity.Container> containersTable;
    @Inject
    private Button startContainersBtn;

    @Override
    public void ready() {
        initActions();
    }

    protected void initActions() {
        startContainersBtn.setAction(new StartContainerAction(containersTable, "startContainers"));
    }
}