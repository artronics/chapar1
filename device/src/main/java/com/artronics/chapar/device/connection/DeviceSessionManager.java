package com.artronics.chapar.device.connection;

import com.artronics.chapar.core.entities.Session;
import com.artronics.chapar.device.http.session.SessionManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component("deviceSessionManager")
public class DeviceSessionManager implements SessionManager{
    private final static Logger log = Logger.getLogger(DeviceSessionManager.class);

    private Session session;

    @Override
    public Session getSession() {
        return session;
    }

    @Override
    public void setSession(Session session) {
        this.session = session;
    }
}
