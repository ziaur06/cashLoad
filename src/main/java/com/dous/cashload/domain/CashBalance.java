package com.dous.cashload.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A CashBalance.
 */
@Entity
@Table(name = "cash_balance")
public class CashBalance implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "balance", precision=10, scale=2)
    private BigDecimal balance;

    @Column(name = "n_100", precision=10, scale=2)
    private BigDecimal n100;

    @Column(name = "n_500", precision=10, scale=2)
    private BigDecimal n500;

    @Column(name = "n_1000", precision=10, scale=2)
    private BigDecimal n1000;

    @ManyToOne
    private Office office;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public CashBalance balance(BigDecimal balance) {
        this.balance = balance;
        return this;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getn100() {
        return n100;
    }

    public CashBalance n100(BigDecimal n100) {
        this.n100 = n100;
        return this;
    }

    public void setn100(BigDecimal n100) {
        this.n100 = n100;
    }

    public BigDecimal getn500() {
        return n500;
    }

    public CashBalance n500(BigDecimal n500) {
        this.n500 = n500;
        return this;
    }

    public void setn500(BigDecimal n500) {
        this.n500 = n500;
    }

    public BigDecimal getn1000() {
        return n1000;
    }

    public CashBalance n1000(BigDecimal n1000) {
        this.n1000 = n1000;
        return this;
    }

    public void setn1000(BigDecimal n1000) {
        this.n1000 = n1000;
    }

    public Office getOffice() {
        return office;
    }

    public CashBalance office(Office office) {
        this.office = office;
        return this;
    }

    public void setOffice(Office office) {
        this.office = office;
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
        CashBalance cashBalance = (CashBalance) o;
        if (cashBalance.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cashBalance.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CashBalance{" +
            "id=" + getId() +
            ", balance=" + getBalance() +
            ", n100=" + getn100() +
            ", n500=" + getn500() +
            ", n1000=" + getn1000() +
            "}";
    }
}
