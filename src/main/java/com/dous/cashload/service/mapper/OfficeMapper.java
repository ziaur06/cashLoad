package com.dous.cashload.service.mapper;

import com.dous.cashload.domain.*;
import com.dous.cashload.service.dto.OfficeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Office and its DTO OfficeDTO.
 */
@Mapper(componentModel = "spring", uses = {AtmInformationMapper.class, CompanyMapper.class})
public interface OfficeMapper extends EntityMapper<OfficeDTO, Office> {

    @Mapping(source = "atmInformation.id", target = "atmInformationId")
    @Mapping(source = "company.id", target = "companyId")
    OfficeDTO toDto(Office office);

    @Mapping(source = "atmInformationId", target = "atmInformation")
    @Mapping(source = "companyId", target = "company")
    Office toEntity(OfficeDTO officeDTO);

    default Office fromId(Long id) {
        if (id == null) {
            return null;
        }
        Office office = new Office();
        office.setId(id);
        return office;
    }
}
