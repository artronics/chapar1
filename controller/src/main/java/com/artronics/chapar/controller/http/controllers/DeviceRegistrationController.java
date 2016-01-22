package com.artronics.chapar.controller.http.controllers;

import com.artronics.chapar.core.entities.Device;
import com.artronics.chapar.core.repositories.DeviceRepo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/device/register")
public class DeviceRegistrationController {
    private final static Logger log = Logger.getLogger(DeviceRegistrationController.class);

    @Autowired
    DeviceRepo deviceRepo;

    @RequestMapping(method = RequestMethod.GET,produces = "application/json")
    public Device registerDevice(@PathVariable Long sinkAddress){
        return deviceRepo.save(new Device());
    }

}
