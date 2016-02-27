package com.artronics.chapar.domain.map;

import com.artronics.chapar.domain.entities.Sensor;
import org.junit.Before;

import java.util.HashMap;
import java.util.Map;

public class BaseNetStructureGraphTest extends BaseGraphTest{
    protected NetworkStructureImpl networkStructure;
    protected Map<Sensor,Sensor> registeredSensors;
    protected NodeMap nodeMap;

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();

        networkStructure = new NetworkStructureImpl();

        registeredSensors = new HashMap<>();
        nodeMap = new NodeMapImpl(sampleGraph1);

        networkStructure.setRegisteredSensors(registeredSensors);
        networkStructure.setNodeMap(nodeMap);
    }

}
