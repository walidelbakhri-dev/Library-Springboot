package com.example.library.service.impl;

import com.example.library.dto.BookRequestDTO;
import com.example.library.dto.BookResponseDTO;
import com.example.library.exception.ConflictException;
import com.example.library.exception.ResourceNotFoundException;
import com.example.library.Mapper.BookMapper;
import com.example.library.model.Book;
import com.example.library.Repository.BookRepository;
import com.example.library.service.BookService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageImpl;

import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    public BookServiceImpl(BookRepository bookRepository) { this.bookRepository = bookRepository; }

    @Override
    public BookResponseDTO createBook(BookRequestDTO dto) {
        // vérifie doublon ISBN -> conflit
        if (dto.getIsbn() != null && bookRepository.findByIsbn(dto.getIsbn()).isPresent()) {
            throw new ConflictException("Book with ISBN " + dto.getIsbn() + " already exists");
        }
        Book saved = bookRepository.save(BookMapper.toEntity(dto));
        return BookMapper.toDto(saved);
    }

    @Override
    public BookResponseDTO getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id " + id));
        return BookMapper.toDto(book);
    }

    @Override
    public Page<BookResponseDTO> getAllBooks(Pageable pageable) {
        Page<Book> page = bookRepository.findAll(pageable);
        return new PageImpl<>(
                page.getContent().stream().map(BookMapper::toDto).collect(Collectors.toList()),
                pageable,
                page.getTotalElements()
        );
    }

    @Override
    public BookResponseDTO updateBook(Long id, BookRequestDTO dto) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot update — book not found with id " + id));
        BookMapper.updateEntity(book, dto);
        return BookMapper.toDto(bookRepository.save(book));
    }

    @Override
    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new ResourceNotFoundException("Cannot delete — book not found with id " + id);
        }
        bookRepository.deleteById(id);
    }
}