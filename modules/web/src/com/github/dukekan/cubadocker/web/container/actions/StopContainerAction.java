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

    protected ContainerService containerService = AppBeans.get(ContainerService.NAME);

    public StopContainerAction(ListComponent target, String id) {
        super(target, id);
    }

    @Override
    public void actionPerform(Component component) {
        Set<Container> selectedContainers = target.getSelected();
        containerService.stopContainers(selectedContainers);
        target.getDatasource().refresh();
        target.setSelected(selectedContainers);
    }

    @Override
    public String getCaption() {
        return messages.getMessage(this.getClass(), "stopContainers");
    }
}
