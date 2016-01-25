package com.artronics.chapar.controller.http.controllers;

import com.artronics.chapar.controller.services.DeviceRegistrationService;
import com.artronics.chapar.core.entities.Device;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/device/register")
public class DeviceRegistrationController {
    private final static Logger log = Logger.getLogger(DeviceRegistrationController.class);

    @Autowired
    private DeviceRegistrationService deviceRegistrationService;

    @RequestMapping(method = RequestMethod.POST,produces = "application/json")
    public Device registerDevice(@PathVariable Long sinkAddress,@RequestBody Device device){
        return deviceRegistrationService.registerDevice(device,sinkAddress);
    }

    @RequestMapping(method = RequestMethod.POST,produces = "application/json")
    public Device registerDevice(@RequestBody Device device){
        return deviceRegistrationService.registerDevice(device);
    }

}
