package com.artronics.chapar.domain.utilities;

import com.artronics.chapar.domain.entities.Client;
import com.artronics.chapar.domain.entities.Sensor;
import com.artronics.chapar.domain.entities.SensorLink;
import com.artronics.chapar.domain.entities.address.UnicastAddress;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class SensorLinkUtilsTest {
    private static final Double WEIGHT = 1.0D;

    private final SensorLinkUtils linkUtils = new SensorLinkUtils();

    private Client client = new Client(299L);

    private Sensor n1;
    private Sensor n2;
    private Sensor n3;

    private SensorLink link1;
    private SensorLink link2;
    private SensorLink link3;

    private Set<SensorLink> oldSet = new HashSet<>();
    private Set<SensorLink> newSet = new HashSet<>();

    private Set<SensorLink> links = new HashSet<>();

    @Before
    public void setUp() throws Exception {
        n1 = Sensor.create(UnicastAddress.create(client, 1L));
        n2 = Sensor.create(UnicastAddress.create(client, 2L));
        n3 = Sensor.create(UnicastAddress.create(client, 3L));

        link1 = new SensorLink(n1, WEIGHT);
        link2 = new SensorLink(n2, WEIGHT);
        link3 = new SensorLink(n3, WEIGHT);
    }

    @After
    public void tearDown() throws Exception {
        links.clear();
        oldSet.clear();
        newSet.clear();
    }

    @Test
    public void it_should_merge_what_is_not_in_set2() {
        oldSet.add(link1);
        oldSet.add(link2);

        newSet.add(link3);

        links = SensorLinkUtils.merge(oldSet, newSet);
        assertThat(links.size(), is(equalTo(3)));
    }

    @Test
    public void it_should_also_update_weight_in_what_is_common_between_two_sets() {
        oldSet.add(link1);
        oldSet.add(link2);

        newSet.add(link3);

        // create a new link with n1 and diff weight
        SensorLink link1_new = new SensorLink(n1, 23D);
        newSet.add(link1_new);//Now newSet has an updated value of link1

        links = SensorLinkUtils.merge(oldSet, newSet);

        links.forEach(link -> {
            if (link.equals(link1)) {
                assertThat(link.getWeight(), is(equalTo(23D)));
            }
        });
    }

    @Test
    public void it_should_get_a_set_of_removedLinks() throws Exception {
        oldSet.add(link1);
        oldSet.add(link2);

        //link2 is present so link1 is removed
        newSet.add(link2);

        Set<SensorLink> removeLinks = new HashSet<>();
        SensorLinkUtils.merge(oldSet,newSet,removeLinks);

        assertThat(removeLinks.size(),is(equalTo(1)));
        assertThat(removeLinks.contains(link1),is(true));
    }
}