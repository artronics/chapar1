package com.artronics.chapar.client.driver.buffer;

import java.util.List;

/**
 * A Buffer Collector gets a <code>List</code> of <code>Integer</code>s as input
 * and by some predefined rules it separates them in logical pieces. These chunks of
 * <code>List</code>s will be used to create a {@link com.artronics.chapar.domain.entities.Buffer}
 * <p>A meaningful stream of numbers always must start with <code>startByte</code> and ends with
 * <code>stopByte</code>. These bytes could be provided as properties and injectable under these
 * namespaces: <code>com.artronics.chapar.client.buffer.startByte</code> and
 * <code>com.artronics.chapar.client.buffer.stopByte</code>. The other rules is that the second
 * byte must be the length of whole buffer.
 * </p>
 *
 * @author Seyed Jalal Hosseini (jalalhosseiny@gmail.com)
 */
public interface BufferCollector
{
    /**
     * It gets a <code>List</code> of <code>Integer</code>s and returns a <code>List</code>
     * of buffers. These buffers are also a <code>List</code> of <code>Integer</code>s each.
     * The implementation must keep the end of <code>List</code> values if the sequence is not
     * completed yet. After calling this method again it should keep processing the stream of
     * <code>Integers</code> from where it left in previous call.
     *
     * @param receivedData the received data
     * @return the list
     */
    List<List<Integer>> generateLists(List<Integer> receivedData);

    /**
     * Sets start byte.
     *
     * @param startByte the start byte
     */
    void setStartByte(int startByte);

    /**
     * Sets stop byte.
     *
     * @param stopByte the stop byte
     */
    void setStopByte(int stopByte);

}
