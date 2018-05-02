package com.dous.cashload.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A CashReceive.
 */
@Entity
@Table(name = "cash_receive")
public class CashReceive implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "receive_date")
    private LocalDate receiveDate;

    @Column(name = "amount", precision=10, scale=2)
    private BigDecimal amount;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "invoice_number")
    private String invoiceNumber;

    @Column(name = "tag")
    private String tag;

    @Column(name = "r_100", precision=10, scale=2)
    private BigDecimal r100;

    @Column(name = "r_500", precision=10, scale=2)
    private BigDecimal r500;

    @Column(name = "r_1000", precision=10, scale=2)
    private BigDecimal r1000;

    @Column(name = "recjected_amount", precision=10, scale=2)
    private BigDecimal recjectedAmount;

    @Column(name = "f_100", precision=10, scale=2)
    private BigDecimal f100;

    @Column(name = "f_500", precision=10, scale=2)
    private BigDecimal f500;

    @Column(name = "f_1000", precision=10, scale=2)
    private BigDecimal f1000;

    @Column(name = "tip")
    private String tip;

    @ManyToOne
    private Office office;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getReceiveDate() {
        return receiveDate;
    }

    public CashReceive receiveDate(LocalDate receiveDate) {
        this.receiveDate = receiveDate;
        return this;
    }

    public void setReceiveDate(LocalDate receiveDate) {
        this.receiveDate = receiveDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public CashReceive amount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getUserId() {
        return userId;
    }

    public CashReceive userId(String userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRemarks() {
        return remarks;
    }

    public CashReceive remarks(String remarks) {
        this.remarks = remarks;
        return this;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public CashReceive invoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
        return this;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getTag() {
        return tag;
    }

    public CashReceive tag(String tag) {
        this.tag = tag;
        return this;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public BigDecimal getr100() {
        return r100;
    }

    public CashReceive r100(BigDecimal r100) {
        this.r100 = r100;
        return this;
    }

    public void setr100(BigDecimal r100) {
        this.r100 = r100;
    }

    public BigDecimal getr500() {
        return r500;
    }

    public CashReceive r500(BigDecimal r500) {
        this.r500 = r500;
        return this;
    }

    public void setr500(BigDecimal r500) {
        this.r500 = r500;
    }

    public BigDecimal getr1000() {
        return r1000;
    }

    public CashReceive r1000(BigDecimal r1000) {
        this.r1000 = r1000;
        return this;
    }

    public void setr1000(BigDecimal r1000) {
        this.r1000 = r1000;
    }

    public BigDecimal getRecjectedAmount() {
        return recjectedAmount;
    }

    public CashReceive recjectedAmount(BigDecimal recjectedAmount) {
        this.recjectedAmount = recjectedAmount;
        return this;
    }

    public void setRecjectedAmount(BigDecimal recjectedAmount) {
        this.recjectedAmount = recjectedAmount;
    }

    public BigDecimal getf100() {
        return f100;
    }

    public CashReceive f100(BigDecimal f100) {
        this.f100 = f100;
        return this;
    }

    public void setf100(BigDecimal f100) {
        this.f100 = f100;
    }

    public BigDecimal getf500() {
        return f500;
    }

    public CashReceive f500(BigDecimal f500) {
        this.f500 = f500;
        return this;
    }

    public void setf500(BigDecimal f500) {
        this.f500 = f500;
    }

    public BigDecimal getf1000() {
        return f1000;
    }

    public CashReceive f1000(BigDecimal f1000) {
        this.f1000 = f1000;
        return this;
    }

    public void setf1000(BigDecimal f1000) {
        this.f1000 = f1000;
    }

    public String getTip() {
        return tip;
    }

    public CashReceive tip(String tip) {
        this.tip = tip;
        return this;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public Office getOffice() {
        return office;
    }

    public CashReceive office(Office office) {
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
        CashReceive cashReceive = (CashReceive) o;
        if (cashReceive.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cashReceive.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CashReceive{" +
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
