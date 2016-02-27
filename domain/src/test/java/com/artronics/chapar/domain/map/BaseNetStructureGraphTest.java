package com.artronics.chapar.domain.map;

import com.artronics.chapar.domain.entities.Sensor;
import com.artronics.chapar.domain.model.graph.impl.JGraphTUndirectedWeightedGraphWrapper;
import org.junit.Before;

import java.util.HashMap;
import java.util.Map;

public class BaseNetStructureGraphTest extends BaseGraphTest{
    protected NetworkStructureImpl networkStructure;
    protected Map<Sensor,Sensor> registeredSensors;

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();

        //Add sample graph as sensorGraph
        JGraphTUndirectedWeightedGraphWrapper gw = new JGraphTUndirectedWeightedGraphWrapper(sampleGraph1);
//        networkStructure = new NetworkStructureImpl(new JGraphTDelegator(),gw);

        registeredSensors = new HashMap<>();

        networkStructure.setRegisteredSensors(registeredSensors);

    }
}
