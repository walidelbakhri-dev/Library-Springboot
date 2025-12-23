package com.example.library.Repository;

import com.example.library.model.Loan;
import com.example.library.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<Loan, Long> {
    boolean existsByBookAndReturnedDateIsNull(Book book);
}