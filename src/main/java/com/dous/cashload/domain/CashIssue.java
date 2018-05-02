package com.dous.cashload.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A CashIssue.
 */
@Entity
@Table(name = "cash_issue")
public class CashIssue implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "issue_date")
    private LocalDate issueDate;

    @Column(name = "amount", precision=10, scale=2)
    private BigDecimal amount;

    @Column(name = "i_100", precision=10, scale=2)
    private BigDecimal i100;

    @Column(name = "i_500", precision=10, scale=2)
    private BigDecimal i500;

    @Column(name = "i_1000", precision=10, scale=2)
    private BigDecimal i1000;

    @Column(name = "tip")
    private String tip;

    @Column(name = "invoice_number")
    private String invoiceNumber;

    @Column(name = "tag")
    private String tag;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "remarks")
    private String remarks;

    @ManyToOne
    private Office office;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public CashIssue issueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
        return this;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public CashIssue amount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal geti100() {
        return i100;
    }

    public CashIssue i100(BigDecimal i100) {
        this.i100 = i100;
        return this;
    }

    public void seti100(BigDecimal i100) {
        this.i100 = i100;
    }

    public BigDecimal geti500() {
        return i500;
    }

    public CashIssue i500(BigDecimal i500) {
        this.i500 = i500;
        return this;
    }

    public void seti500(BigDecimal i500) {
        this.i500 = i500;
    }

    public BigDecimal geti1000() {
        return i1000;
    }

    public CashIssue i1000(BigDecimal i1000) {
        this.i1000 = i1000;
        return this;
    }

    public void seti1000(BigDecimal i1000) {
        this.i1000 = i1000;
    }

    public String getTip() {
        return tip;
    }

    public CashIssue tip(String tip) {
        this.tip = tip;
        return this;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public CashIssue invoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
        return this;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getTag() {
        return tag;
    }

    public CashIssue tag(String tag) {
        this.tag = tag;
        return this;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getUserId() {
        return userId;
    }

    public CashIssue userId(String userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRemarks() {
        return remarks;
    }

    public CashIssue remarks(String remarks) {
        this.remarks = remarks;
        return this;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Office getOffice() {
        return office;
    }

    public CashIssue office(Office office) {
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
        CashIssue cashIssue = (CashIssue) o;
        if (cashIssue.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cashIssue.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CashIssue{" +
            "id=" + getId() +
            ", issueDate='" + getIssueDate() + "'" +
            ", amount=" + getAmount() +
            ", i100=" + geti100() +
            ", i500=" + geti500() +
            ", i1000=" + geti1000() +
            ", tip='" + getTip() + "'" +
            ", invoiceNumber='" + getInvoiceNumber() + "'" +
            ", tag='" + getTag() + "'" +
            ", userId='" + getUserId() + "'" +
            ", remarks='" + getRemarks() + "'" +
            "}";
    }
}
