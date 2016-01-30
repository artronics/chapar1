package com.artronics.chapar.domain.repositories;

import com.artronics.chapar.domain.entities.Buffer;
import com.artronics.chapar.domain.entities.Client;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BufferRepo extends
        PagingAndSortingRepository<Buffer,Long> ,BufferCustomRepo
{
    @Query(value = "SELECT b from Buffer b where " +
            "b.direction='TX' and " +
            "b.sentAt= null and " +
            "b.client= :client")
    List<Buffer> getReadyTxBuffers(@Param("client") Client client);
}
