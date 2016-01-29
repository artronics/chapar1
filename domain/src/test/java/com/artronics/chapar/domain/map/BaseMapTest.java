package com.artronics.chapar.domain.map;

import org.junit.Before;

public class BaseMapTest extends BaseGraphTest{
    protected DeviceMap deviceMap;

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        deviceMap = new DeviceMapImpl(sampleGraph1);
    }
}
