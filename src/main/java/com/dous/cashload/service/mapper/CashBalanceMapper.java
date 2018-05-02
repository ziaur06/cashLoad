package com.dous.cashload.service.mapper;

import com.dous.cashload.domain.*;
import com.dous.cashload.service.dto.CashBalanceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CashBalance and its DTO CashBalanceDTO.
 */
@Mapper(componentModel = "spring", uses = {OfficeMapper.class})
public interface CashBalanceMapper extends EntityMapper<CashBalanceDTO, CashBalance> {

    @Mapping(source = "office.id", target = "officeId")
    CashBalanceDTO toDto(CashBalance cashBalance);

    @Mapping(source = "officeId", target = "office")
    CashBalance toEntity(CashBalanceDTO cashBalanceDTO);

    default CashBalance fromId(Long id) {
        if (id == null) {
            return null;
        }
        CashBalance cashBalance = new CashBalance();
        cashBalance.setId(id);
        return cashBalance;
    }
}
