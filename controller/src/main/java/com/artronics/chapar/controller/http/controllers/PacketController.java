package com.artronics.chapar.controller.http.controllers;

import com.artronics.chapar.core.entities.Packet;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/device/{id}")
public class PacketController {
    private final static Logger log = Logger.getLogger(PacketController.class);

    @RequestMapping(value = "/packet",method = RequestMethod.POST)
    public void sendPacket(@PathVariable Long id,@RequestBody Packet packet){

    }
}
