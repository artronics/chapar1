package com.artronics.chapar.domain.repositories.jpa;

import com.artronics.chapar.domain.repositories.TimeRepo;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.sql.Timestamp;
import java.util.Date;

@Repository
public class TimeRepoImpl implements TimeRepo{
    private final static Logger log = Logger.getLogger(TimeRepoImpl.class);

    @PersistenceContext
    private EntityManager em;

    @Override
    public Date getDbNowTime() {
        Query query = em.createNativeQuery("SELECT sysdate(6) as date");
        Timestamp dateEntity = (Timestamp) query.getSingleResult();

        return dateEntity;
    }
}
