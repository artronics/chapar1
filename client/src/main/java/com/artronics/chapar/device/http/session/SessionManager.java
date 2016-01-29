package com.artronics.chapar.device.http.session;

import com.artronics.chapar.core.entities.Session;

public interface SessionManager {

    Session getSession();

    void setSession(Session session);
}
