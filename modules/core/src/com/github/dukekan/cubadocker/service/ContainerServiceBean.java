package com.github.dukekan.cubadocker.service;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.InspectContainerCmd;
import com.github.dockerjava.api.command.LogContainerCmd;
import com.github.dockerjava.api.command.StatsCmd;
import com.github.dockerjava.api.command.TopContainerCmd;
import com.github.dockerjava.api.model.Frame;
import com.github.dockerjava.core.command.LogContainerResultCallback;
import com.github.dukekan.cubadocker.entity.ConnectionParams;
import com.github.dukekan.cubadocker.entity.Container;
import com.github.dukekan.cubadocker.results.StatsLogsResultCallback;
import com.haulmont.cuba.core.global.Metadata;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

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

            List<com.github.dockerjava.api.model.Container> dockerContainers = dockerService.exec(
                    dockerClient.listContainersCmd().withShowAll(true));
            for (com.github.dockerjava.api.model.Container dockerContainer : dockerContainers) {
                Container container = metadata.create(Container.class);
                container.setStatus(dockerContainer.getStatus());
                container.setContainerId(dockerContainer.getId());
                container.setImageId(dockerContainer.getImageId());
                container.setNames(dockerContainer.getNames());
                container.setImage(dockerContainer.getImage());
                container.setState(dockerContainer.getState());
                container.setConnectionParams(connection);
                containers.add(container);
            }
        }

        return containers;
    }

    @Override
    public void startContainers(Collection<Container> containers) {
        for (Container container : containers) {
            DockerClient dockerClient = dockerService.createDockerClient(container.getConnectionParams());
            dockerService.exec(dockerClient.startContainerCmd(container.getContainerId()));
        }
    }

    @Override
    public void stopContainers(Collection<Container> containers) {
        for (Container container : containers) {
            DockerClient dockerClient = dockerService.createDockerClient(container.getConnectionParams());
            dockerService.exec(dockerClient.stopContainerCmd(container.getContainerId()));
        }
    }

    @Override
    public String getContainerLogs(Container container) {
        DockerClient dockerClient = dockerService.createDockerClient(container.getConnectionParams());
        LogContainerCmd logContainerCmd = dockerClient.logContainerCmd(container.getContainerId()).withTail(500);

        final List<String> logs = new ArrayList<>();

        logContainerCmd.withStdOut(true).withStdErr(true);
        try {
            logContainerCmd.exec(new LogContainerResultCallback() {
                @Override
                public void onNext(Frame item) {
                    logs.add(item.toString());
                }
            }).awaitCompletion();
        } catch (InterruptedException e) {
            throw new RuntimeException("Failed to retrieve logs of container " + logContainerCmd.getContainerId(), e);
        }

        logContainerCmd.close();

        return String.join(System.lineSeparator(), logs);
    }

    @Override
    public String inspectContainer(Container container) {
        DockerClient dockerClient = dockerService.createDockerClient(container.getConnectionParams());
        InspectContainerCmd inspectContainerCmd = dockerClient.inspectContainerCmd(container.getContainerId());
        String inspectionResult = inspectContainerCmd.exec().toString();
        inspectContainerCmd.close();
        return inspectionResult;
    }

    @Override
    public String topContainerProcesses(Container container) {
        DockerClient dockerClient = dockerService.createDockerClient(container.getConnectionParams());
        TopContainerCmd topContainerCmd = dockerClient.topContainerCmd(container.getContainerId());
        StringBuilder sb = new StringBuilder();
        for (String[] line: topContainerCmd.exec().getProcesses()) {
            sb.append(String.join(" ", line));
            sb.append(System.lineSeparator());
        }
        topContainerCmd.close();
        return sb.toString();
    }

    @Override
    public String getContainerStats(Container container) {
        DockerClient dockerClient = dockerService.createDockerClient(container.getConnectionParams());
        StatsCmd statsCmd = dockerClient.statsCmd(container.getContainerId());
        CountDownLatch countDownLatch = new CountDownLatch(1);
        StatsLogsResultCallback statslogs = new StatsLogsResultCallback(countDownLatch);
        try {
            StatsLogsResultCallback statscallback = statsCmd.exec(statslogs);
            countDownLatch.await(5, TimeUnit.SECONDS);
            statscallback.close();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return statslogs.getStatistics().toString();
    }
}