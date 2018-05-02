package com.dous.cashload.service.mapper;

import com.dous.cashload.domain.*;
import com.dous.cashload.service.dto.UserInformationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity UserInformation and its DTO UserInformationDTO.
 */
@Mapper(componentModel = "spring", uses = {OfficeMapper.class})
public interface UserInformationMapper extends EntityMapper<UserInformationDTO, UserInformation> {

    @Mapping(source = "office.id", target = "officeId")
    UserInformationDTO toDto(UserInformation userInformation);

    @Mapping(source = "officeId", target = "office")
    UserInformation toEntity(UserInformationDTO userInformationDTO);

    default UserInformation fromId(Long id) {
        if (id == null) {
            return null;
        }
        UserInformation userInformation = new UserInformation();
        userInformation.setId(id);
        return userInformation;
    }
}
