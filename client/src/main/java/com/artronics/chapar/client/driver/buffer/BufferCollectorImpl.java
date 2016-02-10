package com.artronics.chapar.client.driver.buffer;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Component
public class BufferCollectorImpl implements BufferCollector
{
    private final static Logger log = Logger.getLogger(BufferCollectorImpl.class);

    private Integer startByte;
    private Integer stopByte;

    private final LinkedList<Integer> dataQueue = new LinkedList<>();
    private final ArrayList<Integer> thisPacket = new ArrayList<>();
    private final List<List<Integer>> generatedPcks = new ArrayList<>();
    private int thisPacketExpectedSize = 0;
    private boolean isStarted = false;

    @Override
    public List<List<Integer>> generateLists(List<Integer> receivedData)
    {
        return createRawLists(receivedData);
    }

    protected List<List<Integer>> createRawLists(List<Integer> receivedData)
    {
        dataQueue.addAll(receivedData);
        while (!dataQueue.isEmpty()) {
            int thisData = dataQueue.removeFirst();

            if (thisData == startByte
                    && !isStarted
                    && thisPacketExpectedSize == 0) {
                isStarted = Boolean.TRUE;

            }else if (isStarted) {
                if (thisPacketExpectedSize == 0) {
                    thisPacket.add(thisData);
                    thisPacketExpectedSize = thisData;

                }else if (thisPacket.size() < thisPacketExpectedSize) {
                    thisPacket.add(thisData);

                }else if (thisData == stopByte
                        && thisPacket.size() == thisPacketExpectedSize) {
                    ArrayList<Integer> newPacket = new ArrayList<>(thisPacket);
                    generatedPcks.add(newPacket);
                    thisPacket.clear();
                    isStarted = Boolean.FALSE;
                    thisPacketExpectedSize = 0;
                }
            }
        }

        List<List<Integer>> tmp = new ArrayList<>(generatedPcks);
        generatedPcks.clear();

        return tmp;
    }

    @Override
    @Value("${com.artronics.chapar.client.buffer.startByte}")
    public void setStartByte(int startByte)
    {
        this.startByte = startByte;
    }

    @Override
    @Value("${com.artronics.chapar.client.buffer.stopByte}")
    public void setStopByte(int stopByte)
    {
        this.stopByte = stopByte;
    }

}
