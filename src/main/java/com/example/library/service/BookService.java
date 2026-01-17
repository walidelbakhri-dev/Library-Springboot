package com.example.library.service;

import com.example.library.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookDto create(BookCreateDto dto);
    BookDto update(Long id, BookUpdateDto dto);
    BookDto getById(Long id);
    void delete(Long id);
    Page<BookDto> search(String q, Pageable pageable);
}