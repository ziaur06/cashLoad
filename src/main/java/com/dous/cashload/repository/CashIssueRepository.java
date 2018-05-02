package com.dous.cashload.repository;

import com.dous.cashload.domain.CashIssue;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the CashIssue entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CashIssueRepository extends JpaRepository<CashIssue, Long> {

}
