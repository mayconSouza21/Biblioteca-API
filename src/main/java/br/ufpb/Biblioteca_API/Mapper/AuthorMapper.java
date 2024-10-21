package br.ufpb.Biblioteca_API.Mapper;

import br.ufpb.Biblioteca_API.Dto.AuthorDTO;
import br.ufpb.Biblioteca_API.Models.Author;

public class AuthorMapper {
    public static AuthorDTO toDto(Author author) {
        AuthorDTO dto = new AuthorDTO();
        dto.setId(author.getId());
        dto.setName(author.getName());
        dto.setDateOfBirth(author.getBirthDate());
        dto.setCountryOfOrigin(author.getCountryOfOrigin());
        return dto;
    }

    public static Author toEntity(AuthorDTO dto) {
        Author author = new Author();
        author.setId(dto.getId());
        author.setName(dto.getName());
        author.setBirthDate(dto.getDateOfBirth());
        author.setCountryOfOrigin(dto.getCountryOfOrigin());
        return author;
    }
}
