package com.dous.cashload.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.dous.cashload.domain.enumeration.Status;

/**
 * A AtmInformation.
 */
@Entity
@Table(name = "atm_information")
public class AtmInformation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "model")
    private String model;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @OneToMany(mappedBy = "atmInformation")
    @JsonIgnore
    private Set<Office> offices = new HashSet<>();

    @ManyToOne
    private Location location;

    @ManyToOne
    private Branch branch;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public AtmInformation name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public AtmInformation code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getModel() {
        return model;
    }

    public AtmInformation model(String model) {
        this.model = model;
        return this;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Status getStatus() {
        return status;
    }

    public AtmInformation status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Set<Office> getOffices() {
        return offices;
    }

    public AtmInformation offices(Set<Office> offices) {
        this.offices = offices;
        return this;
    }

    public AtmInformation addOffice(Office office) {
        this.offices.add(office);
        office.setAtmInformation(this);
        return this;
    }

    public AtmInformation removeOffice(Office office) {
        this.offices.remove(office);
        office.setAtmInformation(null);
        return this;
    }

    public void setOffices(Set<Office> offices) {
        this.offices = offices;
    }

    public Location getLocation() {
        return location;
    }

    public AtmInformation location(Location location) {
        this.location = location;
        return this;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Branch getBranch() {
        return branch;
    }

    public AtmInformation branch(Branch branch) {
        this.branch = branch;
        return this;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AtmInformation atmInformation = (AtmInformation) o;
        if (atmInformation.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), atmInformation.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AtmInformation{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", code='" + getCode() + "'" +
            ", model='" + getModel() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
