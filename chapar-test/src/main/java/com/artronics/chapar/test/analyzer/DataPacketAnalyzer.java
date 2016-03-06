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

        getFromBuffers();

        XYDelayRRTChart chart = new XYDelayRRTChart("RTT Title", rttSet);
        chart.pack();
        RefineryUtilities.centerFrameOnScreen(chart);
        chart.setVisible(true);

    }

    private void getFromBuffers() {
        List<Buffer> rxBuffs = bufferRepo.getProcessedRxBuffs();
        for (int i = 0; i < rxBuffs.size(); i++) {
            //filter data buffers
            if (rxBuffs.get(i).getContent().get(6) != 0)
                continue;
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
        }
    }

    private void getFromPackets() {
        List<SdwnPacket> packets = packetRepo.getDataPackets(client);
        //remove the first one since there is no response for this data packet
        packets.remove(0);

//        packets.sort(new PacketComparator());
        printDataSet(packets);
        for (int i = 0; i < 502; i += 2) {
            if (packets.get(i).getBuffer().getSentAt() != null &&
                    packets.get(i + 1).getBuffer().getSentAt() != null) {
                packets.remove(i);
                continue;
            }
            Long nxtTime = packets.get(i + 1).getBuffer().getReceivedAt().getTime();
            Long time = packets.get(i).getBuffer().getSentAt().getTime();
            Long diff = nxtTime - time;
//            rttSet.add(diff);
            log.debug(diff);
        }
    }

    public void drawRTT() {
        List<SdwnPacket> packets = packetRepo.getDataPackets(new Client(clientId));
        printDataSet(packets);
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
