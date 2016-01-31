package com.artronics.chapar.domain.repositories;

import com.artronics.chapar.domain.entities.address.Address;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AddressRepo extends PagingAndSortingRepository<Address,Long>{

}
