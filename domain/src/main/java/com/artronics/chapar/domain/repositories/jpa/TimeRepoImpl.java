package com.artronics.chapar.domain.repositories.jpa;

import com.artronics.chapar.domain.repositories.TimeRepo;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;

@Repository
public class TimeRepoImpl implements TimeRepo{
    private final static Logger log = Logger.getLogger(TimeRepoImpl.class);

    @PersistenceContext
    private EntityManager em;

    @Override
    public Date getDbNowTime() {
        return null;
    }
}
