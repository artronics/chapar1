package com.artronics.chapar.controller.http.controllers;

import com.artronics.chapar.core.entities.Buffer;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/device/{id}/buffer")
public class PacketController {
    private final static Logger log = Logger.getLogger(PacketController.class);

    @RequestMapping(method = RequestMethod.POST)
    public void receivePacket(@PathVariable Long id, @RequestBody Buffer buffer){
        log.debug(buffer);
    }
}
