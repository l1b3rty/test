package org.gl4u.und3f1n3d.provided.cluster;

import org.gl4u.und3f1n3d.api.cluster.Cluster;
import org.gl4u.und3f1n3d.api.cluster.ClusterNode;
import org.jgroups.*;
import org.jgroups.protocols.*;
import org.jgroups.protocols.pbcast.GMS;
import org.jgroups.protocols.pbcast.NAKACK2;
import org.jgroups.protocols.pbcast.STABLE;
import org.jgroups.stack.Protocol;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Objects;
import java.util.concurrent.Future;

public class JGroupsCluster implements Cluster {

    private Protocol[] protocolStack;
    private String config;

    private JChannel channel;

    public JGroupsCluster(String config) {

    }

    public JGroupsCluster(Protocol[] protocolStack) {
        this.protocolStack = protocolStack;
    }

    @Override
    public Future<ClusterNode> currentNode() {
        return null;
    }

    @Override
    public Future<Void> connect(String clusterName, String nodeName) {
        try {
            channel = createChannel();
            channel.setName(nodeName);

            channel.setReceiver(new ReceiverAdapter() {
                @Override
                public void viewAccepted(final View view) {
                    
                }

                @Override
                public void receive(final Message msg) {
                    
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Future<Void> disconnect() {
        return null;
    }

    @Override
    public Future<Void> sendMessage() {
        return null;
    }

    private JChannel createChannel() {
        try {
            if (Objects.isNull(protocolStack)) {
                if (null == config || config.length() == 0) {
                    return new JChannel(defaultStack());
                } else {
                    return new JChannel(config);
                }
            } else {
                return new JChannel(protocolStack);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to create JChannel");
        }
    }

    private Protocol[] defaultStack() throws UnknownHostException {
        UDP udp = new UDP();
        udp.setMulticastAddress(InetAddress.getByName("230.1.2.3"));
        udp.setMcastPort(5555);
        udp.setBindAddress(InetAddress.getByName("127.0.0.1"));

        return new Protocol[]{
                udp,
                new PING(),
                new MERGE3(),
                new FD_SOCK(),
                new FD_ALL(),
                new VERIFY_SUSPECT(),
                new BARRIER(),
                new NAKACK2(),
                new UNICAST3(),
                new STABLE(),
                new GMS(),
                new UFC(),
                new MFC(),
                new FRAG2()};
    }

}
