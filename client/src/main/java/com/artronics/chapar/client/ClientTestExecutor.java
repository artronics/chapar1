package com.artronics.chapar.client;

import com.artronics.chapar.domain.entities.Buffer;
import com.artronics.chapar.domain.entities.Client;
import com.artronics.chapar.domain.repositories.BufferRepo;
import com.artronics.chapar.domain.repositories.TimeRepo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;

@Component
public class ClientTestExecutor {
    private final static Logger log = Logger.getLogger(ClientTestExecutor.class);

    private static int seqNum = 0;
    private Client regClient;

    private long delayBeforeStart=30000;
    private long priodicity= 3000;

    private TimeRepo timeRepo;
    private BufferRepo bufferRepo;

    public void start(){
        Thread th = new Thread(new TestExecutor());
        th.start();
    }

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

    public void setDelayBeforeStart(long delayBeforeStart) {
        this.delayBeforeStart = delayBeforeStart;
    }

    @Autowired
    public void setTimeRepo(TimeRepo timeRepo) {
        this.timeRepo = timeRepo;
    }

    @Autowired
    public void setBufferRepo(BufferRepo bufferRepo) {
        this.bufferRepo = bufferRepo;
    }

    private class TestExecutor implements Runnable{

        @Override
        public void run() {
            try {
                Thread.sleep(delayBeforeStart);

                log.debug("start data buffer sender.");
                while (true){
                    log.debug("sending data buffer");
                    sendDataPacket();
                    Thread.sleep(priodicity);
                }

            } catch (InterruptedException | URISyntaxException | IOException e) {
                e.printStackTrace();
            }

        }
    }

}
