package com.github.dukekan.cubadocker.core;

import com.github.dukekan.cubadocker.CubadockerTestContainer;
import com.github.dukekan.cubadocker.service.YouTrackService;
import com.haulmont.cuba.core.global.AppBeans;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class SampleIntegrationTest {

    @ClassRule
    public static CubadockerTestContainer cont = CubadockerTestContainer.Common.INSTANCE;

    private YouTrackService youTrackService;

    @Before
    public void setUp() throws Exception {
        youTrackService = AppBeans.get(YouTrackService.class);
    }

    @Test
    public void testLoadUser() throws IOException {
        List<String> verifiedIssueNumbers = youTrackService.getVerifiedIssueNumbers();
        Assert.assertTrue(verifiedIssueNumbers.size() > 0);
    }
}