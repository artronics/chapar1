package com.artronics.chapar.controller.http.rest.controllers;

import com.artronics.chapar.controller.entities.packet.SdwnPacket;
import com.artronics.chapar.controller.http.rest.models.PacketModel;
import com.artronics.chapar.controller.repositories.PacketRepo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class PacketModelController {
    private final static Logger log = Logger.getLogger(PacketModelController.class);

    @Autowired
    PacketRepo packetRepo;

    @RequestMapping(value = "/packets",method = RequestMethod.GET)
    @CrossOrigin(origins = {"*"})
    public List<PacketModel> getAllPackets(){
        Pageable pageable = new PageRequest(0,10);
        List<SdwnPacket> packets = packetRepo.getAllPackets(pageable);
        List<PacketModel> packetModels=new ArrayList<>();

        for (SdwnPacket packet : packets) {
            PacketModel packetModel= new PacketModel();

            packetModel.setRid(packet.getId());
            packetModel.setType(packet.getType());
            packetModel.setSrcAdd(packet.getSrcAddress().getLocalAddress());
            packetModel.setDstAdd(packet.getDstAddress().getLocalAddress());
            packetModel.setDir(packet.getBuffer().getDirection());
            packetModel.setSentAt(packet.getBuffer().getSentAt());
            packetModel.setReceivedAt(packet.getBuffer().getReceivedAt());
            packetModel.setClientId(packet.getBuffer().getClient().getId());

            packetModels.add(packetModel);
        }

        return packetModels;
    }
}
