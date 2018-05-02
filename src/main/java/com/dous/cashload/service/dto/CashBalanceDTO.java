package com.dous.cashload.service.dto;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the CashBalance entity.
 */
public class CashBalanceDTO implements Serializable {

    private Long id;

    private BigDecimal balance;

    private BigDecimal n100;

    private BigDecimal n500;

    private BigDecimal n1000;

    private Long officeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getn100() {
        return n100;
    }

    public void setn100(BigDecimal n100) {
        this.n100 = n100;
    }

    public BigDecimal getn500() {
        return n500;
    }

    public void setn500(BigDecimal n500) {
        this.n500 = n500;
    }

    public BigDecimal getn1000() {
        return n1000;
    }

    public void setn1000(BigDecimal n1000) {
        this.n1000 = n1000;
    }

    public Long getOfficeId() {
        return officeId;
    }

    public void setOfficeId(Long officeId) {
        this.officeId = officeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CashBalanceDTO cashBalanceDTO = (CashBalanceDTO) o;
        if(cashBalanceDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cashBalanceDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CashBalanceDTO{" +
            "id=" + getId() +
            ", balance=" + getBalance() +
            ", n100=" + getn100() +
            ", n500=" + getn500() +
            ", n1000=" + getn1000() +
            "}";
    }
}
