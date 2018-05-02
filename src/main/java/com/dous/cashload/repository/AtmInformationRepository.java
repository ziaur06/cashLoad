package com.dous.cashload.repository;

import com.dous.cashload.domain.AtmInformation;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the AtmInformation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AtmInformationRepository extends JpaRepository<AtmInformation, Long> {

}
