package com.artronics.chapar.controller.services.impl;

import com.artronics.chapar.domain.entities.Client;
import com.artronics.chapar.domain.entities.Sensor;
import com.artronics.chapar.domain.entities.address.UnicastAddress;
import com.artronics.chapar.domain.map.NetworkStructure;
import com.artronics.chapar.domain.repositories.SensorRepo;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

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
        sensor.setAddress(UnicastAddress.create(new Client(12L),12345L));
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

    @Test
    public void it_should_return_registered_sensor_if_it_is_already_registered() throws Exception {
        when(networkStructure.containsSensor(sensor)).thenReturn(false);
        sensorRegistrationService.registerSensor(sensor);

        when(networkStructure.containsSensor(sensor)).thenReturn(true);
        sensorRegistrationService.registerSensor(sensor);

        verify(sensorRepo,times(1)).save(sensor);
    }

    @Test
    public void it_should_unregisterSensor() throws Exception {
        sensorRegistrationService.registerSensor(sensor);

        when(networkStructure.containsSensor(sensor)).thenReturn(true);
        sensorRegistrationService.unregisterSensor(sensor);

        verify(networkStructure,times(1)).removeSensor(sensor);
    }

    @Test
    public void it_should_call_removeSensor_if_sensor_had_registered_before() throws Exception {
        sensorRegistrationService.unregisterSensor(new Sensor());

        verify(networkStructure,times(0)).removeSensor(any(Sensor.class));
    }
}