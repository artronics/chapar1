package com.artronics.chapar.controller.sdwn.map;

import com.artronics.chapar.core.entities.Address;
import com.artronics.chapar.core.entities.Device;
import com.artronics.chapar.core.entities.Link;
import com.artronics.chapar.core.entities.Node;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class RssiSimpleWeightCalculatorTest {
    WeightCalculator weightCalculator;

    Device device;
    Node src;
    Node dst;

    Link link;

    @Before
    public void setUp() throws Exception {
        weightCalculator = new RssiSimpleWeightCalculator();

        device = new Device(1L);
        src = Node.create(Address.create(1L));
        dst = Node.create(Address.create(2L));

        link = new Link(dst);
    }

    @Test
    public void it_should_return_right_weight()
    {
        double weight = weightCalculator.calculate( 100);

        assertThat(weight, equalTo(155.0));

    }

}