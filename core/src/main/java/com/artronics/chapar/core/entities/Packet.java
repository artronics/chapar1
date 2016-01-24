package com.artronics.chapar.core.entities;

import com.artronics.chapar.core.entities.packet.PacketType;
import com.artronics.chapar.core.support.PrintUtils;

import java.util.List;

//@Entity
//@Table(name = "tables")
public class Packet<T extends Enum<T> & PacketType> extends AbstractBaseEntity {

    protected List<Integer> content;

    protected Node srcNode;

    protected Address dstAddress;

    protected T type;

    protected Direction direction;

    public Packet(List<Integer> content) {
        this.content = content;
    }

    public Packet() {
    }

    public static Packet create(Buffer buffer){
        Packet packet = new Packet(buffer.getContent());

        return packet;
    }

    public Node getSrcNode() {
        return srcNode;
    }

    public Address getDstAddress() {
        return dstAddress;
    }

    public List<Integer> getContent() {
        return content;
    }

    public T getType() {
        return type;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setType(T type) {
        this.type = type;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void setSrcNode(Node srcNode) {
        this.srcNode = srcNode;
    }

    public void setDstAddress(Address dstAddress) {
        this.dstAddress = dstAddress;
    }

    protected enum Direction
    {
        RX,
        TX
    }

    @Override
    public String toString()
    {
//        return String.format("ID: %-5d - src: %s -> dst: %s ",id,srcNode,dstNode);
        return PrintUtils.printBuffer(content);
    }

}
