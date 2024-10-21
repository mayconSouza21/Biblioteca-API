package br.ufpb.Biblioteca_API.Dto;

import lombok.Data;

@Data
public class BookDTO {
    private Long id;
    private String title;
    private Long publisherId;
    private Long authorId;
    private int numberOfPages;
    private String summary;
    private String language;
    private String literaryGenre;
}
