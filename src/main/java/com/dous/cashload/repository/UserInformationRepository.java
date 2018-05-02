package com.dous.cashload.repository;

import com.dous.cashload.domain.UserInformation;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the UserInformation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserInformationRepository extends JpaRepository<UserInformation, Long> {

}
