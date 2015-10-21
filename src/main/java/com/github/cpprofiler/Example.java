package com.github.cpprofiler;

import com.github.cpprofiler.Connector;
import com.github.cpprofiler.Message.Node;
import org.zeromq.ZMQ;

public class Example {
  public static void main (String args[]) {

    Connector connector = new Connector();

    connector.connectToSocket(6565);
    connector.restartGist(-1);

    connector.sendNode(0, -1, -1, 2, Node.NodeStatus.BRANCH, "root", "");
    connector.sendNode(1, 0, 0, 0, Node.NodeStatus.SOLVED, "a", "");
    connector.sendNode(2, 0, 1, 0, Node.NodeStatus.FAILED, "b", "");

    connector.disconnectFromSocket();
  }
}