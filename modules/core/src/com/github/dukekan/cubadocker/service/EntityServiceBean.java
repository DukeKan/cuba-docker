package com.github.dukekan.cubadocker.service;

import com.github.dukekan.cubadocker.entity.ConnectionParams;
import com.haulmont.cuba.core.global.DataManager;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Collection;

@Service(EntityService.NAME)
public class EntityServiceBean implements EntityService {

    @Inject
    protected DataManager dataManager;

    @Override
    public Collection<ConnectionParams> loadAllConnectionParams() {
        return dataManager.load(ConnectionParams.class).list();
    }
}