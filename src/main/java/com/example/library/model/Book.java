package com.example.library.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "books")
public class Book {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le titre est obligatoire")
    @Size(max = 255, message = "Le titre doit contenir au maximum {max} caractères")
    @Column(nullable=false)
    private String title;

    @Size(max = 255, message = "Le nom de l'auteur doit contenir au maximum {max} caractères")
    private String author;

    @NotBlank(message = "L'ISBN est obligatoire")
    @Size(max = 255, message = "L'ISBN doit contenir au maximum {max} caractères")
    @Column(unique = true)
    private String isbn;


    public Book() {}
    public Book(String title, String author, String isbn) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
    }


    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id; }
    public String getTitle() { return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public String getIsbn() {
        return isbn;
    }
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
}