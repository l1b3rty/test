package org.gl4u.und3f1n3d.api.cluster;

import java.util.concurrent.Future;

public interface Cluster {

    Future<ClusterNode> currentNode();

    Future<Void> connect(String clusterName, String nodeName);

    Future<Void> disconnect();

    Future<Void> sendMessage();

}
