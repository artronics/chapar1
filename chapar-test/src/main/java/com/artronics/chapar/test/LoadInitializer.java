//package com.artronics.chapar.test;
//
//import com.artronics.chapar.controller.entities.packet.DataPacket;
//import com.artronics.chapar.controller.entities.packet.SdwnPacketType;
//import com.artronics.chapar.domain.entities.Address;
//import Client;
//import com.artronics.chapar.client.connection.ControllerResolverHttp;
//import com.artronics.chapar.test.factory.BufferFactory;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.apache.http.HttpEntity;
//import org.apache.http.client.methods.CloseableHttpResponse;
//import org.apache.http.util.EntityUtils;
//import org.apache.log4j.Logger;
//import org.springframework.context.ApplicationListener;
//import org.springframework.context.event.ContextRefreshedEvent;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//import java.net.URISyntaxException;
//import java.util.ArrayList;
//import java.util.List;
//
//@Component
//public class LoadInitializer extends ControllerResolverHttp
//        implements ApplicationListener<ContextRefreshedEvent> {
//    private final static Logger log = Logger.getLogger(LoadInitializer.class);
//    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
//
//    private final static Long DST_DEV = 94L;
//
//    private final static Long SINK_ADDRESS = 0L;
//    private final static Long DST_ADD = 50L;
//    private final static int NET_ID = 1;
//
//    private static int seq =1;
//
//    private Client client;
//
//    private BufferFactory bufferFactory;
//
//    public LoadInitializer() {
//        client = new Client();
//
//        bufferFactory = new BufferFactory();
//    }
//
//    @Override
//    public void onApplicationEvent(ContextRefreshedEvent event) {
//        serverCall();
//    }
//
//    private void serverCall(){
//        Client dev = new Client();
//        try {
//            log.debug("Reg dev");
//
//            String jDevice = toJson(dev);
//            CloseableHttpResponse r = httpClient.registerDevice(jDevice, SINK_ADDRESS);
//            this.client = (Client) toObject(r,Client.class);
//            log.debug(client);
//
//            if (client.getId() != null) {
//                log.debug("Client registered: "+ client);
//                r = sendDataPacket();
//                r.close();
//            }
//
//            Thread t = new Thread(new PacketSender());
//            t.start();
//
//        } catch (IOException | URISyntaxException e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    private CloseableHttpResponse sendDataPacket() throws URISyntaxException, IOException {
//        Address dst = Address.create(new Client(DST_DEV),DST_ADD);
//        DataPacket p = createDataPacket(dst.getLocalAdd().intValue(),10);
//
//        String j = OBJECT_MAPPER.writeValueAsString(p);
//        log.debug(j);
//        CloseableHttpResponse r =
//                httpClient.sendRequest(j,DST_DEV,"packet");
//
//        return r;
//    }
//
//    private DataPacket createDataPacket(int dst,int payloadLen){
//        List<Integer> h = bufferFactory.createHeader(0,dst, SdwnPacketType.DATA);
//
//        List<Integer> pl = new ArrayList<>();
//        for (int i = 0; i < payloadLen; i++) {
//            pl.add(i+seq);
//        }
//        //add seq number
//        pl.set(9,seq);
//        seq++;
//        if (seq==255){
//            seq=0;
//        }
//
//        h.addAll(pl);
//        h.set(0,h.size());
//
//        DataPacket p = DataPacket.create(h);
//        p.setSrcAddress(null);
//
//        return p;
//    }
//
//    class PacketSender implements Runnable{
//
//        @Override
//        public void run() {
//            while (true){
//                try {
//                    CloseableHttpResponse r = sendDataPacket();
//                    HttpEntity e =r.getEntity();
////                    log.debug(EntityUtils.toString(e));
//                    EntityUtils.consume(e);
//                    r.close();
//
//                    Thread.sleep(5000);
//
//                } catch (URISyntaxException | InterruptedException | IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//
//}
