package com.artronics.chapar.test.analyzer;

import com.artronics.chapar.controller.entities.packet.SdwnPacket;
import com.artronics.chapar.controller.repositories.PacketRepo;
import com.artronics.chapar.domain.entities.Buffer;
import com.artronics.chapar.domain.entities.Client;
import com.artronics.chapar.domain.repositories.BufferRepo;
import com.artronics.chapar.test.chart.XYDelayRRTChart;
import org.apache.log4j.Logger;
import org.jfree.ui.RefineryUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataPacketAnalyzer implements Analyzer {
    private final static Logger log = Logger.getLogger(DataPacketAnalyzer.class);

    private PacketRepo packetRepo;
    private BufferRepo bufferRepo;

    private long clientId;
    private Client client;

    private List<Float> rttSet = new ArrayList<>();

    @Override
    public void start() {
        client = new Client(clientId);

//        getFromBuffers();
        getFromPackets();

        XYDelayRRTChart chart = new XYDelayRRTChart("30 Payload,one hop, db RTT", rttSet);
        chart.pack();
        RefineryUtilities.centerFrameOnScreen(chart);
        chart.setVisible(true);

    }

    private void getFromBuffers() {
        List<Buffer> rxBuffs = bufferRepo.getProcessedRxBuffs();
        int count = 0;
        float sum = 0;
        int total = rxBuffs.size();
        for (int i = 0; i < rxBuffs.size(); i++) {
            //filter data buffers
            if (rxBuffs.get(i).getContent().get(6) != 0) {
                total--;
                continue;
            }
            float start = 0;
            float stop = 0;
            start = ((rxBuffs.get(i).getContent().get(11) & 0xFF)
                    + (rxBuffs.get(i).getContent().get(12) & 0xFF) * 256);

            stop = ((rxBuffs.get(i).getContent().get(13) & 0xFF)
                    + (rxBuffs.get(i).getContent().get(14) & 0xFF) * 256);


            float rtt;
            if (start > stop) {
                rtt = (65535 - start + stop) / 250;
            } else {
                rtt = (stop - start) / 250;
            }

            rttSet.add(rtt);
            log.debug(rtt);
            sum += rtt;
        }
        log.debug("Total Data RX Packets: "+total);
        log.debug("RTT sum: "+sum);
        log.debug("Average RTT: "+sum / total);
    }

    private void getFromPackets() {
        List<SdwnPacket> packets = packetRepo.getDataPackets(client);
        int misPackets = 0;
        int overFlowMiss=0;
        float avg=0;
        int sum=0;
        int total=packets.size();
        log.debug(packets.size());
        for (int i = 0; i < packets.size(); i += 1) {
            if (packets.get(i).getBuffer().getDirection() == Buffer.Direction.RX) {
                total--;
                continue;
            }
            if (packets.get(i).getBuffer().getSentAt() == null) {
                total--;
                continue;
            }

            Long txTime = packets.get(i).getBuffer().getSentAt().getTime();
            Long rxTime = null;
            int seq = packets.get(i).getBuffer().getContent().get(19);
            //look for rx packet with in next 20 packets
            int distance= packets.size()-100>100?100:(packets.size()-100);
            for (int j = i + 1; j < i + distance; j++) {
                if (packets.get(j).getBuffer().getContent().get(19) == seq) {
                    rxTime = packets.get(j).getBuffer().getReceivedAt().getTime();
                    break;
                }
            }
            if (rxTime == null) {
                misPackets++;
                total--;
                continue;
            }

            Long diff = Math.abs(rxTime-txTime);
            rttSet.add(diff.floatValue());
            sum+=diff;
//            Long diff = rxTime - txTime;
//            if (diff > 0) {
//                rttSet.add(diff.floatValue());
//                sum+=diff;
//            }
//            else
//                overFlowMiss++;
        }
        avg = sum/total/2;
        log.debug("total number of packets in query: " +rttSet.size());
        log.debug("total number of Data Packets: "+ total);
        log.debug("Total number of Data packets divided by 2: "+total/2);
        log.debug("avg: "+avg);
    }

    private void printDataSet(List<SdwnPacket> packets) {
        for (int i = 0; i < packets.size(); i++) {
            log.debug("id: " + packets.get(i).getId());
        }
    }

    public List<Float> getRttSet() {
        return rttSet;
    }

    @Autowired
    public void setPacketRepo(PacketRepo packetRepo) {
        this.packetRepo = packetRepo;
    }

    @Autowired
    public void setBufferRepo(BufferRepo bufferRepo) {
        this.bufferRepo = bufferRepo;
    }

    @Value("${com.artronics.chapar.test.client_id}")
    public void setClientId(long clientId) {
        this.clientId = clientId;
    }
}
