package br.ufpb.Biblioteca_API.Dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class AuthorDTO {
    private Long id;
    private String name;
    private LocalDate birthDate;
    private String countryOfOrigin;

    public LocalDate getDateOfBirth() {
        return birthDate;
    }
    public void setDateOfBirth(LocalDate birthDate) {
        this.birthDate = birthDate;
    }
}
