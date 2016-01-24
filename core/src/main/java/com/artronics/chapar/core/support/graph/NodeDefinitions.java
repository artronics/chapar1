package com.artronics.chapar.core.support.graph;

import com.artronics.chapar.core.entities.Address;
import com.artronics.chapar.core.entities.Device;
import com.artronics.chapar.core.entities.Node;

public class NodeDefinitions {
    protected Device device;
    protected Device device2;

    protected Node sink1;
    protected Node sink2;

    protected Node sameAddNode1;
    protected Node sameAddNode2;

    protected Node node135;
    protected Node node136;
    protected Node node137;

    protected Node node245;
    protected Node node246;

    public NodeDefinitions() {
        device = new Device(1L);
        device2 = new Device(2L);

        Address add1 = Address.create(device,1L);
        Address add2 = Address.create(device2,2L);


        Address add30_1 = Address.create(device,30L);
        Address add30_2 = Address.create(device2,30L);

        Address add135 = Address.create(device,135L);
        Address add136 = Address.create(device,136L);
        Address add137 = Address.create(device,137L);

        sink1 = Node.create(add1);
        sink2 = Node.create(add2);

        sameAddNode1 = Node.create(add30_1);
        sameAddNode2 = Node.create(add30_2);

        node135 = Node.create(add135);
        node136 = Node.create(add136);
        node137 = Node.create(add137);

        Address add245 = Address.create(device2,245L);
        Address add246 = Address.create(device2,246L);

        node245 = Node.create(add245);
        node246 = Node.create(add246);
    }

    public Device getDevice() {
        return device;
    }

    public Node getSink1() {
        return sink1;
    }

    public void setSink1(Node sink1) {
        this.sink1 = sink1;
    }

    public Node getSink2() {
        return sink2;
    }

    public void setSink2(Node sink2) {
        this.sink2 = sink2;
    }

    public Node getSameAddNode1() {
        return sameAddNode1;
    }

    public void setSameAddNode1(Node sameAddNode1) {
        this.sameAddNode1 = sameAddNode1;
    }

    public Node getSameAddNode2() {
        return sameAddNode2;
    }

    public void setSameAddNode2(Node sameAddNode2) {
        this.sameAddNode2 = sameAddNode2;
    }

    public Node getNode135() {
        return node135;
    }

    public void setNode135(Node node135) {
        this.node135 = node135;
    }

    public Node getNode136() {
        return node136;
    }

    public void setNode136(Node node136) {
        this.node136 = node136;
    }

    public Node getNode137() {
        return node137;
    }

    public void setNode137(Node node137) {
        this.node137 = node137;
    }

    public Node getNode245() {
        return node245;
    }

    public void setNode245(Node node245) {
        this.node245 = node245;
    }

    public Node getNode246() {
        return node246;
    }

    public void setNode246(Node node246) {
        this.node246 = node246;
    }
}
