package com.example.library.dto;

import com.example.library.model.BookCondition;
import com.example.library.model.BookStatus;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookDto {
    private Long id;
    private String title;
    private String author;
    private String isbn;
    private Integer year;
    private String createdAt;
    private BookCondition condition;
    private BookStatus status;
}