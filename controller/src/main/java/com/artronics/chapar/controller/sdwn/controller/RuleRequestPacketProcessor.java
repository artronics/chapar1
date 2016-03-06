package com.artronics.chapar.controller.sdwn.controller;

import com.artronics.chapar.controller.entities.packet.Packet;
import com.artronics.chapar.controller.sdwn.packet.SdwnPacketType;
import com.artronics.chapar.controller.sdwn.packet.SdwnPacketUtils;
import com.artronics.chapar.domain.entities.Buffer;
import com.artronics.chapar.domain.entities.Sensor;
import com.artronics.chapar.domain.entities.address.Address;
import com.artronics.chapar.domain.entities.address.UnicastAddress;
import com.artronics.chapar.domain.exceptions.RouteNotFound;
import com.artronics.chapar.domain.map.NetworkStructure;
import com.artronics.chapar.domain.repositories.BufferRepo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class RuleRequestPacketProcessor {
    private final static Logger log = Logger.getLogger(RuleRequestPacketProcessor.class);
    private static final int HEADER_LENGTH = 10;

    private NetworkStructure networkStructure;
    private BufferRepo bufferRepo;

    Packet<SdwnPacketType> processRuleRequestPacket(Packet<SdwnPacketType> packet){
        assert packet.getType()==SdwnPacketType.RL_REQ;

        //extract source and dst sensor for calculating shortest path
        Sensor src = Sensor.create(packet.getSrcAddress());
        //Rule request packet must have a dst address of unicast
        Address dstAdd = packet.getDstAddress();
        UnicastAddress uAdd = UnicastAddress.create(packet.getBuffer().getClient(),dstAdd.getLocalAddress());

        Sensor dst = Sensor.create(uAdd);

        Buffer openPathBuff=null;
        //first see if the source address is equal to node.
        if (packet.getSrcAddress().getLocalAddress()==0L){
            List<Sensor> path = new ArrayList<>();
            try {
                path = networkStructure.getShortestPath(src,dst);

            } catch (RouteNotFound routeNotFound) {
                routeNotFound.printStackTrace();
            }

            openPathBuff= createOpenPathBuffer(src.getAddress().getLocalAddress()
            ,dst.getAddress().getLocalAddress(),path);

        }
        else {
            List<Sensor> path = new ArrayList<>();
            try {
                path = networkStructure.getShortestPath(dst,src);

            } catch (RouteNotFound routeNotFound) {
                routeNotFound.printStackTrace();
            }
            openPathBuff= createOpenPathBuffer(dst.getAddress().getLocalAddress()
                    ,src.getAddress().getLocalAddress(),path);

        }

        openPathBuff.setDirection(Buffer.Direction.TX);
        openPathBuff.setClient(dst.getAddress().getClient());

        bufferRepo.save(openPathBuff);
        return packet;
    }

    public static Buffer createOpenPathBuffer(Long srcLocalAdd, Long dstLocalAdd, List<Sensor> path){
        int length = HEADER_LENGTH + 2 * path.size();

        int finalLength = path.size() * 2;

        List<Integer> addresses = new ArrayList<>();
        for (int i = 0; i < path.size(); i ++) {
            int lowAdd = SdwnPacketUtils.getLowAddress(
                    path.get(i).getAddress().getLocalAddress().intValue());
            int highAdd = SdwnPacketUtils.getHighAddress(
                    path.get(i).getAddress().getLocalAddress().intValue());

            addresses.add(highAdd);
            addresses.add(lowAdd);
        }

        List<Integer> h =new ArrayList<>(Arrays.asList(
                1,
               SdwnPacketUtils.getHighAddress(srcLocalAdd.intValue()),
               SdwnPacketUtils.getLowAddress(srcLocalAdd.intValue()),

               SdwnPacketUtils.getHighAddress(dstLocalAdd.intValue()),
               SdwnPacketUtils.getLowAddress(dstLocalAdd.intValue()),

                SdwnPacketType.OPN_PT.getValue(),
                20,
                0,0
        ));

        h.add(0,length);
        h.addAll(addresses);

        Buffer buffer = new Buffer(h);

        return buffer;
    }

    @Autowired
    public void setNetworkStructure(NetworkStructure networkStructure) {
        this.networkStructure = networkStructure;
    }

    @Autowired
    public void setBufferRepo(BufferRepo bufferRepo) {
        this.bufferRepo = bufferRepo;
    }

}
