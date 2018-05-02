package com.dous.cashload.service.dto;


import java.time.LocalDate;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the CashReceive entity.
 */
public class CashReceiveDTO implements Serializable {

    private Long id;

    private LocalDate receiveDate;

    private BigDecimal amount;

    private String userId;

    private String remarks;

    private String invoiceNumber;

    private String tag;

    private BigDecimal r100;

    private BigDecimal r500;

    private BigDecimal r1000;

    private BigDecimal recjectedAmount;

    private BigDecimal f100;

    private BigDecimal f500;

    private BigDecimal f1000;

    private String tip;

    private Long officeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(LocalDate receiveDate) {
        this.receiveDate = receiveDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public BigDecimal getr100() {
        return r100;
    }

    public void setr100(BigDecimal r100) {
        this.r100 = r100;
    }

    public BigDecimal getr500() {
        return r500;
    }

    public void setr500(BigDecimal r500) {
        this.r500 = r500;
    }

    public BigDecimal getr1000() {
        return r1000;
    }

    public void setr1000(BigDecimal r1000) {
        this.r1000 = r1000;
    }

    public BigDecimal getRecjectedAmount() {
        return recjectedAmount;
    }

    public void setRecjectedAmount(BigDecimal recjectedAmount) {
        this.recjectedAmount = recjectedAmount;
    }

    public BigDecimal getf100() {
        return f100;
    }

    public void setf100(BigDecimal f100) {
        this.f100 = f100;
    }

    public BigDecimal getf500() {
        return f500;
    }

    public void setf500(BigDecimal f500) {
        this.f500 = f500;
    }

    public BigDecimal getf1000() {
        return f1000;
    }

    public void setf1000(BigDecimal f1000) {
        this.f1000 = f1000;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
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

        CashReceiveDTO cashReceiveDTO = (CashReceiveDTO) o;
        if(cashReceiveDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cashReceiveDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CashReceiveDTO{" +
            "id=" + getId() +
            ", receiveDate='" + getReceiveDate() + "'" +
            ", amount=" + getAmount() +
            ", userId='" + getUserId() + "'" +
            ", remarks='" + getRemarks() + "'" +
            ", invoiceNumber='" + getInvoiceNumber() + "'" +
            ", tag='" + getTag() + "'" +
            ", r100=" + getr100() +
            ", r500=" + getr500() +
            ", r1000=" + getr1000() +
            ", recjectedAmount=" + getRecjectedAmount() +
            ", f100=" + getf100() +
            ", f500=" + getf500() +
            ", f1000=" + getf1000() +
            ", tip='" + getTip() + "'" +
            "}";
    }
}
