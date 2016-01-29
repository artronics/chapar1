package com.artronics.chapar.client.driver.buffer;

import java.io.InputStream;

public interface BufferDistributor
{
    void setInput(InputStream input);

    void bufferReceived();
}
