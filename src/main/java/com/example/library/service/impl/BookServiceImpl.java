package com.example.library.service.impl;

import com.example.library.dto.*;
import com.example.library.entity.Book;
import com.example.library.exception.NotFoundException;
import com.example.library.mapper.BookMapper;
import com.example.library.repository.BookRepository;
import com.example.library.repository.Specification.BookSpecification;
import com.example.library.service.BookService;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BookServiceImpl implements BookService {

    private final BookRepository repo;
    private final BookMapper mapper;

    public BookServiceImpl(BookRepository repo, BookMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    @Override
    public BookDto create(BookCreateDto dto) {
        Book entity = mapper.toEntity(dto);
        if (dto.getCondition() != null) entity.setCondition(dto.getCondition());
        Book saved = repo.save(entity);
        return mapper.toDto(saved);
    }

    @Override
    public BookDto update(Long id, BookUpdateDto dto) {
        Book existing = repo.findById(id).orElseThrow(() -> new NotFoundException("Livre non trouvé"));
        mapper.updateEntityFromDto(dto, existing);
        Book saved = repo.save(existing);
        return mapper.toDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public BookDto getById(Long id) {
        return repo.findById(id).map(mapper::toDto).orElseThrow(() -> new NotFoundException("Livre non trouvé"));
    }

    @Override
    public void delete(Long id) {
        if (!repo.existsById(id)) throw new NotFoundException("Livre non trouvé");
        repo.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BookDto> search(String q, Pageable pageable) {
        if (q == null || q.isBlank()) {
            return repo.findAll(pageable).map(mapper::toDto);
        }
        Specification<Book> spec = Specification.where(BookSpecification.titleContainsIgnoreCase(q))
                .or(BookSpecification.authorContainsIgnoreCase(q));
        return repo.findAll(spec, pageable).map(mapper::toDto);
    }
}