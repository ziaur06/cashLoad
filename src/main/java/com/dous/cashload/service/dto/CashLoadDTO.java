package com.dous.cashload.service.dto;


import java.time.LocalDate;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the CashLoad entity.
 */
public class CashLoadDTO implements Serializable {

    private Long id;

    private LocalDate loadingDate;

    private BigDecimal amount;

    private BigDecimal l100;

    private BigDecimal l500;

    private BigDecimal l1000;

    private String tag;

    private Long atmId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getLoadingDate() {
        return loadingDate;
    }

    public void setLoadingDate(LocalDate loadingDate) {
        this.loadingDate = loadingDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getl100() {
        return l100;
    }

    public void setl100(BigDecimal l100) {
        this.l100 = l100;
    }

    public BigDecimal getl500() {
        return l500;
    }

    public void setl500(BigDecimal l500) {
        this.l500 = l500;
    }

    public BigDecimal getl1000() {
        return l1000;
    }

    public void setl1000(BigDecimal l1000) {
        this.l1000 = l1000;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Long getAtmId() {
        return atmId;
    }

    public void setAtmId(Long atmInformationId) {
        this.atmId = atmInformationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CashLoadDTO cashLoadDTO = (CashLoadDTO) o;
        if(cashLoadDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cashLoadDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CashLoadDTO{" +
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
