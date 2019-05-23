package com.github.dukekan.cubadocker.web.datasources;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.DockerCmdExecFactory;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.jaxrs.JerseyDockerCmdExecFactory;
import com.github.dukekan.cubadocker.entity.ConnectionParams;
import com.github.dukekan.cubadocker.entity.Container;
import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.gui.data.impl.CustomCollectionDatasource;

import java.util.*;

/**
 * @author d.kuznetsov
 * @version $Id$
 */
public class ContainerCollectionDatasource extends CustomCollectionDatasource<Container, UUID> {

    private DataManager dataManager = AppBeans.get(DataManager.NAME);

    @Override
    protected Collection<Container> getEntities(Map<String, Object> params) {
        List<Container> containers = new ArrayList<>();

        List<ConnectionParams> allConnections = dataManager.load(ConnectionParams.class).list();
        for (ConnectionParams connection : allConnections) {
            DockerClientConfig config = DefaultDockerClientConfig.createDefaultConfigBuilder()
                    .withDockerHost("tcp://" + connection.getHost() + ":" + connection.getPort())
                    .withDockerTlsVerify(false)
                    .build();
            // using jaxrs/jersey implementation here (netty impl is also available)
            DockerCmdExecFactory dockerCmdExecFactory = new JerseyDockerCmdExecFactory()
                    .withReadTimeout(1000)
                    .withConnectTimeout(1000)
                    .withMaxTotalConnections(100)
                    .withMaxPerRouteConnections(10);

            DockerClient dockerClient = DockerClientBuilder.getInstance(config)
                    .withDockerCmdExecFactory(dockerCmdExecFactory)
                    .build();

            List<com.github.dockerjava.api.model.Container> dockerContainers = dockerClient.listContainersCmd().exec();
            for (com.github.dockerjava.api.model.Container dockerContainer : dockerContainers) {
                System.out.println(String.join(",", dockerContainer.getNames()));
                Container container = metadata.create(Container.class);
                container.setStatus(dockerContainer.getStatus());
                container.setContainerId(dockerContainer.getId());
                container.setImageId(dockerContainer.getImageId());
                containers.add(container);
            }
        }

        return containers;
    }
}
