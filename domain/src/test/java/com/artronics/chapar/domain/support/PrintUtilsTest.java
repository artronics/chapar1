package com.artronics.chapar.domain.support;

import com.artronics.chapar.domain.entities.Client;
import com.artronics.chapar.domain.entities.Sensor;
import com.artronics.chapar.domain.entities.SensorLink;
import com.artronics.chapar.domain.entities.address.UnicastAddress;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PrintUtilsTest {

    /*
        For now the purpose of these tests are just to see whether it works or not
        buy printing to console. Remember these are NOT real tests
     */

    protected Sensor src ;
    protected Sensor dst ;
    protected Sensor n0;
    protected Sensor n1;

    @Before
    public void setUp() throws Exception {
        Client client = new Client(2L);
        src = Sensor.create(UnicastAddress.create(client,12l));
        src.setType(Sensor.Type.SINK);
        dst = Sensor.create(UnicastAddress.create(client,132l));
        n0 = Sensor.create(UnicastAddress.create(client,113l));
        n1 = Sensor.create(UnicastAddress.create(client,128l));
    }

    @Test
    public void short_and_long_node_toString(){
        System.out.println(PrintUtils.printLongNode(src));
        System.out.println(PrintUtils.printShortNode(src));
        src.setId(123L);
        System.out.println(PrintUtils.printLongNode(src));
    }

    @Test
    public void print_links(){
        SensorLink l1 = new SensorLink(dst, 23D);
        SensorLink l2 = new SensorLink(n0, 23D);
        SensorLink l3 = new SensorLink(n1, 23D);
        Set<SensorLink> l = new HashSet<>(Arrays.asList(l1,l2,l3));

        System.out.println(PrintUtils.printNodeLinks(src,l));
    }

    @Test
    public void print_links_neighbor(){
        SensorLink l1 = new SensorLink(dst, 23D);
        SensorLink l2 = new SensorLink(n0, 23D);
        SensorLink l3 = new SensorLink(n1, 23D);
        Set<SensorLink> l = new HashSet<>(Arrays.asList(l1,l2,l3));

        System.out.println(PrintUtils.printNeighbors(l,100));
    }

    @Test
    public void print_buffer(){
        List<Integer> c = Arrays.asList(23,45,67,888,6,44,4);
        System.out.println(PrintUtils.printBuffer(c));
    }

}