package com.dous.cashload.repository;

import com.dous.cashload.domain.CashBalance;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the CashBalance entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CashBalanceRepository extends JpaRepository<CashBalance, Long> {

}
