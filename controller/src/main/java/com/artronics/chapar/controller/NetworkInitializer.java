package com.artronics.chapar.controller;

import com.artronics.chapar.domain.entities.Controller;
import com.artronics.chapar.domain.map.NetworkStructure;
import com.artronics.chapar.domain.repositories.ControllerRepo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class NetworkInitializer implements ApplicationListener<ContextRefreshedEvent>{
    private final static Logger log = Logger.getLogger(NetworkInitializer.class);

    private ControllerRepo controllerRepo;

    private NetworkStructure networkStructure;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.debug("Initializing Chapar's Controller Application");
        Controller controller = new Controller();
        controllerRepo.save(controller);

        networkStructure.addController(controller);
    }

    @Autowired
    public void setControllerRepo(ControllerRepo controllerRepo) {
        this.controllerRepo = controllerRepo;
    }

}