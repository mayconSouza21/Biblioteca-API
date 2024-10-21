package br.ufpb.Biblioteca_API.Exception;

public class PublisherNotFoundException extends RuntimeException {
    public PublisherNotFoundException(String id) {
        super("Publisher with ID " + id + " not found.");
    }
}
