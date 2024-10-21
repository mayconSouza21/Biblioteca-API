package br.ufpb.Biblioteca_API.Service;

import br.ufpb.Biblioteca_API.Models.Author;
import br.ufpb.Biblioteca_API.Repositories.AuthorRepository;
import br.ufpb.Biblioteca_API.Exception.AuthorNotFoundException;
import br.ufpb.Biblioteca_API.Services.AuthorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorService authorService;

    private Author author;

    @BeforeEach
    void setUp() {
        author = new Author();
        author.setId(1L);
        author.setName("Test Author");
        author.setBirthDate(LocalDate.of(1990, 1, 1));
        author.setCountryOfOrigin("Brazil");
    }

    @Test
    void testGetAllAuthors() {
        when(authorRepository.findAll()).thenReturn(Arrays.asList(author));
        List<Author> authors = authorService.getAllAuthors();
        assertEquals(1, authors.size());
        assertEquals(author.getName(), authors.get(0).getName());
    }

    @Test
    void testGetAuthorById() {
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        Author foundAuthor = authorService.getAuthorById(1L);
        assertEquals(author, foundAuthor);
    }

    @Test
    void testGetAuthorByIdNotFound() {
        when(authorRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(AuthorNotFoundException.class, () -> authorService.getAuthorById(1L));
    }

    @Test
    void testSaveAuthor() {
        when(authorRepository.save(any(Author.class))).thenReturn(author);
        Author savedAuthor = authorService.saveAuthor(author);
        assertNotNull(savedAuthor);
        assertEquals(author.getName(), savedAuthor.getName());
    }

    @Test
    void testUpdateAuthor() {
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        when(authorRepository.save(any(Author.class))).thenReturn(author);

        author.setName("Updated Author");
        Author updatedAuthor = authorService.updateAuthor(1L, author);
        assertNotNull(updatedAuthor);
        assertEquals("Updated Author", updatedAuthor.getName());
    }

    @Test
    void testUpdateAuthorNotFound() {
        when(authorRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(AuthorNotFoundException.class, () -> authorService.updateAuthor(1L, author));
    }

    @Test
    void testDeleteAuthor() {
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        doNothing().when(authorRepository).delete(any(Author.class));

        authorService.deleteAuthor(1L);
        verify(authorRepository, times(1)).delete(author);
    }

    @Test
    void testDeleteAuthorNotFound() {
        when(authorRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(AuthorNotFoundException.class, () -> authorService.deleteAuthor(1L));
    }
}
