package com.artronics.chapar.core.map;

import org.junit.Before;

public class BaseMapTest extends BaseGraphTest{
    protected NodeMap nodeMap;

    /*
     * node number 30 is sameAddNode
     *
     * Graph is like
     *       sink:0
     *       /   \
     *      w50  w10
     *      /      \
     *   135 --w20-- 30
     *     \         /
     *     w25    w100
     *       \    /
     *        136
     *         |
     *        w30
     *         |
     *        137
     *
     *
     */
    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        nodeMap = new NodeMapImpl(sampleGraph1);
    }
}
