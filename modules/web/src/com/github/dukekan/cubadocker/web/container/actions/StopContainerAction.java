package com.github.dukekan.cubadocker.web.container.actions;

import com.github.dukekan.cubadocker.entity.Container;
import com.github.dukekan.cubadocker.service.ContainerService;
import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.gui.components.Component;
import com.haulmont.cuba.gui.components.ListComponent;
import com.haulmont.cuba.gui.components.actions.ItemTrackingAction;

import java.util.Set;

/**
 * @author d.kuznetsov
 * @version $Id$
 */
public class StopContainerAction extends ItemTrackingAction {

    private final Runnable afterPerformHandler;
    protected ContainerService containerService = AppBeans.get(ContainerService.NAME);

    public StopContainerAction(ListComponent target, String id, Runnable afterPerformHandler) {
        super(target, id);
        this.afterPerformHandler = afterPerformHandler;
    }

    @Override
    public void actionPerform(Component component) {
        Set<Container> selectedContainers = target.getSelected();
        containerService.stopContainers(selectedContainers);
        target.getDatasource().refresh();
        target.setSelected(selectedContainers);
        afterPerformHandler.run();
    }

    @Override
    public String getCaption() {
        return messages.getMessage(this.getClass(), "stopContainers");
    }
}
