package com.artronics.chapar.controller.repositories;

import com.artronics.chapar.domain.entities.Client;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ClientRepo extends PagingAndSortingRepository<Client,Long>{
}
