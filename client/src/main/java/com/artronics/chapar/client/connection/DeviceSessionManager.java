package com.artronics.chapar.client.connection;

import com.artronics.chapar.client.http.session.SessionManager;
import com.artronics.chapar.domain.entities.Session;
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
