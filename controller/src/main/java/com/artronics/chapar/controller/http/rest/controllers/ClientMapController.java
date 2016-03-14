package com.artronics.chapar.controller.http.rest.controllers;

import com.artronics.chapar.controller.http.rest.models.ClientMap;
import com.artronics.chapar.controller.http.rest.models.SensorLinkModel;
import com.artronics.chapar.controller.http.rest.models.SensorModel;
import com.artronics.chapar.domain.entities.Client;
import com.artronics.chapar.domain.entities.Sensor;
import com.artronics.chapar.domain.entities.SensorLink;
import com.artronics.chapar.domain.map.NetworkStructure;
import com.artronics.chapar.domain.map.NodeMap;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
public class ClientMapController {
    private final static Logger log = Logger.getLogger(ClientMapController.class);

    private Map<Client, Client> registeredClients;

    @Autowired
    private NodeMap nodeMap;
    @Autowired
    private NetworkStructure networkStructure;


    @RequestMapping(value = "/clients/map",method = RequestMethod.GET)
    @CrossOrigin(origins = "*")
    public List<ClientMap> getClientsMap(){
        List<ClientMap> maps = new ArrayList<>();

        for (Client client : registeredClients.keySet()) {
            List<SensorModel> sensorModels = new ArrayList<>();

            List<Sensor> sensors = networkStructure.getSensors(client);
            for (Sensor sensor : sensors) {
                List<SensorLinkModel> slm = new ArrayList<>();
                SensorModel s = new SensorModel();
                s.setLocalAdd(sensor.getAddress().getLocalAddress());
                s.setLinks(slm);

                Set<SensorLink> sl = networkStructure.getLinks(sensor);
                for (SensorLink sensorLink : sl) {
                    SensorLinkModel link = new SensorLinkModel();
                    link.setDstAdd(sensorLink.getDstSensor().getAddress().getLocalAddress());
                    link.setWeight(sensorLink.getWeight());
                    slm.add(link);
                }

                sensorModels.add(s);
            }

            ClientMap clientMap = new ClientMap();
            clientMap.setSensors(sensorModels);
            clientMap.setRid(client.getId());

            maps.add(clientMap);
        }

        return maps;
    }

    @Resource(name = "registeredClients")
    public void setRegisteredClients(Map<Client, Client> registeredClients) {
        this.registeredClients = registeredClients;
    }
}
