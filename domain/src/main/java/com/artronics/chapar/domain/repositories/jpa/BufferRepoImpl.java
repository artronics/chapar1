package com.artronics.chapar.domain.repositories.jpa;

import com.artronics.chapar.domain.entities.Buffer;
import com.artronics.chapar.domain.entities.Client;
import com.artronics.chapar.domain.repositories.BufferCustomRepo;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class BufferRepoImpl implements BufferCustomRepo{
    private final static Logger log = Logger.getLogger(BufferRepoImpl.class);

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Buffer persist(Buffer buffer, Long clientId) {
        Client c = em.find(Client.class,clientId);

        buffer.setClient(c);

        em.persist(buffer);

        return buffer;
    }

}
