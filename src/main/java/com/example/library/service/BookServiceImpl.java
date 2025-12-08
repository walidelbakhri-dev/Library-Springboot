package com.example.library.service;

import com.example.library.dto.BookRequestDTO;
import com.example.library.dto.BookResponseDTO;
import com.example.library.Mapper.BookMapper;
import com.example.library.model.Book;
import com.example.library.Repository.BookRepository;
import com.example.library.service.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageImpl;

import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    public BookServiceImpl(BookRepository bookRepository) { this.bookRepository = bookRepository; }

    @Override
    public BookResponseDTO createBook(BookRequestDTO dto) {
        Book book = BookMapper.toEntity(dto);
        Book saved = bookRepository.save(book);
        return BookMapper.toDto(saved);
    }

    @Override
    public BookResponseDTO getBookById(Long id) {
        return bookRepository.findById(id).map(BookMapper::toDto).orElse(null);
    }

    @Override
    public Page<BookResponseDTO> getAllBooks(Pageable pageable) {
        Page<Book> page = bookRepository.findAll(pageable);
        return new PageImpl<>(page.getContent().stream().map(BookMapper::toDto).collect(Collectors.toList()), pageable, page.getTotalElements());
    }

    @Override
    public BookResponseDTO updateBook(Long id, BookRequestDTO dto) {
        return bookRepository.findById(id).map(book -> {
            BookMapper.updateEntity(book, dto);
            return BookMapper.toDto(bookRepository.save(book));
        }).orElse(null);
    }

    @Override
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
}