package com.artronics.chapar.client;

import com.artronics.chapar.client.http.BaseHttpClient;
import com.artronics.chapar.domain.entities.Buffer;
import com.artronics.chapar.domain.entities.Client;
import com.artronics.chapar.domain.repositories.BufferRepo;
import com.artronics.chapar.domain.repositories.TimeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;

@Component
public class ClientTestExecutor {
    private static int seqNum = 0;
    private BaseHttpClient baseHttpClient;
    private Client regClient;

    private TimeRepo timeRepo;
    private BufferRepo bufferRepo;

    public void sendDataPacket() throws URISyntaxException, IOException {
        Buffer dataBuff = createDataBuff(regClient, 10, 30);
        dataBuff.setReceivedAt(timeRepo.getDbNowTime());

        bufferRepo.save(dataBuff);
    }

    private Buffer createDataBuff(Client client, int payloadSize, int dst) {
        assert payloadSize > 9;
        ArrayList<Integer> content = new ArrayList<>();
        for (int i = 0; i < payloadSize; i++) {
            content.add(i);
        }
        content.set(9, seqNum);
        seqNum++;

        int addH = (dst & 0x0000FF00) >> 8;
        int addL = dst & 0x000000FF;

        ArrayList<Integer> header = new ArrayList<>(Arrays.asList(
                payloadSize + 10,
                1,//NetId
                1,//src = 256
                0,
                addH,//dst
                addL,
                0,//data
                20,//ttl
                0, 0 //next hop
        ));

        ArrayList<Integer> pckContent = new ArrayList<>(header);
        pckContent.addAll(content);

        Buffer b = new Buffer(pckContent, Buffer.Direction.RX, client);

        return b;
    }

    public void setRegClient(Client regClient) {
        this.regClient = regClient;
    }

    @Autowired
    public void setTimeRepo(TimeRepo timeRepo) {
        this.timeRepo = timeRepo;
    }

    @Autowired
    public void setBufferRepo(BufferRepo bufferRepo) {
        this.bufferRepo = bufferRepo;
    }

}
