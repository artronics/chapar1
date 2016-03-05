package com.artronics.chapar.test.test;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class ChaparTestInitializer implements ApplicationListener<ContextRefreshedEvent>{
    private final static Logger log = Logger.getLogger(ChaparTestInitializer.class);
    private ClientTestExecutor clientTestExecutor;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        log.debug("start test application");
        clientTestExecutor.start();
    }

    @Autowired
    @Qualifier("httpClientTestExecutor")
    public void setClientTestExecutor(ClientTestExecutor clientTestExecutor) {
        this.clientTestExecutor = clientTestExecutor;
    }

}
