package com.artronics.chapar.controller.sdwn.controller;

import com.artronics.chapar.controller.entities.packet.Packet;
import com.artronics.chapar.controller.sdwn.helpers.FakeSdwnBufferFactory;
import com.artronics.chapar.controller.sdwn.packet.SdwnPacketType;
import com.artronics.chapar.controller.services.AddressRegistrationService;
import com.artronics.chapar.controller.services.SensorRegistrationService;
import com.artronics.chapar.domain.entities.Buffer;
import com.artronics.chapar.domain.entities.Client;
import com.artronics.chapar.domain.entities.Sensor;
import com.artronics.chapar.domain.entities.SensorLink;
import com.artronics.chapar.domain.entities.address.UnicastAddress;
import com.artronics.chapar.domain.map.NetworkStructure;
import com.artronics.chapar.domain.repositories.SensorRepo;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.*;

public class ReportPacketProcessorTest {

    @InjectMocks
    private ReportPacketProcessor processor;

    @Mock
    private SensorRepo sensorRepo;
    @Mock
    private SensorRegistrationService sensorRegistrationService;
    @Mock
    private AddressRegistrationService addressRegistrationService;
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

        processor.setWeightCalculator(new FixedWeightCalculator());

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

    //srcNode has a list of NodeLink. for each link there is a dstNode
    //During persistence of srcNode these links should contains a dstNode
    //which must be persisted before.
    @Test
    public void it_should_set_links_of_src_node_with_registered_dstNode() throws Exception {
        Packet packet = getPacket();

        when(networkStructure.containsSensor(any(Sensor.class))).thenReturn(false);

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                ((Sensor)args[0]).setId(12345L);
                return args[0];
            }
        }).
        when(sensorRegistrationService).registerSensor(any(Sensor.class));

        processor.processReportPacket(packet);
        Sensor cSensor = captureSensorRepoArg();

        cSensor.getLinks().forEach(l->
                assertThat(l.getDstSensor().getId(),is(notNullValue()))
        );
    }

    @Ignore("fix it")
    @Test
    public void it_should_set_links_of_src_node_with_registered_dstNode_address() throws Exception {
        Packet packet = getPacket();

        when(networkStructure.containsSensor(any(Sensor.class))).thenReturn(false);

        UnicastAddress address = UnicastAddress.create(client, 123456L);
        address.setId(1200L);
        when(sensorRegistrationService.registerSensor(any(Sensor.class))).thenReturn(Sensor.create(address));

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();

                return UnicastAddress.create((Client)args[1],(Long)args[0]);
            }
        }).
                when(addressRegistrationService).registerUnicastAddress(anyLong(),eq(client));

        processor.processReportPacket(packet);
        Sensor cSensor = captureSensorRepoArg();

        cSensor.getLinks().forEach(l->
                assertThat(l.getDstSensor().getAddress().getId(),is(notNullValue()))
        );
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