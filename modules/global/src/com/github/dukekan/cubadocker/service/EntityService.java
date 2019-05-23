package com.github.dukekan.cubadocker.service;


import com.github.dukekan.cubadocker.entity.ConnectionParams;

import java.util.Collection;

public interface EntityService {
    String NAME = "cubadocker_EntityService";

    Collection<ConnectionParams> loadAllConnectionParams();
}