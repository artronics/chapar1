package com.artronics.chapar.domain.support.graph;

import com.artronics.chapar.domain.entities.Sensor;

public class NodeDefinitions {
    protected Sensor sink1 = new Sensor(0L);
    protected Sensor sink2 = new Sensor(1L);

    protected Sensor sameAddSensor1 = new Sensor(30L);
    protected Sensor sameAddSensor2 = new Sensor(30L);

    protected Sensor sensor135 = new Sensor(135L);
    protected Sensor sensor136 = new Sensor(136L);
    protected Sensor sensor137 = new Sensor(137L);

    protected Sensor sensor245 = new Sensor(245L);
    protected Sensor sensor246 = new Sensor(246L);

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
