package com.example.library.entity;

import com.example.library.model.BookCondition;
import com.example.library.model.BookStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.time.Instant;

@Entity
@Table(name = "book", indexes = {
        @Index(name = "idx_book_title", columnList = "title"),
        @Index(name = "idx_book_author", columnList = "author")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, length = 255)
    private String title;

    @Column(length = 255)
    private String author;

    @Column(unique = true, length = 100)
    private String isbn;

    private Integer year;

    @Column(name = "created_at", updatable = false, nullable = false)
    private Instant createdAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "condition", length = 20, nullable = false)
    private BookCondition condition;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20, nullable = false)
    private BookStatus status;

    @PrePersist
    public void prePersist() {
        if (createdAt == null) createdAt = Instant.now();
        if (condition == null) condition = BookCondition.NEW;
        if (status == null) status = BookStatus.AVAILABLE;
    }

    public void setCondition(BookCondition condition) {
    }

    public long getId() {
        return 0;
    }
}