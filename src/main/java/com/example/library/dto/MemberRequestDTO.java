package com.example.library.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class MemberRequestDTO {

    @NotBlank(message = "Le nom est obligatoire")
    @Size(max = 255, message = "Le nom doit contenir au plus {max} caractères")
    private String name;

    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "L'email est invalide")
    @Size(max = 255, message = "L'email doit contenir au plus {max} caractères")
    private String email;

    public MemberRequestDTO() {}
    public MemberRequestDTO(String name, String email) { this.name = name; this.email = email; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}