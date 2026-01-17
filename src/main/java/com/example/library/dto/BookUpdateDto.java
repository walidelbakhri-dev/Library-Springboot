package com.example.library.dto;

import com.example.library.model.BookCondition;
import com.example.library.model.BookStatus;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookUpdateDto {
    @Size(max = 255)
    private String title;

    @Size(max = 255)
    private String author;

    @Size(max = 100)
    private String isbn;

    private Integer year;

    private BookCondition condition;

    private BookStatus status;
}