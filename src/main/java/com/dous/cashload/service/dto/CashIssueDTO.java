package com.dous.cashload.service.dto;


import java.time.LocalDate;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the CashIssue entity.
 */
public class CashIssueDTO implements Serializable {

    private Long id;

    private LocalDate issueDate;

    private BigDecimal amount;

    private BigDecimal i100;

    private BigDecimal i500;

    private BigDecimal i1000;

    private String tip;

    private String invoiceNumber;

    private String tag;

    private String userId;

    private String remarks;

    private Long officeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal geti100() {
        return i100;
    }

    public void seti100(BigDecimal i100) {
        this.i100 = i100;
    }

    public BigDecimal geti500() {
        return i500;
    }

    public void seti500(BigDecimal i500) {
        this.i500 = i500;
    }

    public BigDecimal geti1000() {
        return i1000;
    }

    public void seti1000(BigDecimal i1000) {
        this.i1000 = i1000;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
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

        CashIssueDTO cashIssueDTO = (CashIssueDTO) o;
        if(cashIssueDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cashIssueDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CashIssueDTO{" +
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
