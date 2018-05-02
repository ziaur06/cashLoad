package com.dous.cashload.repository;

import com.dous.cashload.domain.CashLoad;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the CashLoad entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CashLoadRepository extends JpaRepository<CashLoad, Long> {

}
