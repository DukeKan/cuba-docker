package com.github.dukekan.cubadocker.service;


import com.github.dukekan.cubadocker.entity.ConnectionParams;
import com.github.dukekan.cubadocker.entity.Container;

import java.util.Collection;

public interface ContainerService {
    String NAME = "cubadocker_ContainerService";

    Collection<Container> getContainers(Collection<ConnectionParams> connections);

    void startContainers(Collection<Container> containers);

    void stopContainers(Collection<Container> containers);

    String getContainerLogs(Container container);

    String inspectContainer(Container container);

    String topContainerProcesses(Container container);

    String getContainerStats(Container container);
}