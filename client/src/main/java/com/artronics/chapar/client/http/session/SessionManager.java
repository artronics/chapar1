package com.artronics.chapar.client.http.session;

import com.artronics.chapar.domain.entities.Session;

public interface SessionManager {

    Session getSession();

    void setSession(Session session);
}
