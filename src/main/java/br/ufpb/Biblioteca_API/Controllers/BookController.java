package br.ufpb.Biblioteca_API.Controllers;

import br.ufpb.Biblioteca_API.Dto.BookDTO;
import br.ufpb.Biblioteca_API.Exception.BookNotFoundException;
import br.ufpb.Biblioteca_API.Mapper.BookMapper;
import br.ufpb.Biblioteca_API.Models.Author;
import br.ufpb.Biblioteca_API.Models.Publisher;
import br.ufpb.Biblioteca_API.Models.Book;
import br.ufpb.Biblioteca_API.Services.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        List<Book> books = bookService.getAllBooks();
        List<BookDTO> bookDTOs = books.stream()
                .map(BookMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(bookDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable Long id) {
        Book book = bookService.getBookById(id);
        return ResponseEntity.ok(BookMapper.toDto(book));
    }

    @PostMapping
    public ResponseEntity<BookDTO> saveBook(@Valid @RequestBody BookDTO bookDTO) {

        Author author = bookService.findAuthorById(bookDTO.getAuthorId());
        Publisher publisher = bookService.findPublisherById(bookDTO.getPublisherId());

        Book book = BookMapper.toEntity(bookDTO, publisher, author);
        Book savedBook = bookService.saveBook(book);
        return ResponseEntity.ok(BookMapper.toDto(savedBook));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDTO> updateBook(@PathVariable Long id, @Valid @RequestBody BookDTO bookDTO) {
        Book updatedBook = bookService.updateBook(id, BookMapper.toEntity(bookDTO));
        return ResponseEntity.ok(BookMapper.toDto(updatedBook));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<String> handleBookNotFoundException(BookNotFoundException ex) {
        return ResponseEntity.status(404).body(ex.getMessage());
    }
}
