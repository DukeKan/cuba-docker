package com.github.dukekan.cubadocker.service;

import java.io.IOException;
import java.util.List;

/**
 * @author d.kuznetsov
 * @version $Id$
 */
public interface YouTrackService {
    String NAME = "cubadocker_YouTrackServicee";

    List<String> getVerifiedIssueNumbers() throws IOException;
}
