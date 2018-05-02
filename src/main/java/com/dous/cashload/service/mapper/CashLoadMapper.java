package com.dous.cashload.service.mapper;

import com.dous.cashload.domain.*;
import com.dous.cashload.service.dto.CashLoadDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CashLoad and its DTO CashLoadDTO.
 */
@Mapper(componentModel = "spring", uses = {AtmInformationMapper.class})
public interface CashLoadMapper extends EntityMapper<CashLoadDTO, CashLoad> {

    @Mapping(source = "atm.id", target = "atmId")
    CashLoadDTO toDto(CashLoad cashLoad);

    @Mapping(source = "atmId", target = "atm")
    CashLoad toEntity(CashLoadDTO cashLoadDTO);

    default CashLoad fromId(Long id) {
        if (id == null) {
            return null;
        }
        CashLoad cashLoad = new CashLoad();
        cashLoad.setId(id);
        return cashLoad;
    }
}
