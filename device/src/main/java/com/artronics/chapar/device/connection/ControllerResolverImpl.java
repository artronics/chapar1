package com.artronics.chapar.device.connection;

import com.artronics.chapar.core.entities.Session;
import com.artronics.chapar.core.remote.RemoteControllerService;
import com.artronics.chapar.core.remote.SessionManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class ControllerResolverImpl implements ControllerResolver{
    private final static Logger log = Logger.getLogger(ControllerResolverImpl.class);

    private RemoteControllerService remoteControllerService;

    private SessionManager sessionManager;

    @Override
    public void connect(String url) {
        log.debug("Connecting to controller");
        Session session=remoteControllerService.connect(url);
        log.debug("Set current session: " + session);
        sessionManager.setSession(session);
    }

    @Autowired
    @Qualifier("hessianRemoteControllerService")
    public void setRemoteControllerService(RemoteControllerService remoteControllerService) {
        this.remoteControllerService = remoteControllerService;
    }

    @Autowired
    @Qualifier("deviceSessionManager")
    public void setSessionManager(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }
}
