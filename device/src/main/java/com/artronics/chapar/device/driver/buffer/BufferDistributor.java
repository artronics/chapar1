package com.artronics.chapar.device.driver.buffer;

import java.io.InputStream;

public interface BufferDistributor
{
    void setInput(InputStream input);

    void bufferReceived();
}
