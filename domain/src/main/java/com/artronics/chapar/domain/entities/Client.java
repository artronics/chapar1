package com.artronics.chapar.domain.entities;

import com.artronics.chapar.domain.model.NetworkComponent;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "clients")
public class Client implements NetworkComponent{

    protected Long id;

    private Controller controller;

    protected Date created;
    protected Date updated;

    public Client() {
    }

    public Client(Long id) {
        this.id = id;
    }

    public Client(Controller controller) {
        this.controller = controller;
    }

    public Client(Long id, Controller controller) {
        this.id = id;
        this.controller = controller;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false,unique = true)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ctrl_id",nullable = false)
    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public Date getCreated()
    {
        return created;
    }

    public void setCreated(Date created)
    {
        this.created = created;
    }

    public Date getUpdated()
    {
        return updated;
    }

    public void setUpdated(Date updated)
    {
        this.updated = updated;
    }

    @PrePersist
    protected void onCreate()
    {
        created = new Date();
    }

    @PreUpdate
    protected void onUpdate()
    {
        updated = new Date();
    }

    @Override
    public int hashCode()
    {
        //use getters for getting fields(for ORM) see this SO answer:
        //http://stackoverflow.com/questions/27581/what-issues-should-be-considered-when
        // -overriding-equals-and-hashcode-in-java

        HashCodeBuilder hcb = new HashCodeBuilder();
        hcb.append(this.id);

        return hcb.toHashCode();
    }

    @Override
    public boolean equals(Object obj)
    {
        if (!(obj instanceof Client))
            return false;
        if (obj == this)
            return true;

        Client that = (Client) obj;
        EqualsBuilder eb = new EqualsBuilder();

        eb.append(this.id,that.id);

        return eb.isEquals();
    }

    @Override
    public String toString()
    {
        String s = "Client:->ID:";
        s+=getId()==null? "[UNK]": getId().toString();

        return s;
    }

}
