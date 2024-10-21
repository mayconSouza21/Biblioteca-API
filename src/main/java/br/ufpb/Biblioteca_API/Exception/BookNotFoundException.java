package br.ufpb.Biblioteca_API.Exception;

public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(String id) {
        super("Book with ID: " + id + " not found.");
    }
}
