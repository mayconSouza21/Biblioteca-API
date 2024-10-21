package br.ufpb.Biblioteca_API.Services;

import br.ufpb.Biblioteca_API.Exception.BookNotFoundException;
import br.ufpb.Biblioteca_API.Exception.PublisherNotFoundException;
import br.ufpb.Biblioteca_API.Exception.AuthorNotFoundException;
import br.ufpb.Biblioteca_API.Models.Author;
import br.ufpb.Biblioteca_API.Models.Book;
import br.ufpb.Biblioteca_API.Models.Publisher;
import br.ufpb.Biblioteca_API.Repositories.AuthorRepository;
import br.ufpb.Biblioteca_API.Repositories.BookRepository;
import br.ufpb.Biblioteca_API.Repositories.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private PublisherRepository publisherRepository;

    @Autowired
    private AuthorRepository authorRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book with ID " + id + " not found."));
    }

    public Book saveBook(Book book) {
        book.setAvailable(true);
        return bookRepository.save(book);
    }

    public Book updateBook(Long id, Book book) {
        if (!bookRepository.existsById(id)) {
            throw new BookNotFoundException("Book with ID " + id + " not found.");
        }

        book.setId(id);
        return bookRepository.save(book);
    }

    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new BookNotFoundException("Book with ID " + id + " not found.");
        }
        bookRepository.deleteById(id);
    }

    public Author findAuthorById(Long authorId) {
        return authorRepository.findById(authorId)
                .orElseThrow(() -> new AuthorNotFoundException("Author not found with ID " + authorId));
    }

    public Publisher findPublisherById(Long publisherId) {
        return publisherRepository.findById(publisherId)
                .orElseThrow(() -> new PublisherNotFoundException("Publisher not found with ID " + publisherId));
    }

}
