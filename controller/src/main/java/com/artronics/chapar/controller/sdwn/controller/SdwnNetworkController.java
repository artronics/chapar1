package com.artronics.chapar.controller.sdwn.controller;

import com.artronics.chapar.controller.sdwn.log.SdwnPacketLogger;
import com.artronics.chapar.controller.sdwn.map.SdwnNodeMapUpdater;
import com.artronics.chapar.controller.sdwn.packet.BaseSdwnPacket;
import com.artronics.chapar.controller.sdwn.packet.ReportPacket;
import com.artronics.chapar.controller.sdwn.packet.SdwnPacketType;
import com.artronics.chapar.controller.services.AddressRegistrationService;
import com.artronics.chapar.controller.services.NetworkController;
import com.artronics.chapar.controller.services.NodeRegistrationService;
import com.artronics.chapar.core.entities.Link;
import com.artronics.chapar.core.entities.Node;
import com.artronics.chapar.core.entities.Packet;
import com.artronics.chapar.core.exceptions.MalformedPacketException;
import com.artronics.chapar.core.exceptions.NodeNotRegistered;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Component("sdwnNetworkController")
public class SdwnNetworkController implements NetworkController<SdwnPacketType,BaseSdwnPacket>{
    private final static Logger log = Logger.getLogger(SdwnNetworkController.class);
    private final static SdwnPacketLogger LOGGER = new SdwnPacketLogger(SdwnNetworkController.class);

    private SdwnNodeMapUpdater sdwnNodeMapUpdater;

    private AddressRegistrationService addressRegistrationService;

    private NodeRegistrationService nodeRegistrationService;

    private Set<Node> islandedNodes = new HashSet<>();

    @PostConstruct
    public void initBean(){
        log.debug("Initializing SdwnController as NetworkController");
    }

    @Override
    public Packet processPacket(BaseSdwnPacket packet) throws MalformedPacketException, NodeNotRegistered {
        switch (packet.getType()){
            case REPORT:
                processReportPacket((ReportPacket) packet);
                return null;

            case MALFORMED:
                throw new MalformedPacketException();
        }

        throw new MalformedPacketException();
    }

    private void processReportPacket(ReportPacket packet) throws NodeNotRegistered {
        LOGGER.logReport(packet);
        Set<Link> links = sdwnNodeMapUpdater.createLinks(packet);

        log.debug("Registering Neighbors Addresses.");
        addressRegistrationService.registerNeighborsAddress(links);
        log.debug("Registering Neighbors Nodes.");
        nodeRegistrationService.registerNeighbors(links);

        //We are sure that the src node of packet is registered before
        Node srcNode = Node.create(packet.getSrcAddress());
        islandedNodes.clear();
        sdwnNodeMapUpdater.updateMap(srcNode,links,islandedNodes);
        if (!islandedNodes.isEmpty()){
            islandedNodes.forEach(node -> {
                log.debug("Find island "+ node);
                nodeRegistrationService.unregisterNode(node);
            });
        }
    }

    private static Node createSrcNode(Packet packet){
        return Node.create(packet.getSrcAddress());
    }

    @Autowired
    public void setAddressRegistrationService(AddressRegistrationService addressRegistrationService) {
        this.addressRegistrationService = addressRegistrationService;
    }

    @Autowired
    public void setNodeRegistrationService(NodeRegistrationService nodeRegistrationService) {
        this.nodeRegistrationService = nodeRegistrationService;
    }

    @Autowired
    public void setSdwnNodeMapUpdater(SdwnNodeMapUpdater sdwnNodeMapUpdater) {
        this.sdwnNodeMapUpdater = sdwnNodeMapUpdater;
    }
}
