package com.artronics.chapar.controller.sdwn.controller;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class RssiSimpleWeightCalculatorTest {

    WeightCalculator weightCalculator;

    @Before
    public void setUp() throws Exception {
        weightCalculator = new RssiSimpleWeightCalculator();
    }
    @Test
    public void it_should_return_right_weight()
    {
        double weight = weightCalculator.calculate( 100);

        assertThat(weight, equalTo(155.0));
    }

}