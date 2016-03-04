package com.artronics.chapar.client.test;

import com.artronics.chapar.domain.entities.Buffer;
import com.artronics.chapar.domain.entities.Client;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class BaseClientTestExecutor  implements ClientTestExecutor{
    private final static Logger log = Logger.getLogger(BaseClientTestExecutor.class);

    protected static int seqNum = 0;
    protected Client regClient;

    protected long delayBeforeStart=30000;
    protected long priodicity= 3000;

    protected abstract void sendDataPacket();

    protected Buffer createDataBuff(Client client, int payloadSize, int dst) {
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

    protected class TestExecutor implements Runnable{

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

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void setRegClient(Client regClient) {
        this.regClient = regClient;
    }

    @Override
    public void setDelayBeforeStart(long delayBeforeStart) {
        this.delayBeforeStart = delayBeforeStart;
    }

}