package com.artronics.chapar.controller.http.controllers;

import com.artronics.chapar.controller.entities.packet.Packet;
import com.artronics.chapar.controller.exceptions.MalformedPacketException;
import com.artronics.chapar.controller.services.PacketService;
import com.artronics.chapar.domain.entities.Buffer;
import com.artronics.chapar.domain.entities.Client;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

@RestController
@RequestMapping("/client/{id}")
public class PacketController {
    private final static Logger log = Logger.getLogger(PacketController.class);

    private PacketService packetService;
    private Map<Client,BlockingQueue<Buffer>> bufferQueues;

    @RequestMapping(value = "/buffer",method = {RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public Buffer receivePacket(@PathVariable Long id, @RequestBody Buffer buffer){
        log.debug("receive packet from http request.");
        Client client = new Client(id);
        buffer.setClient(client);
        log.debug("Request:  "+Buffer.soutBuffer(buffer));

        Buffer response = new Buffer();
        response=packetService.receiveBufferAndGetResponse(buffer);
        if (response != null) {
            log.debug("Response: "+Buffer.soutBuffer(response));
        }

        return response;
    }

//    @RequestMapping(value = "/packet",method = {RequestMethod.POST})
    public Packet receivePacket(@PathVariable long id, @RequestBody Packet packet){
        log.debug("receive packet from http request.");
        try {
            packetService.receivePacket(packet);

        } catch (MalformedPacketException e) {
            e.printStackTrace();
        }

        return packet;
    }

    @Resource(name = "bufferQueues")
    public void setBufferQueues(Map<Client, BlockingQueue<Buffer>> bufferQueues) {
        this.bufferQueues = bufferQueues;
    }

    @Autowired
    public void setPacketService(PacketService packetService) {
        this.packetService = packetService;
    }

}
