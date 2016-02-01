package com.artronics.chapar.controller.services.impl;

import com.artronics.chapar.domain.entities.Sensor;
import com.artronics.chapar.domain.map.NetworkStructure;
import com.artronics.chapar.domain.repositories.SensorRepo;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class SensorRegistrationServiceImplTest {
    @InjectMocks
    private SensorRegistrationServiceImpl sensorRegistrationService;
    @Mock
    private NetworkStructure networkStructure;
    @Mock
    private SensorRepo sensorRepo;

    private Sensor sensor;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        sensor = new Sensor();
    }

    @Test
    public void it_should_persist_sensor() throws Exception {
        sensorRegistrationService.registerSensor(sensor);

        verify(sensorRepo,times(1)).save(sensor);
    }

    @Test
    public void it_should_add_sensor_to_networkStructure() throws Exception {
        sensorRegistrationService.registerSensor(sensor);

        verify(networkStructure,times(1)).addSensor(sensor);
    }
}