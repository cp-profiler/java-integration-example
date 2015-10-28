package com.github.cpprofiler;

import com.github.cpprofiler.Connector;
import org.zeromq.ZMQ;

public class RestartsExample {
  public static void main (String args[]) {

    Connector c = new Connector();

    /// Connect to port 6565 (default for cp-profiler)
    c.connect(6565);

    /// Initiate restarts
    c.restart(0);

    c.createNode(0, -1, -1, 2, Connector.NodeStatus.BRANCH).setLabel("root-1").setRestartId(0).send();
    c.createNode(1, 0, 0, 0, Connector.NodeStatus.FAILED).setLabel("a").setRestartId(0).send();
    c.createNode(2, 0, 1, 0, Connector.NodeStatus.FAILED).setLabel("b").setRestartId(0).send();

    /// Restart happens
    c.restart(1);

    c.createNode(0, -1, -1, 2, Connector.NodeStatus.BRANCH).setLabel("root-2").setRestartId(1).send();
    c.createNode(1, 0, 0, 0, Connector.NodeStatus.SOLVED).setLabel("c").setRestartId(1).send();
    c.createNode(2, 0, 1, 0, Connector.NodeStatus.FAILED).setLabel("d").setRestartId(1).send();

    /// Restart happens
    c.restart(2);

    c.createNode(0, -1, -1, 2, Connector.NodeStatus.BRANCH).setLabel("root-2").setRestartId(2).send();
    c.createNode(1, 0, 0, 0, Connector.NodeStatus.FAILED).setLabel("e").setRestartId(2).send();
    c.createNode(2, 0, 1, 0, Connector.NodeStatus.FAILED).setLabel("f").setRestartId(2).send();

    c.disconnect();
  }
}