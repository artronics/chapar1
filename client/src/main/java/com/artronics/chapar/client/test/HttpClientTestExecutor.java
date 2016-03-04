package com.artronics.chapar.client.test;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component("httpClientTestExecutor")
public class HttpClientTestExecutor extends BaseClientTestExecutor{
    private final static Logger log = Logger.getLogger(HttpClientTestExecutor.class);

    @Override
    public void start() {

    }

    @Override
    protected void sendDataPacket() {

    }
}
