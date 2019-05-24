package com.github.dukekan.cubadocker.web.container;

import com.github.dukekan.cubadocker.web.container.actions.StartContainerAction;
import com.github.dukekan.cubadocker.web.container.actions.StopContainerAction;
import com.haulmont.cuba.gui.components.AbstractLookup;
import com.haulmont.cuba.gui.components.PopupButton;
import com.haulmont.cuba.gui.components.Table;

import javax.inject.Inject;

public class ContainerBrowse extends AbstractLookup {

    @Inject
    protected Table<com.github.dukekan.cubadocker.entity.Container> containersTable;
    @Inject
    protected PopupButton containerActionsBtn;

    @Override
    public void ready() {
        initActions();
    }

    protected void initActions() {
        containerActionsBtn.addAction(new StartContainerAction(containersTable, "startContainers"));
        containerActionsBtn.addAction(new StopContainerAction(containersTable, "stopContainers"));
    }
}