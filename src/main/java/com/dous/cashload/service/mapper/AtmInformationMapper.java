package com.dous.cashload.service.mapper;

import com.dous.cashload.domain.*;
import com.dous.cashload.service.dto.AtmInformationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity AtmInformation and its DTO AtmInformationDTO.
 */
@Mapper(componentModel = "spring", uses = {LocationMapper.class, BranchMapper.class})
public interface AtmInformationMapper extends EntityMapper<AtmInformationDTO, AtmInformation> {

    @Mapping(source = "location.id", target = "locationId")
    @Mapping(source = "branch.id", target = "branchId")
    AtmInformationDTO toDto(AtmInformation atmInformation);

    @Mapping(target = "offices", ignore = true)
    @Mapping(source = "locationId", target = "location")
    @Mapping(source = "branchId", target = "branch")
    AtmInformation toEntity(AtmInformationDTO atmInformationDTO);

    default AtmInformation fromId(Long id) {
        if (id == null) {
            return null;
        }
        AtmInformation atmInformation = new AtmInformation();
        atmInformation.setId(id);
        return atmInformation;
    }
}
