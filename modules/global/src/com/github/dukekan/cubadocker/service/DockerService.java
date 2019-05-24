package com.github.dukekan.cubadocker.service;


import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.SyncDockerCmd;
import com.github.dukekan.cubadocker.entity.ConnectionParams;

public interface DockerService {
    String NAME = "cubadocker_DockerService";

    DockerClient createDockerClient(ConnectionParams connection);

    <RES_T> RES_T exec(SyncDockerCmd<RES_T> command);
}