package com.github.dukekan.cubadocker.service;

import com.github.dockerjava.api.DockerClient;
import com.github.dukekan.cubadocker.entity.ConnectionParams;
import com.github.dukekan.cubadocker.entity.Container;
import com.haulmont.cuba.core.global.Metadata;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service(ContainerService.NAME)
public class ContainerServiceBean implements ContainerService {

    @Inject
    protected DockerService dockerService;
    @Inject
    protected Metadata metadata;

    @Override
    public Collection<Container> getContainers(Collection<ConnectionParams> connections) {
        List<Container> containers = new ArrayList<>();

        for (ConnectionParams connection : connections) {
            DockerClient dockerClient = dockerService.createDockerClient(connection);

            List<com.github.dockerjava.api.model.Container> dockerContainers = dockerClient.listContainersCmd().exec();
            for (com.github.dockerjava.api.model.Container dockerContainer : dockerContainers) {
                Container container = metadata.create(Container.class);
                container.setStatus(dockerContainer.getStatus());
                container.setContainerId(dockerContainer.getId());
                container.setImageId(dockerContainer.getImageId());
                container.setNames(dockerContainer.getNames());
                container.setImage(dockerContainer.getImage());
                container.setState(dockerContainer.getState());
                containers.add(container);
            }
        }

        return containers;
    }
}