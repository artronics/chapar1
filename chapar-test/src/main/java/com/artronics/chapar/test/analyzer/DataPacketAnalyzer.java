package com.artronics.chapar.test.analyzer;

import com.artronics.chapar.controller.entities.packet.SdwnPacket;
import com.artronics.chapar.controller.repositories.PacketRepo;
import com.artronics.chapar.domain.entities.Client;
import com.artronics.chapar.test.chart.XYDelayRRTChart;
import org.apache.log4j.Logger;
import org.jfree.ui.RefineryUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataPacketAnalyzer implements Analyzer{
    private final static Logger log = Logger.getLogger(DataPacketAnalyzer.class);

    private PacketRepo packetRepo;

    private long clientId;
    private Client client;

    private List<Long> rtt = new ArrayList<>();

    @Override
    public void start() {
        client = new Client(clientId);
        List<SdwnPacket> packets = packetRepo.getDataPackets(client);
        //remove the first one since there is no response for this data packet
        packets.remove(0);

//        packets.sort(new PacketComparator());
        printDataSet(packets);
        for (int i = 0; i < packets.size(); i+=2) {
            Long nxtTime = packets.get(i+1).getBuffer().getReceivedAt().getTime();
            Long time = packets.get(i).getBuffer().getSentAt().getTime();
            Long diff = nxtTime-time;
            rtt.add(diff);
            log.debug(diff);
        }

        XYDelayRRTChart chart = new XYDelayRRTChart("RTT Title",rtt);
        chart.pack();
        RefineryUtilities.centerFrameOnScreen( chart );
        chart.setVisible( true );

    }

    public void drawRTT(){
        List<SdwnPacket> packets = packetRepo.getDataPackets(new Client(clientId));
        printDataSet(packets);
    }

    private void printDataSet(List<SdwnPacket> packets){
        for (int i = 0; i < packets.size(); i++) {
            log.debug("id: " + packets.get(i).getId());
        }
    }

    public List<Long> getRtt() {
        return rtt;
    }

    @Autowired
    public void setPacketRepo(PacketRepo packetRepo) {
        this.packetRepo = packetRepo;
    }

    @Value("${com.artronics.chapar.test.client_id}")
    public void setClientId(long clientId) {
        this.clientId = clientId;
    }
}
