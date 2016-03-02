package com.artronics.chapar.controller.http.controllers;

import com.artronics.chapar.controller.entities.packet.Packet;
import com.artronics.chapar.controller.exceptions.MalformedPacketException;
import com.artronics.chapar.controller.services.PacketService;
import com.artronics.chapar.domain.entities.Buffer;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/client/{id}")
public class PacketController {
    private final static Logger log = Logger.getLogger(PacketController.class);

    private PacketService packetService;

    @RequestMapping(value = "/buffer",method = {RequestMethod.POST,RequestMethod.GET})
    public void receivePacket(@PathVariable int id, @RequestBody Buffer buffer){
        log.debug(buffer);
    }

    @RequestMapping(value = "/packet",method = {RequestMethod.POST})
    public Packet receivePacket(@PathVariable long id, @RequestBody Packet packet){
        log.debug("receive packet from http request.");
        try {
            packetService.receivePacket(packet);

        } catch (MalformedPacketException e) {
            e.printStackTrace();
        }

        return packet;
    }

    @Autowired
    public void setPacketService(PacketService packetService) {
        this.packetService = packetService;
    }

}
