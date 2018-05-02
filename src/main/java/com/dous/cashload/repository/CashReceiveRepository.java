package com.dous.cashload.repository;

import com.dous.cashload.domain.CashReceive;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the CashReceive entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CashReceiveRepository extends JpaRepository<CashReceive, Long> {

}
