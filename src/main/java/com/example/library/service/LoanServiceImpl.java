package com.example.library.service;

import com.example.library.dto.LoanRequestDTO;
import com.example.library.dto.LoanResponseDTO;
import com.example.library.Mapper.LoanMapper;
import com.example.library.model.Book;
import com.example.library.model.Loan;
import com.example.library.model.Member;
import com.example.library.Repository.BookRepository;
import com.example.library.Repository.LoanRepository;
import com.example.library.Repository.MemberRepository;
import com.example.library.service.LoanService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.stream.Collectors;

@Service
public class LoanServiceImpl implements LoanService {
    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;

    public LoanServiceImpl(LoanRepository loanRepository, BookRepository bookRepository, MemberRepository memberRepository) {
        this.loanRepository = loanRepository;
        this.bookRepository = bookRepository;
        this.memberRepository = memberRepository;
    }

    @Override
    public LoanResponseDTO createLoan(LoanRequestDTO dto) {
        Book b = bookRepository.findById(dto.getBookId()).orElseThrow(() -> new IllegalArgumentException("Book not found"));
        Member m = memberRepository.findById(dto.getMemberId()).orElseThrow(() -> new IllegalArgumentException("Member not found"));
        Loan loan = new Loan(b, m, dto.getLoanDate() != null ? dto.getLoanDate() : LocalDate.now(), dto.getDueDate());
        Loan saved = loanRepository.save(loan);
        return LoanMapper.toDto(saved);
    }

    @Override
    public LoanResponseDTO getLoanById(Long id) {
        return loanRepository.findById(id).map(LoanMapper::toDto).orElse(null);
    }

    @Override
    public Page<LoanResponseDTO> getAllLoans(Pageable pageable) {
        Page<Loan> page = loanRepository.findAll(pageable);
        return new PageImpl<>(page.getContent().stream().map(LoanMapper::toDto).collect(Collectors.toList()), pageable, page.getTotalElements());
    }

    @Override
    public LoanResponseDTO returnLoan(Long id) {
        return loanRepository.findById(id).map(loan -> {
            loan.setReturnedDate(LocalDate.now());
            return LoanMapper.toDto(loanRepository.save(loan));
        }).orElse(null);
    }

    @Override
    public void deleteLoan(Long id) { loanRepository.deleteById(id); }
}