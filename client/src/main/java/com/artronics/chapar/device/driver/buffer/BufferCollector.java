package com.artronics.chapar.device.driver.buffer;

import java.util.List;

public interface BufferCollector
{
    List<List<Integer>> generateLists(List<Integer> receivedData);
}
