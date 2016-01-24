package com.artronics.chapar.core.support;

import com.artronics.chapar.core.entities.Address;
import com.artronics.chapar.core.entities.Device;
import com.artronics.chapar.core.entities.Link;
import com.artronics.chapar.core.entities.Node;
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

    protected Node src ;
    protected Node dst ;
    protected Node n0;
    protected Node n1;

    @Before
    public void setUp() throws Exception {
        Device device = new Device(2L);
        src = Node.create(Address.create(device,12l));
        dst = Node.create(Address.create(device,132l));
        n0 = Node.create(Address.create(device,113l));
        n1 = Node.create(Address.create(device,128l));
    }

    @Test
    public void short_and_long_node_toString(){
        System.out.println(PrintUtils.printLongNode(src));
        System.out.println(PrintUtils.printShortNode(src));
    }

    @Test
    public void print_links(){
        Link l1 = new Link(dst, 23D);
        Link l2 = new Link(n0, 23D);
        Link l3 = new Link(n1, 23D);
        Set<Link> l = new HashSet<>(Arrays.asList(l1,l2,l3));

        System.out.println(PrintUtils.printNodeLinks(src,l));
    }

    @Test
    public void print_buffer(){
        List<Integer> c = Arrays.asList(23,45,67,888,6,44,4);
        System.out.println(PrintUtils.printBuffer(c));
    }

}