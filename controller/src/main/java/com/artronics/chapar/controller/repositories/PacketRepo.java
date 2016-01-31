package com.artronics.chapar.controller.repositories;

import com.artronics.chapar.controller.entities.packet.Packet;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PacketRepo extends PagingAndSortingRepository<Packet,Long>{
}
