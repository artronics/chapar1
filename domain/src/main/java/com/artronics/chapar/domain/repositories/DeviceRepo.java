package com.artronics.chapar.domain.repositories;

import com.artronics.chapar.domain.entities.Client;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface DeviceRepo extends PagingAndSortingRepository<Client,Long>{
//    Client persist(Client client);
}
