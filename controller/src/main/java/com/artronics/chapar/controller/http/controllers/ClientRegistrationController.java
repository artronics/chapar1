package com.artronics.chapar.controller.http.controllers;

import com.artronics.chapar.controller.entities.Client;
import com.artronics.chapar.controller.services.ClientRegistrationService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/client/register")
public class ClientRegistrationController {
    private final static Logger log = Logger.getLogger(ClientRegistrationController.class);

    @Autowired
    private ClientRegistrationService clientRegistrationService;

    @RequestMapping(method = RequestMethod.POST,produces = "application/json")
    public Client registerDevice(@RequestBody Client client){
        return clientRegistrationService.registerDevice(client);
    }

}
