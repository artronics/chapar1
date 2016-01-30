package com.artronics.chapar.controller.http.controllers;

import com.artronics.chapar.controller.services.ClientRegistrationService;
import com.artronics.chapar.domain.entities.Client;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/client/register")
public class ClientRegistrationController {
    private final static Logger log = Logger.getLogger(ClientRegistrationController.class);

    @Autowired
    private ClientRegistrationService clientRegistrationService;

    @RequestMapping(method = RequestMethod.POST,produces = "application/json")
    public Client registerDevice(@RequestBody Client client){
        Client registeredClient = clientRegistrationService.registerDevice(client);

        return registeredClient;
    }

    @RequestMapping(method = RequestMethod.POST,produces = "application/json",params = {"sinkAddress"})
    public Client registerDevice(@RequestParam Long sinkAddress, @RequestBody Client client){
        Client registeredClient = clientRegistrationService.registerDevice(client, sinkAddress);
        return registeredClient;
    }

}
