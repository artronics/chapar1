package com.artronics.chapar.controller.entities;

import org.apache.log4j.Logger;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "packets")
public class Packet extends AbstractBaseEntity{
    private final static Logger log = Logger.getLogger(Packet.class);
}
