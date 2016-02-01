package com.artronics.chapar.controller.sdwn.controller;

import com.artronics.chapar.controller.entities.packet.Packet;
import com.artronics.chapar.controller.sdwn.helpers.FakeSdwnBufferFactory;
import com.artronics.chapar.controller.sdwn.packet.SdwnPacketType;
import com.artronics.chapar.controller.services.SensorRegistrationService;
import com.artronics.chapar.domain.entities.Buffer;
import com.artronics.chapar.domain.entities.Client;
import com.artronics.chapar.domain.entities.Sensor;
import com.artronics.chapar.domain.entities.SensorLink;
import com.artronics.chapar.domain.entities.address.UnicastAddress;
import com.artronics.chapar.domain.map.NetworkStructure;
import com.artronics.chapar.domain.repositories.SensorRepo;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.*;

public class ReportPacketProcessorTest {

    private ReportPacketProcessor processor;

    @Mock
    private SensorRepo sensorRepo;
    @Mock
    private SensorRegistrationService sensorRegistrationService;
    @Mock
    private NetworkStructure networkStructure;

    private Set<SensorLink> links;
    private Client client = new Client(1L);

    private Long srcLocalAdd = 1000l;
    private Long dstLocalAdd = 2000l;

    private UnicastAddress srcAdd;
    private UnicastAddress dstAdd;

    private Sensor srcSensor;
    private Sensor dstSensor;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        processor = new ReportPacketProcessor();
        processor.setWeightCalculator(new FixedWeightCalculator());
        processor.setSensorRepo(sensorRepo);
        processor.setSensorRegistrationService(sensorRegistrationService);
        processor.setNetworkStructure(networkStructure);

        srcAdd = UnicastAddress.create(client,srcLocalAdd);
        dstAdd = UnicastAddress.create(client,dstLocalAdd);

        srcSensor = Sensor.create(srcAdd);
        dstSensor = Sensor.create(dstAdd);
    }

    /*
        Update Source Sensor
     */

    @Test
    public void it_should_update_src_node() throws Exception {
        Packet packet = getPacket();

        processor.processReportPacket(packet);

        verify(sensorRepo,times(1)).save(eq(srcSensor));
        verifyNoMoreInteractions(sensorRepo);
    }

    @Test
    public void it_should_set_battery() throws Exception {
        Packet packet = getPacket();

        processor.processReportPacket(packet);
        Sensor cSensor = captureSensorRepoArg();

        assertThat(cSensor.getBattery(),is(notNullValue()));
    }

    @Test
    public void it_should_set_src_links() throws Exception {
        Packet packet = getPacket();

        processor.processReportPacket(packet);
        Sensor cSensor = captureSensorRepoArg();

        assertThat(cSensor.getLinks(),is(notNullValue()));
        assertThat(cSensor.getLinks().size(),is(equalTo(3)));
    }

    private Sensor captureSensorRepoArg(){
        ArgumentCaptor<Sensor> ac = ArgumentCaptor.forClass(Sensor.class);

        verify(sensorRepo).save(ac.capture());

        return ac.getValue();
    }

    /*
        Register Neighbors if necessary
     */

    @Test
    public void it_should_register_neighbors_if_necessary() throws Exception {
        Packet packet = getPacket();

        processor.processReportPacket(packet);
        when(networkStructure.containsSensor(any())).thenReturn(false);

        verify(sensorRegistrationService,times(3)).registerSensor(any());
    }

    /*
        CreateSensorLinks() test
     */

    @Test
    public void it_should_return_a_set_of_links(){
        Packet packet = getPacket();

        links = processor.createSensorLinks(packet);

        assertThat(links.size(),is(equalTo(3)));
    }

    @Test
    public void it_should_set_weight() throws Exception {
        Packet packet = getPacket();

        links = processor.createSensorLinks(packet);

        links.forEach(link -> {
            assertThat(link.getWeight(),is(equalTo(100.0)));
        });
    }

    @Test
    public void it_should_make_a_dst_node() throws Exception {
        Packet packet = getPacket();

        links = processor.createSensorLinks(packet);

        links.forEach(link -> {
            assertThat(link.getDstSensor(),is(notNullValue()));
        });
    }

    @Test
    public void it_should_add_srcNode_device_to_all_links_dst_nodes() throws Exception {
        Packet packet = getPacket();

        links = processor.createSensorLinks(packet);

        links.forEach(link -> {
            UnicastAddress address = link.getDstSensor().getAddress();

            assertThat(address,is(notNullValue()));
            assertThat(address.getLocalAddress(),is(notNullValue()));

            assertThat(address.getClient(),is(notNullValue()));
            assertThat(address.getClient(),is(equalTo(client)));
        });
    }

    private Packet getPacket() {
        List<Integer> content =
                FakeSdwnBufferFactory.createReportBuffer
                        (srcLocalAdd.intValue(),dstLocalAdd.intValue(),39,50,40).getContent();

        Packet packet = new Packet(new Buffer(content));
        packet.setType(SdwnPacketType.REPORT);

        packet.setSrcAddress(srcAdd);
        packet.setDstAddress(dstAdd);

        return packet;
    }
}

class FixedWeightCalculator implements WeightCalculator{
    @Override
    public Double calculate(Integer rssi) {
        return 100.0;
    }

}