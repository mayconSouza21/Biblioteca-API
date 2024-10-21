package br.ufpb.Biblioteca_API.Exception;

public class LoanNotFoundException extends RuntimeException {
    public LoanNotFoundException(String id) {
        super("Loan with ID: " + id + " not found.");
    }
}
