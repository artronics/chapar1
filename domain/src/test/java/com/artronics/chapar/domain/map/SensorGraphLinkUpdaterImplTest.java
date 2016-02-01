package com.artronics.chapar.domain.map;

import com.artronics.chapar.domain.entities.Sensor;
import com.artronics.chapar.domain.entities.SensorLink;
import com.artronics.chapar.domain.entities.address.UnicastAddress;
import com.artronics.chapar.domain.exceptions.SensorNotRegistered;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

public class SensorGraphLinkUpdaterImplTest extends BaseNetStructureGraphTest{
    private SensorGraphLinkUpdaterImpl updater;

    /*
     * node number 30 is sameAddNode
     *
     * Graph is like
     *       sink:0
     *       /   \
     *      w50  w10
     *      /      \
     *   135 --w20-- 30
     *     \         /
     *     w25    w100
     *       \    /
     *        136
     *         |
     *        w30
     *         |
     *        137
     *
     */
    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();

        updater = new SensorGraphLinkUpdaterImpl();
        updater.setNetworkStructure(networkStructure);
    }

    /*
        Exception tests
     */
    @Test(expected = SensorNotRegistered.class)
    public void it_should_throw_exp_if_srcNode_is_not_already_in_map() throws SensorNotRegistered {
        Set<SensorLink> links = new HashSet<>();
        updater.update(Sensor.create(UnicastAddress.create(client,2324L)),links);
    }

    @Test(expected = SensorNotRegistered.class)
    public void it_should_throw_exp_if_any_of_neighbors_are_not_already_in_map() throws SensorNotRegistered {
        Set<SensorLink> links = new HashSet<>();
        links.add(new SensorLink(Sensor.create(UnicastAddress.create(client,4224L)),23D));
        updater.update(sensor135,links);
    }

}