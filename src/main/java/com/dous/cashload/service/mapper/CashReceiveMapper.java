package com.dous.cashload.service.mapper;

import com.dous.cashload.domain.*;
import com.dous.cashload.service.dto.CashReceiveDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CashReceive and its DTO CashReceiveDTO.
 */
@Mapper(componentModel = "spring", uses = {OfficeMapper.class})
public interface CashReceiveMapper extends EntityMapper<CashReceiveDTO, CashReceive> {

    @Mapping(source = "office.id", target = "officeId")
    CashReceiveDTO toDto(CashReceive cashReceive);

    @Mapping(source = "officeId", target = "office")
    CashReceive toEntity(CashReceiveDTO cashReceiveDTO);

    default CashReceive fromId(Long id) {
        if (id == null) {
            return null;
        }
        CashReceive cashReceive = new CashReceive();
        cashReceive.setId(id);
        return cashReceive;
    }
}
