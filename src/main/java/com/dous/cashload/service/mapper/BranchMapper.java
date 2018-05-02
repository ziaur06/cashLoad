package com.dous.cashload.service.mapper;

import com.dous.cashload.domain.*;
import com.dous.cashload.service.dto.BranchDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Branch and its DTO BranchDTO.
 */
@Mapper(componentModel = "spring", uses = {BankMapper.class})
public interface BranchMapper extends EntityMapper<BranchDTO, Branch> {

    @Mapping(source = "bank.id", target = "bankId")
    BranchDTO toDto(Branch branch);

    @Mapping(source = "bankId", target = "bank")
    Branch toEntity(BranchDTO branchDTO);

    default Branch fromId(Long id) {
        if (id == null) {
            return null;
        }
        Branch branch = new Branch();
        branch.setId(id);
        return branch;
    }
}
