package com.cloud.utils;

import java.util.List;

public interface ConnectionConciergeMBean {

    List <String> testValidityOfConnections();

    String resetConnection(String name);

    String resetKeepAliveTask(int seconds);

    List <String> getConnectionsNotPooled();
}
