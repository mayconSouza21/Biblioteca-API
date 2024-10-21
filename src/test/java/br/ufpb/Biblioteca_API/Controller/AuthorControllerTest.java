package br.ufpb.Biblioteca_API.Controller;

import br.ufpb.Biblioteca_API.Controllers.AuthorController;
import br.ufpb.Biblioteca_API.Dto.AuthorDTO;
import br.ufpb.Biblioteca_API.Mapper.AuthorMapper;
import br.ufpb.Biblioteca_API.Models.Author;
import br.ufpb.Biblioteca_API.Services.AuthorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AuthorControllerTest {

    @InjectMocks
    private AuthorController authorController;

    @Mock
    private AuthorService authorService;

    private Author author;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        author = new Author();
        author.setId(1L);

        author.setName("Test Author");
        author.setBirthDate(LocalDate.of(1990, 1, 1));
        author.setCountryOfOrigin("Brazil");
    }

    @Test
    void getAllAuthors_ShouldReturnListOfAuthors() {
        when(authorService.getAllAuthors()).thenReturn(Arrays.asList(author));

        ResponseEntity<List<AuthorDTO>> response = authorController.getAllAuthors();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals("Test Author", response.getBody().get(0).getName());
    }

    @Test
    void getAuthorById_ShouldReturnAuthor() {

        when(authorService.getAuthorById(1L)).thenReturn(author);

        ResponseEntity<AuthorDTO> response = authorController.getAuthorById(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Test Author", response.getBody().getName());
    }

    @Test
    void saveAuthor_ShouldCreateNewAuthor() {
        AuthorDTO authorDTO = AuthorMapper.toDto(author);
        when(authorService.saveAuthor(any(Author.class))).thenReturn(author);

        ResponseEntity<AuthorDTO> response = authorController.saveAuthor(authorDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Test Author", response.getBody().getName());
    }

    @Test
    void updateAuthor_ShouldUpdateExistingAuthor() {
        AuthorDTO authorDTO = AuthorMapper.toDto(author);
        when(authorService.updateAuthor(any(Long.class), any(Author.class))).thenReturn(author);
        when(authorService.getAuthorById(1L)).thenReturn(author);

        ResponseEntity<AuthorDTO> response = authorController.updateAuthor(1L, authorDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Test Author", response.getBody().getName());
    }

    @Test
    void deleteAuthor_ShouldDeleteExistingAuthor() {

        when(authorService.getAuthorById(1L)).thenReturn(author);
        ResponseEntity<Void> response = authorController.deleteAuthor(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(authorService, times(1)).deleteAuthor(1L);
    }
}
