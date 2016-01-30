package com.artronics.chapar.domain.repositories;

import com.artronics.chapar.domain.entities.Buffer;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BufferRepo extends
        PagingAndSortingRepository<Buffer,Long> ,BufferCustomRepo
{
}
