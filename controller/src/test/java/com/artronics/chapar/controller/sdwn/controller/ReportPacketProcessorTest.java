package com.artronics.chapar.controller.sdwn.controller;

import com.artronics.chapar.controller.entities.packet.Packet;
import com.artronics.chapar.controller.sdwn.helpers.FakeSdwnBufferFactory;
import com.artronics.chapar.domain.entities.Buffer;
import com.artronics.chapar.domain.entities.Client;
import com.artronics.chapar.domain.entities.SensorLink;
import com.artronics.chapar.domain.entities.address.UnicastAddress;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class ReportPacketProcessorTest {

    private ReportPacketProcessor processor;

    private Set<SensorLink> links;
    private Client client = new Client(1L);

    @Before
    public void setUp() throws Exception {
        processor = new ReportPacketProcessor();
        processor.setWeightCalculator(new FixedWeightCalculator());
    }

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
        List<Integer> content = FakeSdwnBufferFactory.createReportBuffer(30,10,39,50,40).getContent();
        Packet packet = new Packet(new Buffer(content));
        UnicastAddress a30 = UnicastAddress.create(client,30L);
        UnicastAddress a10 = UnicastAddress.create(client,10L);
        packet.setSrcAddress(a30);
        packet.setDstAddress(a10);
        return packet;
    }
}

class FixedWeightCalculator implements WeightCalculator{
    @Override
    public Double calculate(Integer rssi) {
        return 100.0;
    }

}