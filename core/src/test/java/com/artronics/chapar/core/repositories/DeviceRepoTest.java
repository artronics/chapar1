package com.artronics.chapar.core.repositories;

import com.artronics.chapar.core.entities.Device;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertNotNull;

public class DeviceRepoTest extends BaseRepoTest{

    @Autowired
    protected DeviceRepo deviceRepo;

    @Test
    public void it_should_persist_a_device(){
        Device device = new Device();
        deviceRepo.save(device);

        assertNotNull(device.getId());
    }

}