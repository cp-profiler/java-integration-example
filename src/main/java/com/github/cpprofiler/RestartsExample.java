package com.github.cpprofiler;

import com.github.cpprofiler.Connector;
import org.zeromq.ZMQ;

public class RestartsExample {
  public static void main (String args[]) {

    Connector c = new Connector();

    /// Connect to port 6565 (default for cp-profiler)
    c.connect(6565);

    /// Starting a new tree (also used in case of a restart)
    c.restart(0);


    /// SEND ROOT NODE
    /// Arguments:
    /// 1. 0 -- node index;
    /// 2. -1 -- parent's index (-1 indicates that the parent does not exist)
    /// 3. -1 -- parent's current alternative (normally starts with 0, but -1 as irrelevant here)
    /// 4.  2 -- number of children nodes (as in binary tree)
    /// 5.  Connector.NodeStatus.BRANCH -- decision node / node with children
    /// The rest of fields are optional and can be specified in arbitrary order
    c.createNode(0, -1, -1, 2, Connector.NodeStatus.BRANCH).setLabel("root-1").send();

    // SEND LEFT CHILD
    c.createNode(1, 0, 0, 0, Connector.NodeStatus.FAILED).setLabel("a").send();

    // SEND RIGHT CHILD
    // "Info" field is specified, which is arbitrary text to go along with the node
    c.createNode(2, 0, 1, 0, Connector.NodeStatus.FAILED).setLabel("b").setInfo("some info").send();

    c.restart(1);

    c.createNode(0, -1, -1, 2, Connector.NodeStatus.BRANCH).setLabel("root-2").setRestartId(1).send();
    c.createNode(1, 0, 0, 0, Connector.NodeStatus.SOLVED).setLabel("c").setRestartId(1).send();
    c.createNode(2, 0, 1, 0, Connector.NodeStatus.FAILED).setLabel("d").setRestartId(1).send();

    c.disconnect();
  }
}