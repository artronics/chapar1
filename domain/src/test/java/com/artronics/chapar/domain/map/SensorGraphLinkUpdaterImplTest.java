package com.artronics.chapar.domain.map;

import com.artronics.chapar.domain.entities.Sensor;
import com.artronics.chapar.domain.entities.SensorLink;
import com.artronics.chapar.domain.entities.address.UnicastAddress;
import com.artronics.chapar.domain.exceptions.SensorNotRegistered;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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

    @Test
    public void it_should_drop_links_which_are_not_present_in_new_set() throws SensorNotRegistered {
        SensorLink l136_135 = new SensorLink(sensor135,23D);
        Set<SensorLink> links = new HashSet<>(Arrays.asList(l136_135));

        //when we send links to updater it should drop links from 136 to 30 and from 136 to 137
        updater.update(sensor136,links);

        assertFalse(networkStructure.hasLink(sensor136, sensor30));
        assertFalse(networkStructure.hasLink(sensor136, sensor137));

        assertTrue(networkStructure.hasLink(sensor136, sensor135));
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