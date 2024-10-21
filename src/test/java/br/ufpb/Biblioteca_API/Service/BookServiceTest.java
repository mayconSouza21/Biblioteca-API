package br.ufpb.Biblioteca_API.Service;

import br.ufpb.Biblioteca_API.Exception.AuthorNotFoundException;
import br.ufpb.Biblioteca_API.Exception.PublisherNotFoundException;
import br.ufpb.Biblioteca_API.Exception.BookNotFoundException;
import br.ufpb.Biblioteca_API.Models.Author;
import br.ufpb.Biblioteca_API.Models.Publisher;
import br.ufpb.Biblioteca_API.Models.Book;
import br.ufpb.Biblioteca_API.Repositories.AuthorRepository;
import br.ufpb.Biblioteca_API.Repositories.PublisherRepository;
import br.ufpb.Biblioteca_API.Repositories.BookRepository;
import br.ufpb.Biblioteca_API.Services.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BookServiceTest {

    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private PublisherRepository publisherRepository;

    @Mock
    private AuthorRepository authorRepository;

    private Book book;
    private Publisher publisher;
    private Author author;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        publisher = new Publisher();
        publisher.setId(1L);
        publisher.setName("Test Publisher");

        author = new Author();
        author.setId(1L);
        author.setName("Test Author");

        book = new Book();
        book.setId(1L);
        book.setTitle("Test Book");
        book.setSummary("Test Book Summary");
        book.setPublisher(publisher);
        book.setAuthor(author);
    }

    @Test
    void testGetAllBooks() {
        bookService.getAllBooks();
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    void testGetBookById_Existing() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        Book found = bookService.getBookById(1L);
        assertEquals("Test Book", found.getTitle());
    }

    @Test
    void testGetBookById_NonExisting() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(BookNotFoundException.class, () -> bookService.getBookById(1L));
    }

    @Test
    void testSaveBook() {
        when(bookRepository.save(book)).thenReturn(book);
        Book saved = bookService.saveBook(book);
        assertNotNull(saved);
        assertEquals("Test Book", saved.getTitle());
    }

    @Test
    void testUpdateBook_Existing() {
        when(bookRepository.existsById(1L)).thenReturn(true);
        when(bookRepository.save(book)).thenReturn(book);

        Book updated = bookService.updateBook(1L, book);
        assertEquals(book.getTitle(), updated.getTitle());
    }

    @Test
    void testUpdateBook_NonExisting() {
        when(bookRepository.existsById(1L)).thenReturn(false);
        assertThrows(BookNotFoundException.class, () -> bookService.updateBook(1L, book));
    }

    @Test
    void testDeleteBook_Existing() {
        when(bookRepository.existsById(1L)).thenReturn(true);
        bookService.deleteBook(1L);
        verify(bookRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteBook_NonExisting() {
        when(bookRepository.existsById(1L)).thenReturn(false);
        assertThrows(BookNotFoundException.class, () -> bookService.deleteBook(1L));
    }

    @Test
    void testFindAuthorById_Existing() {
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        Author found = bookService.findAuthorById(1L);
        assertEquals("Test Author", found.getName());
    }

    @Test
    void testFindAuthorById_NonExisting() {
        when(authorRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(AuthorNotFoundException.class, () -> bookService.findAuthorById(1L));
    }

    @Test
    void testFindPublisherById_Existing() {
        when(publisherRepository.findById(1L)).thenReturn(Optional.of(publisher));
        Publisher found = bookService.findPublisherById(1L);
        assertEquals("Test Publisher", found.getName());
    }

    @Test
    void testFindPublisherById_NonExisting() {
        when(publisherRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(PublisherNotFoundException.class, () -> bookService.findPublisherById(1L));
    }
}
