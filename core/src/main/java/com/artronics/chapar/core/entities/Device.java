package com.artronics.chapar.core.entities;

import org.apache.log4j.Logger;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "devices")
public class Device extends AbstractBaseEntity{
    private final static Logger log = Logger.getLogger(Device.class);

}
