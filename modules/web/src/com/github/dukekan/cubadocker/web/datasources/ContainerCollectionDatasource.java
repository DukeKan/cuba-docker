package com.github.dukekan.cubadocker.web.datasources;

import com.github.dukekan.cubadocker.entity.ConnectionParams;
import com.github.dukekan.cubadocker.entity.Container;
import com.github.dukekan.cubadocker.service.ContainerService;
import com.github.dukekan.cubadocker.service.EntityService;
import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.gui.data.impl.CustomCollectionDatasource;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

/**
 * @author d.kuznetsov
 * @version $Id$
 */
public class ContainerCollectionDatasource extends CustomCollectionDatasource<Container, UUID> {

    protected EntityService entityService = AppBeans.get(EntityService.NAME);
    protected ContainerService containerService = AppBeans.get(ContainerService.NAME);

    @Override
    protected Collection<Container> getEntities(Map<String, Object> params) {
        Collection<ConnectionParams> connectionParams = entityService.loadAllConnectionParams();
        return containerService.getContainers(connectionParams);
    }
}
