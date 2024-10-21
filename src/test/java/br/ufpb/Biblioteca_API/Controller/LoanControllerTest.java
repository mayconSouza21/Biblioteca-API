package br.ufpb.Biblioteca_API.Controller;

import br.ufpb.Biblioteca_API.Controllers.LoanController;
import br.ufpb.Biblioteca_API.Dto.LoanDTO;
import br.ufpb.Biblioteca_API.Mapper.LoanMapper;
import br.ufpb.Biblioteca_API.Models.Loan;
import br.ufpb.Biblioteca_API.Services.LoanService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class LoanControllerTest {

    @InjectMocks
    private LoanController loanController;

    @Mock
    private LoanService loanService;

    @Mock
    private LoanMapper loanMapper;

    private Loan loan;
    private LoanDTO loanDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        loan = new Loan();
        loan.setId(1L);
        loan.setLoanDate(LocalDate.now());
        loan.setReturnDate(LocalDate.now().plusDays(10));

        loanDTO = new LoanDTO();
        loanDTO.setUserId(1L);
        loanDTO.setBookId(1L);
        loanDTO.setLoanDate(LocalDate.now());
        loanDTO.setReturnDate(LocalDate.now().plusDays(10));
    }

    @Test
    public void testGetAllLoans() {
        List<Loan> loans = Arrays.asList(loan);
        List<LoanDTO> loanDTOs = Arrays.asList(loanDTO);

        when(loanService.getAllLoans()).thenReturn(loans);
        when(loanMapper.toDtoList(loans)).thenReturn(loanDTOs);

        ResponseEntity<List<LoanDTO>> response = loanController.getAllLoans();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(loanDTOs, response.getBody());
    }

    @Test
    public void testGetLoanById() {
        when(loanService.getLoanById(1L)).thenReturn(loan);
        when(loanMapper.toDto(loan)).thenReturn(loanDTO);

        ResponseEntity<LoanDTO> response = loanController.getLoanById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(loanDTO, response.getBody());
    }

    @Test
    public void testSaveLoan() {
        when(loanMapper.toEntity(loanDTO)).thenReturn(loan);
        when(loanService.createLoan(any(Long.class), any(Long.class), any(Loan.class))).thenReturn(loan);
        when(loanMapper.toDto(loan)).thenReturn(loanDTO);

        ResponseEntity<?> response = loanController.saveLoan(loanDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(loanDTO, response.getBody());
    }

    @Test
    public void testUpdateLoan() {
        when(loanMapper.toEntity(loanDTO)).thenReturn(loan);
        when(loanService.updateLoan(any(Long.class), any(Loan.class))).thenReturn(loan);
        when(loanMapper.toDto(loan)).thenReturn(loanDTO);

        ResponseEntity<LoanDTO> response = loanController.updateLoan(1L, loanDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(loanDTO, response.getBody());
    }

    @Test
    public void testDeleteLoan() {
        ResponseEntity<Void> response = loanController.deleteLoan(1L);
        verify(loanService, times(1)).deleteLoan(1L);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void testReturnBook() {
        when(loanService.returnBook(1L)).thenReturn("Book returned successfully.");

        ResponseEntity<String> response = loanController.returnBook(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Book returned successfully.", response.getBody());
    }
}
