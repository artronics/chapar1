package com.artronics.chapar.domain.support;

import com.artronics.chapar.domain.map.BaseMapTest;
import org.junit.Test;

public class NodeMapPrinterTest extends BaseMapTest{

    private static final NodeMapPrinter printer= new NodeMapPrinter();

    @Test
    public void just_print_a_netMap_to_see_the_result(){
        String s =printer.printDeviceMap(nodeMap, client);
        System.out.println(s);
    }
}