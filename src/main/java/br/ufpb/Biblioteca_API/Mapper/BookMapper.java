package br.ufpb.Biblioteca_API.Mapper;

import br.ufpb.Biblioteca_API.Dto.BookDTO;
import br.ufpb.Biblioteca_API.Models.Book;
import br.ufpb.Biblioteca_API.Models.Publisher;
import br.ufpb.Biblioteca_API.Models.Author;

public class BookMapper {

    public static BookDTO toDto(Book book) {
        BookDTO dto = new BookDTO();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setPublisherId(book.getPublisher() != null ? book.getPublisher().getId() : null);
        dto.setAuthorId(book.getAuthor() != null ? book.getAuthor().getId() : null);
        dto.setNumberOfPages(book.getNumberOfPages());
        dto.setSummary(book.getSummary());
        dto.setLanguage(book.getLanguage());
        dto.setLiteraryGenre(book.getLiteraryGenre());
        return dto;
    }

    public static Book toEntity(BookDTO dto, Publisher publisher, Author author) {
        Book book = new Book();
        book.setId(dto.getId());
        book.setTitle(dto.getTitle());
        book.setNumberOfPages(dto.getNumberOfPages());
        book.setSummary(dto.getSummary());
        book.setLanguage(dto.getLanguage());
        book.setLiteraryGenre(dto.getLiteraryGenre());

        if (publisher != null) {
            book.setPublisher(publisher);
        }
        if (author != null) {
            book.setAuthor(author);
        }

        return book;
    }

    public static Book toEntity(BookDTO dto) {
        Book book = new Book();
        book.setId(dto.getId());
        book.setTitle(dto.getTitle());
        book.setNumberOfPages(dto.getNumberOfPages());
        book.setSummary(dto.getSummary());
        book.setLanguage(dto.getLanguage());
        book.setLiteraryGenre(dto.getLiteraryGenre());
        return book;
    }
}
