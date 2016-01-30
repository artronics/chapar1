package com.artronics.chapar.client.driver.buffer;

import java.io.InputStream;
import java.io.OutputStream;

public interface BufferDistributor
{
    void setInput(InputStream input);

    void setOutput(OutputStream output);

    void bufferReceived();
}
