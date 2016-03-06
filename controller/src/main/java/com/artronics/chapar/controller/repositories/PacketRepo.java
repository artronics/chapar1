package com.artronics.chapar.controller.repositories;

import com.artronics.chapar.controller.entities.packet.Packet;
import com.artronics.chapar.controller.entities.packet.SdwnPacket;
import com.artronics.chapar.domain.entities.Client;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PacketRepo extends PagingAndSortingRepository<Packet,Long>{
    @Query(value = "select p from SdwnPacket p where " +
            "p.type = 'DATA' AND "+
            "p.buffer.client = :client " +
            "order by p.buffer.receivedAt, p.buffer.sentAt"
            ,nativeQuery = false)
    List<SdwnPacket> getDataPackets(@Param("client") Client client);

}
