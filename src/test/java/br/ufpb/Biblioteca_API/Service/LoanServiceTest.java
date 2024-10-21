package br.ufpb.Biblioteca_API.Service;

import br.ufpb.Biblioteca_API.Exception.LoanNotFoundException;
import br.ufpb.Biblioteca_API.Exception.BookNotFoundException;
import br.ufpb.Biblioteca_API.Exception.UserNotFoundException;
import br.ufpb.Biblioteca_API.Models.Loan;
import br.ufpb.Biblioteca_API.Models.Book;
import br.ufpb.Biblioteca_API.Models.User;
import br.ufpb.Biblioteca_API.Repositories.LoanRepository;
import br.ufpb.Biblioteca_API.Repositories.BookRepository;
import br.ufpb.Biblioteca_API.Repositories.UserRepository;
import br.ufpb.Biblioteca_API.Services.LoanService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LoanServiceTest {

    @Mock
    private LoanRepository loanRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private LoanService loanService;

    private Loan loan;
    private User user;
    private Book book;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setName("Test User");

        book = new Book();
        book.setId(1L);
        book.setTitle("Test Book");
        book.setAvailable(true);

        loan = new Loan();
        loan.setId(1L);
        loan.setUser(user);
        loan.setBook(book);
    }

    @Test
    void testGetAllLoans() {
        when(loanRepository.findAll()).thenReturn(Arrays.asList(loan));
        var loans = loanService.getAllLoans();
        assertEquals(1, loans.size());
        assertEquals(loan.getId(), loans.get(0).getId());
    }

    @Test
    void testGetLoanById() {
        when(loanRepository.findById(1L)).thenReturn(Optional.of(loan));
        var foundLoan = loanService.getLoanById(1L);
        assertEquals(loan, foundLoan);
    }

    @Test
    void testGetLoanByIdNotFound() {
        when(loanRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(LoanNotFoundException.class, () -> loanService.getLoanById(1L));
    }

    @Test
    void testCreateLoan() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(loanRepository.save(any(Loan.class))).thenReturn(loan);

        Loan createdLoan = loanService.createLoan(1L, 1L, loan);
        assertNotNull(createdLoan);
        assertEquals(loan.getId(), createdLoan.getId());
        assertFalse(book.isAvailable());
    }

    @Test
    void testCreateLoanUserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> loanService.createLoan(1L, 1L, loan));
    }

    @Test
    void testCreateLoanBookNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(BookNotFoundException.class, () -> loanService.createLoan(1L, 1L, loan));
    }

    @Test
    void testUpdateLoan() {
        when(loanRepository.existsById(1L)).thenReturn(true);
        when(loanRepository.save(any(Loan.class))).thenReturn(loan);

        loan.setUser(user);
        Loan updatedLoan = loanService.updateLoan(1L, loan);
        assertNotNull(updatedLoan);
        assertEquals(loan.getId(), updatedLoan.getId());
    }

    @Test
    void testUpdateLoanNotFound() {
        when(loanRepository.existsById(1L)).thenReturn(false);
        assertThrows(LoanNotFoundException.class, () -> loanService.updateLoan(1L, loan));
    }

    @Test
    void testDeleteLoan() {
        when(loanRepository.existsById(1L)).thenReturn(true);
        doNothing().when(loanRepository).deleteById(1L);

        boolean deleted = loanService.deleteLoan(1L);
        assertTrue(deleted);
        verify(loanRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteLoanNotFound() {
        when(loanRepository.existsById(1L)).thenReturn(false);
        assertThrows(LoanNotFoundException.class, () -> loanService.deleteLoan(1L));
    }

    @Test
    void testReturnBook() {
        when(loanRepository.findById(1L)).thenReturn(Optional.of(loan));
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        String message = loanService.returnBook(1L);
        assertEquals("Book returned successfully!", message);
        assertTrue(book.isAvailable());
        verify(loanRepository, times(1)).delete(loan);
    }

    @Test
    void testReturnBookNotFound() {
        when(loanRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(LoanNotFoundException.class, () -> loanService.returnBook(1L));
    }
}
