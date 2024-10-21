package br.ufpb.Biblioteca_API.Services;

import br.ufpb.Biblioteca_API.Exception.BookNotFoundException;
import br.ufpb.Biblioteca_API.Exception.LoanNotFoundException;
import br.ufpb.Biblioteca_API.Exception.UserNotFoundException;
import br.ufpb.Biblioteca_API.Models.Book;
import br.ufpb.Biblioteca_API.Models.Loan;
import br.ufpb.Biblioteca_API.Models.User;
import br.ufpb.Biblioteca_API.Repositories.BookRepository;
import br.ufpb.Biblioteca_API.Repositories.LoanRepository;
import br.ufpb.Biblioteca_API.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoanService {

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }

    public Loan getLoanById(Long id) {
        return loanRepository.findById(id)
                .orElseThrow(() -> new LoanNotFoundException("Loan with ID " + id + " not found."));
    }

    public Loan saveLoan(Loan loan) {
        return loanRepository.save(loan);
    }

    public Loan createLoan(Long userId, Long bookId, Loan loan) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + userId + " not found."));
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException("Book with ID " + bookId + " not found."));

        if (!book.isAvailable()) {
            throw new RuntimeException("Book with ID " + bookId + " is not available for loan.");
        }

        loan.setUser(user);
        loan.setBook(book);
        book.setAvailable(false);
        bookRepository.save(book);

        return saveLoan(loan);
    }

    public Loan updateLoan(Long id, Loan loan) {
        if (!loanRepository.existsById(id)) {
            throw new LoanNotFoundException("Loan with ID " + id + " not found.");
        }
        loan.setId(id);
        return loanRepository.save(loan);
    }

    public boolean deleteLoan(Long id) {
        if (!loanRepository.existsById(id)) {
            throw new LoanNotFoundException("Loan with ID " + id + " not found.");
        }
        loanRepository.deleteById(id);
        return true;
    }

    public String returnBook(Long id) {
        Loan loan = getLoanById(id);
        Book book = loan.getBook();
        book.setAvailable(true);
        bookRepository.save(book);

        loanRepository.delete(loan);

        return "Book returned successfully!";
    }
}
