package com.artronics.chapar.domain.repositories;

import com.artronics.chapar.domain.entities.Sensor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SensorRepo extends PagingAndSortingRepository<Sensor,Long>{
}
