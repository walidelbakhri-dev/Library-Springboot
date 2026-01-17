package com.example.library.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;

@Entity
@Table(name = "loan", indexes = {
        @Index(name = "idx_loan_member", columnList = "member_id"),
        @Index(name = "idx_loan_book", columnList = "book_id"),
        @Index(name = "idx_loan_status", columnList = "status")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(name = "loan_date", nullable = false)
    private Instant loanDate;

    @Column(name = "due_date", nullable = false)
    private Instant dueDate;

    @Column(name = "returned_date")
    private Instant returnedDate;

    @Column(length = 20, nullable = false)
    private String status;
}
