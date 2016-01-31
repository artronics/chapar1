package com.artronics.chapar.controller.sdwn.packet;

import com.artronics.chapar.controller.entities.packet.Packet;
import com.artronics.chapar.controller.entities.packet.PacketFactory;
import com.artronics.chapar.controller.sdwn.helpers.FakeSdwnBufferFactory;
import com.artronics.chapar.domain.entities.Buffer;
import org.junit.Before;
import org.junit.Test;

import static com.artronics.chapar.controller.sdwn.packet.SdwnPacketType.REPORT;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class SdwnPacketFactoryTest {
    private FakeSdwnBufferFactory fakeBuffFactory = new FakeSdwnBufferFactory();

    private PacketFactory packetFactory;

    private Packet packet;

    @Before
    public void setUp() throws Exception {
        packetFactory = new SdwnPacketFactory();
    }

    @Test
    public void it_should_specify_the_type_of_packet() throws Exception {
        Buffer b = FakeSdwnBufferFactory.createReportBuffer(0,10);

        packet = packetFactory.create(b);

        assertThat(packet.getType(),is(equalTo(REPORT)));
    }

    @Test
    public void it_should_create_localSrcAddress() throws Exception {
        Buffer b = FakeSdwnBufferFactory.createReportBuffer(1708,10);

        packet = packetFactory.create(b);

        assertThat(packet.getSrcLocalAddress(),is(equalTo(1708L)));
    }

    @Test
    public void it_should_create_localDstAddress() throws Exception {
        Buffer b = FakeSdwnBufferFactory.createReportBuffer(1,745);

        packet = packetFactory.create(b);

        assertThat(packet.getDstLocalAddress(),is(equalTo(745L)));
    }

}