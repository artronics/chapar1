package com.artronics.chapar.client.repositories.jpa;

import com.artronics.chapar.client.repositories.ClientBufferCustomRepo;
import com.artronics.chapar.domain.entities.Buffer;
import com.artronics.chapar.domain.entities.Client;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

//@Repository
public class ClientBufferRepoJpa implements ClientBufferCustomRepo{
    private final static Logger log = Logger.getLogger(ClientBufferRepoJpa.class);

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Buffer persist(Buffer buffer, Client client) {

        throw new NotImplementedException();
    }
}
