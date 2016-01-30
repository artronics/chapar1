package com.artronics.chapar.domain.repositories;

import java.sql.Timestamp;

public interface TimeRepo{
    Timestamp getDbNowTime();
}
