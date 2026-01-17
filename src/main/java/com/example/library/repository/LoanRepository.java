package com.example.library.repository;

import com.example.library.entity.Loan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long>, JpaSpecificationExecutor<Loan> {
    @Query("select l from Loan l where l.book.id = :bookId and l.returnedDate is null")
    Optional<Loan> findActiveLoanByBookId(Long bookId);

    Page<Loan> findByMemberId(Long memberId, Pageable pageable);
}