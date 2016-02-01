package com.artronics.chapar.domain.entities;

import com.artronics.chapar.domain.model.graph.Edge;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

//@Entity
//@Table(name = "sen_links")
@Embeddable
public class SensorLink implements Edge{
    private Sensor dstSensor;

    private Double weight;

    public SensorLink() {
    }

    public SensorLink(Sensor dstSensor) {
        this.dstSensor = dstSensor;
    }

    public SensorLink(Sensor dstSensor, Double weight) {
        this.dstSensor = dstSensor;
        this.weight = weight;
    }

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "dst_sen_id")
    public Sensor getDstSensor() {
        return dstSensor;
    }

    public void setDstSensor(Sensor dstSensor) {
        this.dstSensor = dstSensor;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    @Override
    public int hashCode()
    {
        HashCodeBuilder hcb = new HashCodeBuilder();
        hcb.append(this.dstSensor);

        return hcb.toHashCode();
    }

    @Override
    public boolean equals(Object obj)
    {
        if (!(obj instanceof SensorLink))
            return false;
        if (obj == this)
            return true;

        SensorLink that = (SensorLink) obj;
        EqualsBuilder eb = new EqualsBuilder();

        eb.append(this.dstSensor,that.dstSensor);

        return eb.isEquals();
    }

    @Override
    public String toString()
    {
        return formatNeighbor(this.weight,this.dstSensor);
    }

    public static String formatNeighbor( Double weight, Sensor n2)
    {
        String s ="";
        s+=String.format(" <---[ %-5.0f ]---> " ,weight);
        s += n2.toString();
        return s;
    }

    public static SensorLink create(Sensor dstSensor, double weight) {
        SensorLink l = new SensorLink(dstSensor,weight);

        return l;
    }
}
