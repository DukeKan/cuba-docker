package com.github.dukekan.cubadocker.web.container;

import com.github.dukekan.cubadocker.entity.Container;
import com.github.dukekan.cubadocker.service.YouTrackService;
import com.github.dukekan.cubadocker.web.container.actions.StartContainerAction;
import com.github.dukekan.cubadocker.web.container.actions.StopContainerAction;
import com.github.dukekan.cubadocker.web.datasources.ContainerCollectionDatasource;
import com.haulmont.cuba.gui.components.AbstractLookup;
import com.haulmont.cuba.gui.components.PopupButton;
import com.haulmont.cuba.gui.components.Table;

import javax.inject.Inject;
import java.io.IOException;
import java.util.List;

public class ContainerBrowse extends AbstractLookup {

    @Inject
    protected Table<com.github.dukekan.cubadocker.entity.Container> containersTable;
    @Inject
    protected PopupButton containerActionsBtn;
    @Inject
    protected YouTrackService youTrackService;
    @Inject
    private ContainerCollectionDatasource containersDs;


    @Override
    public void ready() {
        initActions();
        initTable();
    }

    private void initTable() {
        List<String> verifiedIssueNumbers;
        try {
            verifiedIssueNumbers = youTrackService.getVerifiedIssueNumbers();
        } catch (IOException e) {
            showNotification("youtrackConnnectionError");
            return;
        }
        containersTable.setStyleProvider((container, property) -> {
            if (property == null && container.getState().equals("running")) {
                String name = container.getNamesString();
                int from = name.indexOf("knd-");
                int to = name.indexOf("_knd_1");
                try {
                    String issueNumber = name.substring(from+4, to);
                    if (verifiedIssueNumbers.contains(issueNumber)) {
                        return "red-row";
                    }
                } catch (Exception e) {
                    //do nothing
                }
            }
            return null;
        });
    }

    private void initActions() {
        containerActionsBtn.addAction(new StartContainerAction(containersTable, "startContainers"));
        containerActionsBtn.addAction(new StopContainerAction(containersTable, "stopContainers", () -> containersDs.refresh()));
    }
}