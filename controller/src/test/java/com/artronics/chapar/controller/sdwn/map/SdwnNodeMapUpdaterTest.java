package com.artronics.chapar.controller.sdwn.map;

import com.artronics.chapar.controller.sdwn.helpers.FakeSdwnBufferFactory;
import com.artronics.chapar.controller.sdwn.packet.ReportPacket;
import com.artronics.chapar.core.entities.Address;
import com.artronics.chapar.core.entities.Device;
import com.artronics.chapar.core.entities.Link;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class SdwnNodeMapUpdaterTest {

    private SdwnNodeMapUpdater mapUpdater;

    private WeightCalculator weightCalculator;

    private Set<Link> links;
    private Device device = new Device(1L);

    @Before
    public void setUp() throws Exception {
        mapUpdater = new SdwnNodeMapUpdater();
        mapUpdater.setWeightCalculator(new FixedWeightCalculator());
    }

    @Test
    public void it_should_return_a_set_of_links(){
        ReportPacket packet = getReportPacket();

        links = mapUpdater.createLinks(packet);

        assertThat(links.size(),is(equalTo(3)));
    }

    @Test
    public void it_should_set_weight() throws Exception {
        ReportPacket packet = getReportPacket();

        links = mapUpdater.createLinks(packet);

        links.forEach(link -> {
            assertThat(link.getWeight(),is(equalTo(100.0)));
        });
    }

    @Test
    public void it_should_make_a_dst_node() throws Exception {
        ReportPacket packet = getReportPacket();

        links = mapUpdater.createLinks(packet);

        links.forEach(link -> {
            assertThat(link.getDstNode(),is(notNullValue()));
        });
    }

    @Test
    public void it_should_add_srcNode_device_to_all_links_dst_nodes() throws Exception {
        ReportPacket packet = getReportPacket();

        links = mapUpdater.createLinks(packet);

        links.forEach(link -> {
            Address address = link.getDstNode().getAddress();

            assertThat(address,is(notNullValue()));
            assertThat(address.getLocalAdd(),is(notNullValue()));

            assertThat(address.getDevice(),is(notNullValue()));
            assertThat(address.getDevice(),is(equalTo(device)));
        });
    }

    private ReportPacket getReportPacket() {
        List<Integer> content = FakeSdwnBufferFactory.createReportBuffer().getContent();
        ReportPacket packet = ReportPacket.create(content);
        Address a30 = Address.create(device,30L);
        Address a10 = Address.create(device,10L);
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