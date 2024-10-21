package br.ufpb.Biblioteca_API.Controllers;

import br.ufpb.Biblioteca_API.Dto.LoanDTO;
import br.ufpb.Biblioteca_API.Mapper.LoanMapper;
import br.ufpb.Biblioteca_API.Models.Loan;
import br.ufpb.Biblioteca_API.Services.LoanService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loan")
public class LoanController {

    @Autowired
    private LoanService loanService;

    @Autowired
    private LoanMapper loanMapper;

    @GetMapping
    public ResponseEntity<List<LoanDTO>> getAllLoans() {
        List<Loan> loans = loanService.getAllLoans();
        List<LoanDTO> loanDTOs = loanMapper.toDtoList(loans);
        return ResponseEntity.ok(loanDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoanDTO> getLoanById(@PathVariable Long id) {
        Loan loan = loanService.getLoanById(id);
        LoanDTO loanDTO = loanMapper.toDto(loan);
        return ResponseEntity.ok(loanDTO);
    }

    @PostMapping
    public ResponseEntity<?> saveLoan(@Valid @RequestBody LoanDTO loanDTO) {
        try {
            Loan loan = loanMapper.toEntity(loanDTO);
            Loan savedLoan = loanService.createLoan(
                    loanDTO.getUserId(),
                    loanDTO.getBookId(),
                    loan
            );

            LoanDTO savedLoanDTO = loanMapper.toDto(savedLoan);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedLoanDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<LoanDTO> updateLoan(
            @PathVariable Long id,
            @Valid @RequestBody LoanDTO loanDTO) {

        Loan loan = loanMapper.toEntity(loanDTO);
        Loan updatedLoan = loanService.updateLoan(id, loan);
        LoanDTO updatedLoanDTO = loanMapper.toDto(updatedLoan);
        return ResponseEntity.ok(updatedLoanDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLoan(@PathVariable Long id) {
        loanService.deleteLoan(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/return")
    public ResponseEntity<String> returnBook(@PathVariable Long id) {
        String message = loanService.returnBook(id);
        return ResponseEntity.ok(message);
    }
}
