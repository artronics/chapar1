package com.artronics.chapar.core.repositories;

import com.artronics.chapar.core.entities.Device;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface DeviceRepo extends PagingAndSortingRepository<Device,Long>{
//    Device persist(Device device);
}
