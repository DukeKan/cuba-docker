package com.github.dukekan.cubadocker.service;


import com.github.dockerjava.api.DockerClient;
import com.github.dukekan.cubadocker.entity.ConnectionParams;

public interface DockerService {
    String NAME = "cubadocker_DockerService";

    DockerClient createDockerClient(ConnectionParams connection);
}