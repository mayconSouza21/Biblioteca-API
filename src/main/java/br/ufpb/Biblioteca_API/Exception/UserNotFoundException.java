package br.ufpb.Biblioteca_API.Exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String id) {
        super("User with ID " + id + " not found.");
    }
}
