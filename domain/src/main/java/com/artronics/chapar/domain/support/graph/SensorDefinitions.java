package com.artronics.chapar.domain.support.graph;

import com.artronics.chapar.domain.entities.Client;
import com.artronics.chapar.domain.entities.Controller;
import com.artronics.chapar.domain.entities.Sensor;
import com.artronics.chapar.domain.entities.address.UnicastAddress;

public class SensorDefinitions {
    protected Controller controller;

    protected Client client;
    protected Client client2;

    protected Sensor sink1;
    protected Sensor sink2;

    protected Sensor sameAddSensor1;
    protected Sensor sameAddSensor2;

    protected Sensor sensor135;
    protected Sensor sensor136;
    protected Sensor sensor137;

    protected Sensor sensor245;
    protected Sensor sensor246;

    public SensorDefinitions() {
        controller = new Controller(1000L);

        client = new Client(1L,controller);
        client2 = new Client(2L,controller);

        UnicastAddress add1 = UnicastAddress.create(client,1L);
        UnicastAddress add2 = UnicastAddress.create(client2,2L);


        UnicastAddress add30_1 = UnicastAddress.create(client,30L);
        UnicastAddress add30_2 = UnicastAddress.create(client2,30L);

        UnicastAddress add135 = UnicastAddress.create(client,135L);
        UnicastAddress add136 = UnicastAddress.create(client,136L);
        UnicastAddress add137 = UnicastAddress.create(client,137L);

        sink1 = Sensor.create(add1);
        sink2 = Sensor.create(add2);
        sink1.setType(Sensor.Type.SINK);
        sink2.setType(Sensor.Type.SINK);

        sameAddSensor1 = Sensor.create(add30_1);
        sameAddSensor2 = Sensor.create(add30_2);

        sensor135 = Sensor.create(add135);
        sensor136 = Sensor.create(add136);
        sensor137 = Sensor.create(add137);

        UnicastAddress add245 = UnicastAddress.create(client2,245L);
        UnicastAddress add246 = UnicastAddress.create(client2,246L);

        sensor245 = Sensor.create(add245);
        sensor246 = Sensor.create(add246);
    }

    public Client getClient() {
        return client;
    }

    public Sensor getSink1() {
        return sink1;
    }

    public void setSink1(Sensor sink1) {
        this.sink1 = sink1;
    }

    public Sensor getSink2() {
        return sink2;
    }

    public void setSink2(Sensor sink2) {
        this.sink2 = sink2;
    }

    public Sensor getSameAddSensor1() {
        return sameAddSensor1;
    }

    public void setSameAddSensor1(Sensor sameAddSensor1) {
        this.sameAddSensor1 = sameAddSensor1;
    }

    public Sensor getSameAddSensor2() {
        return sameAddSensor2;
    }

    public void setSameAddSensor2(Sensor sameAddSensor2) {
        this.sameAddSensor2 = sameAddSensor2;
    }

    public Sensor getSensor135() {
        return sensor135;
    }

    public void setSensor135(Sensor sensor135) {
        this.sensor135 = sensor135;
    }

    public Sensor getSensor136() {
        return sensor136;
    }

    public void setSensor136(Sensor sensor136) {
        this.sensor136 = sensor136;
    }

    public Sensor getSensor137() {
        return sensor137;
    }

    public void setSensor137(Sensor sensor137) {
        this.sensor137 = sensor137;
    }

    public Sensor getSensor245() {
        return sensor245;
    }

    public void setSensor245(Sensor sensor245) {
        this.sensor245 = sensor245;
    }

    public Sensor getSensor246() {
        return sensor246;
    }

    public void setSensor246(Sensor sensor246) {
        this.sensor246 = sensor246;
    }

}
