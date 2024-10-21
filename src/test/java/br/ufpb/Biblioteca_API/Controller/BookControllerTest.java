package br.ufpb.Biblioteca_API.Controller;

import br.ufpb.Biblioteca_API.Controllers.BookController;
import br.ufpb.Biblioteca_API.Dto.BookDTO;
import br.ufpb.Biblioteca_API.Models.Author;
import br.ufpb.Biblioteca_API.Models.Publisher;
import br.ufpb.Biblioteca_API.Models.Book;
import br.ufpb.Biblioteca_API.Services.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.Arrays;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.*;

public class BookControllerTest {

    private MockMvc mockMvc;

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
    }

    @Test
    public void testGetAllBooks() throws Exception {
        Book book = new Book();
        book.setTitle("Example Book");

        when(bookService.getAllBooks()).thenReturn(Arrays.asList(book));

        mockMvc.perform(get("/api/Book")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title", is("Example Book")));

        verify(bookService, times(1)).getAllBooks();
    }

    @Test
    public void testGetBookById() throws Exception {
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Example Book");

        when(bookService.getBookById(anyLong())).thenReturn(book);

        mockMvc.perform(get("/api/Book/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("Example Book")));

        verify(bookService, times(1)).getBookById(1L);
    }

    @Test
    public void testSaveBook() throws Exception {
        Book book = new Book();
        book.setTitle("Example Book");
        book.setId(1L);

        Author author = new Author();
        Publisher publisher = new Publisher();

        BookDTO bookDTO = new BookDTO();
        bookDTO.setTitle("Example Book");
        bookDTO.setAuthorId(1L);
        bookDTO.setPublisherId(1L);

        when(bookService.findAuthorById(anyLong())).thenReturn(author);
        when(bookService.findPublisherById(anyLong())).thenReturn(publisher);
        when(bookService.saveBook(any(Book.class))).thenReturn(book);

        mockMvc.perform(post("/api/Book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"Example Book\", \"authorId\": 1, \"publisherId\": 1}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("Example Book")));

        verify(bookService, times(1)).saveBook(any(Book.class));
    }

    @Test
    public void testUpdateBook() throws Exception {
        Book book = new Book();
        book.setTitle("Updated Book");

        when(bookService.updateBook(anyLong(), any(Book.class))).thenReturn(book);

        mockMvc.perform(put("/api/Book/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"Updated Book\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("Updated Book")));

        verify(bookService, times(1)).updateBook(anyLong(), any(Book.class));
    }

    @Test
    public void testDeleteBook() throws Exception {
        doNothing().when(bookService).deleteBook(anyLong());

        mockMvc.perform(delete("/api/Book/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(bookService, times(1)).deleteBook(1L);
    }
}
