package com.example.library.repository.Specification;

import com.example.library.entity.Book;
import org.springframework.data.jpa.domain.Specification;

public final class BookSpecification {
    private BookSpecification() {}

    public static Specification<Book> titleContainsIgnoreCase(String text) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("title")), "%" + text.toLowerCase() + "%");
    }

    public static Specification<Book> authorContainsIgnoreCase(String text) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("author")), "%" + text.toLowerCase() + "%");
    }
}