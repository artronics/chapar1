package com.artronics.chapar.core.map;

import com.artronics.chapar.core.exceptions.RouteNotFound;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;

public class NodeMapMultipleDevicesTest extends BaseGraphTest{
    private final static Logger log = Logger.getLogger(NodeMapMultipleDevicesTest.class);
    NodeMap nodeMap;

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        nodeMap = new NodeMapImpl(multipleDeviceGraph);
    }

    @Test
    public void there_is_no_link_between_two_nodes_with_same_add_and_diff_device(){
        assertFalse(nodeMap.hasLink(sameAddNode1,sameAddNode2));
    }

    @Test(expected = RouteNotFound.class)
    public void it_should_throw_exp_if_we_ask_for_a_route_between_two_devices() throws RouteNotFound {
        nodeMap.getShortestPath(sink1,sameAddNode2);
    }


}
