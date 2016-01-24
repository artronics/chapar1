package com.artronics.chapar.core.entities;

import com.artronics.chapar.core.entities.packet.PacketType;
import com.artronics.chapar.core.support.PrintUtils;

import javax.persistence.Transient;
import java.util.List;

//@Entity
//@Table(name = "tables")
public class Packet<T extends Enum<T> & PacketType> extends AbstractBaseEntity implements PacketI{

    protected List<Integer> content;

    protected Address srcAddress;

    protected Address dstAddress;

    private Node srcNode;

    private Node dstNode;

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

    @Transient
    public Address getSrcAddress() {
        return srcAddress;
    }

    @Transient
    public Address getDstAddress() {
        return dstAddress;
    }

    @Override
    public List<Integer> getContent() {
        return content;
    }

    @Override
    public Node getSrcNode() {
        return srcNode;
    }

    @Override
    public Node getDstNode() {
        return dstNode;
    }

    @Override
    public T getType() {
        return type;
    }

    @Override
    public Direction getDirection() {
        return direction;
    }

    public void setSrcNode(Node srcNode) {
        this.srcNode = srcNode;
    }

    public void setDstNode(Node dstNode) {
        this.dstNode = dstNode;
    }

    public void setType(T type) {
        this.type = type;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
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
