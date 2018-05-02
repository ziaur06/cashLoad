package com.dous.cashload.service.mapper;

import com.dous.cashload.domain.*;
import com.dous.cashload.service.dto.CashIssueDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CashIssue and its DTO CashIssueDTO.
 */
@Mapper(componentModel = "spring", uses = {OfficeMapper.class})
public interface CashIssueMapper extends EntityMapper<CashIssueDTO, CashIssue> {

    @Mapping(source = "office.id", target = "officeId")
    CashIssueDTO toDto(CashIssue cashIssue);

    @Mapping(source = "officeId", target = "office")
    CashIssue toEntity(CashIssueDTO cashIssueDTO);

    default CashIssue fromId(Long id) {
        if (id == null) {
            return null;
        }
        CashIssue cashIssue = new CashIssue();
        cashIssue.setId(id);
        return cashIssue;
    }
}
