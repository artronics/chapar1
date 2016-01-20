package com.artronics.chapar.core.remote;

import com.artronics.chapar.core.entities.Session;

public interface SessionManager {

    Session getSession();

    void setSession(Session session);
}
