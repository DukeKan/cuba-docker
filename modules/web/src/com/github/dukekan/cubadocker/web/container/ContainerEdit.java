package com.github.dukekan.cubadocker.web.container;

import com.haulmont.cuba.gui.components.AbstractEditor;
import com.github.dukekan.cubadocker.entity.Container;
import com.haulmont.cuba.gui.components.OptionsList;

import javax.inject.Inject;

public class ContainerEdit extends AbstractEditor<Container> {
    @Inject
    private OptionsList names;

    @Override
    protected void postInit() {
        initNamesOptionList();
    }

    protected void initNamesOptionList() {
        names.setNullOptionVisible(false);
        names.setOptionsList(getItem().getNamesList());
    }
}