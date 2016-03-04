package com.artronics.chapar.test;

import com.artronics.chapar.domain.entities.Buffer;
import com.artronics.chapar.domain.repositories.BufferRepo;
import com.artronics.chapar.domain.repositories.TimeRepo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("dBClientTestExecutor")
public class DBClientTestExecutor extends BaseClientTestExecutor {
    private final static Logger log = Logger.getLogger(DBClientTestExecutor.class);

    private TimeRepo timeRepo;
    private BufferRepo bufferRepo;

    @Override
    public void start(){
        Thread th = new Thread(new TestExecutor());
        th.start();
    }

    public void sendDataPacket() {
        Buffer dataBuff = createDataBuff(regClient, 10, 30);
        dataBuff.setReceivedAt(timeRepo.getDbNowTime());

        bufferRepo.save(dataBuff);
    }

    @Autowired
    public void setTimeRepo(TimeRepo timeRepo) {
        this.timeRepo = timeRepo;
    }

    @Autowired
    public void setBufferRepo(BufferRepo bufferRepo) {
        this.bufferRepo = bufferRepo;
    }

}
