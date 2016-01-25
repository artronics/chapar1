package com.artronics.chapar.core.support;

import com.artronics.chapar.core.map.BaseMapTest;
import org.junit.Test;

public class NodeMapPrinterTest extends BaseMapTest{

    private static final NodeMapPrinter printer= new NodeMapPrinter();


    @Test
    public void just_print_a_netMap_to_see_the_result(){
        String s =printer.printDeviceMap(nodeMap,device);
        System.out.println(s);
    }

}