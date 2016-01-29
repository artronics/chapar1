package com.artronics.chapar.controller.http.controllers;

import com.artronics.chapar.controller.services.DeviceRegistrationService;
import com.artronics.chapar.domain.entities.Client;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/device/register")
public class DeviceRegistrationController {
    private final static Logger log = Logger.getLogger(DeviceRegistrationController.class);

    @Autowired
    private DeviceRegistrationService deviceRegistrationService;

    @RequestMapping(method = RequestMethod.POST,produces = "application/json")
    public Client registerDevice(@RequestBody Client client){
        return deviceRegistrationService.registerDevice(client);
    }

}
