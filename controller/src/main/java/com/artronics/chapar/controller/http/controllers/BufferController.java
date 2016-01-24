package com.artronics.chapar.controller.http.controllers;

import com.artronics.chapar.core.entities.Buffer;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/device/{id}")
public class BufferController {
    private final static Logger log = Logger.getLogger(BufferController.class);

    @RequestMapping(value = "/buffer",method = {RequestMethod.POST,RequestMethod.GET})
    public void receivePacket(@PathVariable int id, @RequestBody Buffer buffer){
        log.debug(buffer);
    }
}
