package com.artronics.chapar.domain.entities;

import com.artronics.chapar.domain.entities.address.UnicastAddress;
import com.artronics.chapar.domain.model.NetworkComponent;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "sensors")
public class Sensor extends AbstractBaseEntity implements NetworkComponent{

    private UnicastAddress address;

    //Normal as default value
    private Type type = Type.NORMAL;

    private Double battery;

    private List<SensorLink> links;

    public Sensor() {
    }

    private Sensor(UnicastAddress address) {
        this.address = address;
    }

    public static Sensor create(UnicastAddress address){
        return new Sensor(address);
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "add_id")
    public UnicastAddress getAddress() {
        return address;
    }

    @Enumerated(EnumType.STRING)
    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setAddress(UnicastAddress address) {
        this.address = address;
    }

    public Double getBattery() {
        return battery;
    }

    public void setBattery(Double battery) {
        this.battery = battery;
    }

    @ElementCollection(fetch = FetchType.EAGER,targetClass = SensorLink.class)
    @CollectionTable(name = "sen_links")
    @Column(name = "links")
    public List<SensorLink> getLinks() {
        return links;
    }

    public void setLinks(List<SensorLink> links) {
        this.links = links;
    }

    @Override
    public int hashCode()
    {
        //use getters for getting fields(for ORM) see this SO answer:
        //http://stackoverflow.com/questions/27581/what-issues-should-be-considered-when
        // -overriding-equals-and-hashcode-in-java

        HashCodeBuilder hcb = new HashCodeBuilder();
        hcb.append(this.address);

        return hcb.toHashCode();
    }

    @Override
    public boolean equals(Object obj)
    {
        if (!(obj instanceof Sensor))
            return false;
        if (obj == this)
            return true;

        Sensor that = (Sensor) obj;
        EqualsBuilder eb = new EqualsBuilder();

        eb.append(this.address,that.address);

        return eb.isEquals();
    }

    @Override
    public String toString() {
        return printShortNode(this);
    }

    public static String printShortNode(Sensor sensor)
    {
        String s = sensor.getType() == Sensor.Type.SINK ? "Sink: " : "Sensor: ";

        return String.format(s + "%-3d ", sensor.getAddress().getLocalAddress());
    }

    public enum Type
    {
        SINK,
        NORMAL
    }

}
