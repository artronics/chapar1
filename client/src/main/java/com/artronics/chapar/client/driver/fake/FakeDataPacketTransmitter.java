package com.artronics.chapar.client.driver.fake;

import com.artronics.chapar.client.services.ClientBufferService;
import com.artronics.chapar.domain.entities.Buffer;
import com.artronics.chapar.domain.repositories.TimeRepo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;

@Component
public class FakeDataPacketTransmitter {
    private final static Logger log = Logger.getLogger(FakeDataPacketTransmitter.class);
    private boolean transmitterIsEnabled = false;
    protected static int seqNum = 0;

    private ClientBufferService clientBufferService;

    private TimeRepo timeRepo;


    @Scheduled(fixedRateString = "${com.artronics.chapar.client.buffer.scheduler.availability.rate}",initialDelay = 15000)
    public void sendNextData() {
        if (transmitterIsEnabled) {
            Buffer dataBuff = createDataBuff(10, 0, 30);

            clientBufferService.sendBufferToController
                    (dataBuff, Buffer.Direction.TX);
        }
    }

    protected Buffer createDataBuff(int payloadSize,int src, int dst) {
        assert payloadSize > 9;
        ArrayList<Integer> content = new ArrayList<>();
        for (int i = 0; i < payloadSize; i++) {
            content.add(i);
        }
        content.set(9, seqNum);
        seqNum++;

        int addH_src = (src & 0x0000FF00) >> 8;
        int addL_src = src & 0x000000FF;

        int addH = (dst & 0x0000FF00) >> 8;
        int addL = dst & 0x000000FF;

        ArrayList<Integer> header = new ArrayList<>(Arrays.asList(
                payloadSize + 10,
                1,//NetId
                addH_src,
                addL_src,
                addH,//dst
                addL,
                0,//data
                20,//ttl
                0, 0 //next hop
        ));

        ArrayList<Integer> pckContent = new ArrayList<>(header);
        pckContent.addAll(content);

        Buffer b = new Buffer(pckContent);

        return b;
    }

    @Value("${com.artronics.chapar.client.test.dataTransmitter}")
    public void setTransmitterIsEnabled(boolean transmitterIsEnabled) {
        this.transmitterIsEnabled = transmitterIsEnabled;
    }

    @Autowired
    public void setTimeRepo(TimeRepo timeRepo) {
        this.timeRepo = timeRepo;
    }

    @Autowired
    public void setClientBufferService(ClientBufferService clientBufferService) {
        this.clientBufferService = clientBufferService;
    }

}
