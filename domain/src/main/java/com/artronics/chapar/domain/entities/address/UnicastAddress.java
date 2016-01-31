package com.artronics.chapar.domain.entities.address;

import org.apache.log4j.Logger;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "uni_addresses")
public class UnicastAddress extends Address{
    private final static Logger log = Logger.getLogger(UnicastAddress.class);
}
