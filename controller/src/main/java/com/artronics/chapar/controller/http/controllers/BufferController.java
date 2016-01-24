package com.artronics.chapar.controller.http.controllers;

import com.artronics.chapar.controller.services.BufferService;
import com.artronics.chapar.core.entities.Buffer;
import com.artronics.chapar.core.exceptions.MalformedPacketException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/device/{id}")
public class BufferController {
    private final static Logger log = Logger.getLogger(BufferController.class);

    private BufferService bufferService;

    @RequestMapping(value = "/buffer",method = {RequestMethod.POST,RequestMethod.GET})
    public void receivePacket(@PathVariable Long id, @RequestBody Buffer buffer){
        try {
            bufferService.addBuffer(id,buffer);


        } catch (MalformedPacketException e) {
            e.printStackTrace();
        }
        log.debug(buffer);
    }

    @Autowired
    public void setBufferService(BufferService bufferService) {
        this.bufferService = bufferService;
    }

}
