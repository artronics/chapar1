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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class NodeMapUpdaterImplTest extends BaseMapTest {

    private NodeMapUpdater mapUpdater;

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
        mapUpdater = new NodeMapUpdaterImpl();
    }

    @Test
    public void it_should_drop_links_which_are_not_present_in_new_set() throws SensorNotRegistered {
        SensorLink l136_135 = new SensorLink(sensor135,23D);
        Set<SensorLink> links = new HashSet<>(Arrays.asList(l136_135));

        //when we send links to updater it should drop links from 136 to 30 and from 136 to 137
        mapUpdater.update(nodeMap, sensor136,links);

        assertFalse(nodeMap.hasLink(sensor136, sensor30));
        assertFalse(nodeMap.hasLink(sensor136, sensor137));

        assertTrue(nodeMap.hasLink(sensor136, sensor135));
    }

    @Test
    public void it_should_update_weight_value_for_links() throws SensorNotRegistered {
        SensorLink l136_135 = new SensorLink(sensor135,230D);
        Set<SensorLink> links = new HashSet<>(Arrays.asList(l136_135));

        //when we send links to updater it should drop links from 136 to 30 and from 136 to 137
        mapUpdater.update(nodeMap, sensor136,links);

        Double weight = nodeMap.getWeigh(sensor136, sensor135);
        assertThat(weight,is(equalTo(230D)));

        weight = nodeMap.getWeigh(sensor135, sensor136);
        assertThat(weight,is(equalTo(230D)));
    }

    @Test
    public void it_should_find_island_nodes() throws SensorNotRegistered {
        SensorLink l136_135 = new SensorLink(sensor135,230D);
        Set<SensorLink> links = new HashSet<>(Arrays.asList(l136_135));

        //when we send links to updater it should drop links from 136 to 30 and from 136 to 137
        //then node 137 must be island
        Set<Sensor> islands = new HashSet<>();
        mapUpdater.update(nodeMap, sensor136,links,islands);

        assertThat(islands.size(),is(equalTo(1)));
        assertTrue(islands.contains(sensor137));
    }

    /*
        Detailed Tests
     */
    @Test
    public void it_should_not_touch_unrelated_links() throws SensorNotRegistered {
        SensorLink l136_135 = new SensorLink(sensor135,23D);
        Set<SensorLink> links = new HashSet<>(Arrays.asList(l136_135));

        mapUpdater.update(nodeMap, sensor136,links);
        //we want to update sensor136 links. other nodes must be as it was
        assertTrue(nodeMap.contains(sensor135));
        assertTrue(nodeMap.contains(sensor136));
        assertTrue(nodeMap.contains(sensor137));
        assertTrue(nodeMap.contains(sensor30));

        assertTrue(nodeMap.hasLink(sensor135, sensor30));
        assertTrue(nodeMap.hasLink(sink1, sensor30));

        assertThat(nodeMap.getWeigh(sensor135, sensor30),is(equalTo(20D)));
    }

    /*
        Exception tests
     */
    @Test(expected = SensorNotRegistered.class)
    public void it_should_throw_exp_if_srcNode_is_not_already_in_map() throws SensorNotRegistered {
        Set<SensorLink> links = new HashSet<>();
        mapUpdater.update(nodeMap, Sensor.create(UnicastAddress.create(client,2324L)),links);
    }

    @Test(expected = SensorNotRegistered.class)
    public void it_should_throw_exp_if_any_of_neighbors_are_not_already_in_map() throws SensorNotRegistered {
        Set<SensorLink> links = new HashSet<>();
        links.add(new SensorLink(Sensor.create(UnicastAddress.create(client,4224L)),23D));
        mapUpdater.update(nodeMap, sensor135,links);
    }

}