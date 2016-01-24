package com.artronics.chapar.controller.sdwn.packet;

import com.artronics.chapar.controller.helpers.FakeBufferFactory;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

public class PacketUtilsTest {

    private List<Integer> aBuff;
    private List<Integer> malformedPacket = new ArrayList<>();
    private FakeBufferFactory factory = new FakeBufferFactory();

    @Before
    public void setUp()
    {
        aBuff = FakeBufferFactory.createABuffer();
    }

    @Ignore("Validation for packet is deprecated")
    @Test
    public void Test_validation()
    {
        boolean isvalid = PacketUtils.validate(aBuff);
        assertTrue(isvalid);

        malformedPacket.add(2);//current validation just check the length(there is no more rule)
        assertFalse(PacketUtils.validate(malformedPacket));
    }

    @Test
    public void test_get_length()
    {
        assertEquals(aBuff.size(), PacketUtils.getLength(aBuff));
    }

    @Test
    public void Test_getType()
    {
        SdwnPacketType actType = SdwnPacketType.DATA;

        assertEquals(PacketUtils.getType(aBuff),actType);
    }

    @Test
    public void test_getPayload(){
        List<Integer> payload =PacketUtils.getPayload(aBuff);
        assertNotNull(payload);
        assertThat(payload.size(),equalTo(10));
        assertEquals(FakeBufferFactory.createPayload(10), payload);
    }

    @Test
    public void Test_getIntAddress_it_gets_two_int_and_returns_int()
    {
        int l = 10;
        int h = 0;
        int actMixed = PacketUtils.getIntAddress(l, h);
        int exp = 10;
        assertEquals(exp, actMixed);

        l = 0;
        exp = 0;
        actMixed = PacketUtils.getIntAddress(l, h);
        assertEquals(exp, actMixed);

        l = 4;
        h = 1;
        exp = 260; //256 +4
        actMixed = PacketUtils.getIntAddress(l, h);
        assertEquals(exp, actMixed);
    }

    @Test
    public void Test_splitAddress()
    {
        int[] exp = new int[]{0, 0};
        int[] act;
        act = PacketUtils.splitAddress(0);
        assertArrayEquals(exp, act);

        exp[0] = 1;
        exp[1] = 4;
        act = PacketUtils.splitAddress(260);
        assertArrayEquals(exp, act);

        exp[0] = 0;
        exp[1] = 30;
        act = PacketUtils.splitAddress(30);
        assertArrayEquals(exp, act);
    }

    @Test
    public void Test_joinAddresses()
    {
        int exp = 0;
        int act = PacketUtils.joinAddresses(0, 0);
        assertEquals(exp, act);

        exp = 30;
        act = PacketUtils.joinAddresses(0, 30);
        assertEquals(exp, act);

        exp = 260;
        act = PacketUtils.joinAddresses(1, 4);
        assertEquals(exp, act);
    }
}