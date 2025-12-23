package com.example.library.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "members")
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom est obligatoire")
    @Size(max = 255, message = "Le nom doit contenir au maximum {max} caractères")
    @Column(nullable=false)
    private String name;

    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "L'email est invalide")
    @Size(max = 255, message = "L'email doit contenir au maximum {max} caractères")
    private String email;

    public Member() {}
    public Member(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}