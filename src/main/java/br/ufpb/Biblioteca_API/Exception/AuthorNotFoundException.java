package br.ufpb.Biblioteca_API.Exception;

public class AuthorNotFoundException extends RuntimeException {
    public AuthorNotFoundException(String id) {
        super("Author with Id " + id + " not found.");
    }
}
