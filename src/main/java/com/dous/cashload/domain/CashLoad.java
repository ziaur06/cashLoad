package com.dous.cashload.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A CashLoad.
 */
@Entity
@Table(name = "cash_load")
public class CashLoad implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "loading_date")
    private LocalDate loadingDate;

    @Column(name = "amount", precision=10, scale=2)
    private BigDecimal amount;

    @Column(name = "l_100", precision=10, scale=2)
    private BigDecimal l100;

    @Column(name = "l_500", precision=10, scale=2)
    private BigDecimal l500;

    @Column(name = "l_1000", precision=10, scale=2)
    private BigDecimal l1000;

    @Column(name = "tag")
    private String tag;

    @ManyToOne
    private AtmInformation atm;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getLoadingDate() {
        return loadingDate;
    }

    public CashLoad loadingDate(LocalDate loadingDate) {
        this.loadingDate = loadingDate;
        return this;
    }

    public void setLoadingDate(LocalDate loadingDate) {
        this.loadingDate = loadingDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public CashLoad amount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getl100() {
        return l100;
    }

    public CashLoad l100(BigDecimal l100) {
        this.l100 = l100;
        return this;
    }

    public void setl100(BigDecimal l100) {
        this.l100 = l100;
    }

    public BigDecimal getl500() {
        return l500;
    }

    public CashLoad l500(BigDecimal l500) {
        this.l500 = l500;
        return this;
    }

    public void setl500(BigDecimal l500) {
        this.l500 = l500;
    }

    public BigDecimal getl1000() {
        return l1000;
    }

    public CashLoad l1000(BigDecimal l1000) {
        this.l1000 = l1000;
        return this;
    }

    public void setl1000(BigDecimal l1000) {
        this.l1000 = l1000;
    }

    public String getTag() {
        return tag;
    }

    public CashLoad tag(String tag) {
        this.tag = tag;
        return this;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public AtmInformation getAtm() {
        return atm;
    }

    public CashLoad atm(AtmInformation atmInformation) {
        this.atm = atmInformation;
        return this;
    }

    public void setAtm(AtmInformation atmInformation) {
        this.atm = atmInformation;
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
        CashLoad cashLoad = (CashLoad) o;
        if (cashLoad.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cashLoad.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CashLoad{" +
            "id=" + getId() +
            ", loadingDate='" + getLoadingDate() + "'" +
            ", amount=" + getAmount() +
            ", l100=" + getl100() +
            ", l500=" + getl500() +
            ", l1000=" + getl1000() +
            ", tag='" + getTag() + "'" +
            "}";
    }
}
