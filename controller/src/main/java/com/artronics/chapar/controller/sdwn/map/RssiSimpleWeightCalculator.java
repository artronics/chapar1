package com.artronics.chapar.controller.sdwn.map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class RssiSimpleWeightCalculator implements WeightCalculator{
    private final static Logger log = Logger.getLogger(RssiSimpleWeightCalculator.class);

    @Override
    public Double calculate(Integer rssi) {
        return 255-rssi.doubleValue();
    }
}
