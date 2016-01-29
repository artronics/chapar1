package com.artronics.chapar.controller.http.controllers;

import com.artronics.chapar.domain.model.Buffer;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/client/{id}")
public class PacketController {
    private final static Logger log = Logger.getLogger(PacketController.class);

    @RequestMapping(value = "/buffer",method = {RequestMethod.POST,RequestMethod.GET})
    public void receivePacket(@PathVariable int id, @RequestBody Buffer buffer){
        log.debug(buffer);
    }
}
