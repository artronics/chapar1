package com.artronics.chapar.controller.repositories;

import com.artronics.chapar.controller.entities.Client;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface DeviceRepo extends PagingAndSortingRepository<Client,Long>{
//    Client persist(Client client);
}
