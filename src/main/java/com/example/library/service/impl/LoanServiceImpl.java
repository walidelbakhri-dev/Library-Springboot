package com.example.library.service.impl;

import com.example.library.dto.*;
import com.example.library.entity.*;
import com.example.library.exception.NotFoundException;
import com.example.library.mapper.LoanMapper;
import com.example.library.repository.*;
import com.example.library.service.LoanService;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
@Transactional
public class LoanServiceImpl implements LoanService {

    private final LoanRepository loanRepo;
    private final BookRepository bookRepo;
    private final MemberRepository memberRepo;
    private final LoanMapper mapper;

    public LoanServiceImpl(LoanRepository loanRepo, BookRepository bookRepo, MemberRepository memberRepo, LoanMapper mapper) {
        this.loanRepo = loanRepo;
        this.bookRepo = bookRepo;
        this.memberRepo = memberRepo;
        this.mapper = mapper;
    }

    @Override
    public LoanDto createLoan(LoanCreateDto dto) {
        // Validate book & member exist
        Book book = bookRepo.findById(dto.getBookId()).orElseThrow(() -> new NotFoundException("Livre non trouvé"));
        Member member = memberRepo.findById(dto.getMemberId()).orElseThrow(() -> new NotFoundException("Membre non trouvé"));

        // Check if book already loaned
        if (loanRepo.findActiveLoanByBookId(book.getId()).isPresent()) {
            throw new IllegalArgumentException("Livre déjà emprunté");
        }

        Instant now = Instant.now();

        int days;
        if (dto.getDueInDays() > 0) days = dto.getDueInDays();
        else days = 14;

        Instant due = now.plus(days, ChronoUnit.DAYS);

        Loan loan = null;
        Loan saved = loanRepo.save(loan);
        return mapper.toDto(saved);
    }

    @Override
    public LoanDto returnLoan(Long loanId) {
        Loan loan = loanRepo.findById(loanId).orElseThrow(() -> new NotFoundException("Emprunt non trouvé"));
        if (loan.getReturnedDate() != null) throw new IllegalArgumentException("Emprunt déjà retourné");

        loan.setReturnedDate(Instant.now());
        loan.setStatus(loan.getReturnedDate().isAfter(loan.getDueDate()) ? "LATE" : "RETURNED");
        Loan saved = loanRepo.save(loan);
        return mapper.toDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public LoanDto getById(Long id) {
        return loanRepo.findById(id).map(mapper::toDto).orElseThrow(() -> new NotFoundException("Emprunt non trouvé"));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LoanDto> list(String q, Pageable pageable) {
        if (q == null || q.isBlank()) {
            return loanRepo.findAll(pageable).map(mapper::toDto);
        }
        // search by book title or member name
        return loanRepo.findAll(
                (root, query, cb) -> cb.or(
                        cb.like(cb.lower(root.join("book").get("title")), "%" + q.toLowerCase() + "%"),
                        cb.like(cb.lower(root.join("member").get("fullName")), "%" + q.toLowerCase() + "%")
                ), pageable).map(mapper::toDto);
    }

    @Override
    public Page<LoanDto> listByMember(Long memberId, Pageable pageable) {
        return loanRepo.findByMemberId(memberId, pageable).map(mapper::toDto);
    }
}