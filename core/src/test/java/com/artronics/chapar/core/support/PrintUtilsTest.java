package com.artronics.chapar.core.support;

import com.artronics.chapar.core.entities.Link;
import com.artronics.chapar.core.entities.Node;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class PrintUtilsTest {

    /*
        For now the purpose of these tests are just to see whether it works or not
        buy printing to console. Remember these are NOT real tests
     */

    protected Node src = new Node(2123L);
    protected Node dst = new Node(87L);
    protected Node n0 = new Node(10L);
    protected Node n1 = new Node(143L);

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

}