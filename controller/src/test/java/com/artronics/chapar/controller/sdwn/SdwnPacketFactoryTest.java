package com.artronics.chapar.controller.sdwn;

import com.artronics.chapar.controller.factories.PacketFactory;
import com.artronics.chapar.controller.sdwn.packet.ReportPacket;
import com.artronics.chapar.controller.sdwn.packet.SdwnPacketType;
import com.artronics.chapar.core.entities.Packet;
import com.artronics.chapar.core.exceptions.MalformedPacketException;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class SdwnPacketFactoryTest {
    private PacketFactory factory = new SdwnPacketFactory();

    @Test
    public void it_should_create_a_ReportPacket() throws MalformedPacketException {
        List<Integer> b = new ArrayList<>(Arrays.asList(1,2,3,4,5,6,2));
        Packet packet = new Packet(b);
        Packet actPacket =factory.create(packet);
        assertThat(actPacket,is(instanceOf(ReportPacket.class)));
        assertThat(actPacket.getType(),is(equalTo(SdwnPacketType.REPORT)));
    }
}