package com.artronics.chapar.domain.repositories;

import com.artronics.chapar.domain.entities.Client;
import com.artronics.chapar.domain.entities.Sensor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SensorRepo extends PagingAndSortingRepository<Sensor,Long>{
//    @Query(value = "select s from Sensor s where " +
//            "s.address.client=:client")
//    public List<Sensor> getSensors(@Param("client") Client client);
}
