package br.ufpb.Biblioteca_API.Mapper;

import br.ufpb.Biblioteca_API.Dto.LoanDTO;
import br.ufpb.Biblioteca_API.Models.Loan;
import br.ufpb.Biblioteca_API.Models.User;
import br.ufpb.Biblioteca_API.Models.Book;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class LoanMapper {

    public LoanDTO toDto(Loan loan) {
        LoanDTO dto = new LoanDTO();
        dto.setId(loan.getId());
        dto.setUserId(loan.getUser() != null ? loan.getUser().getId() : null);
        dto.setBookId(loan.getBook() != null ? loan.getBook().getId() : null);
        dto.setLoanDate(loan.getLoanDate());
        dto.setReturnDate(loan.getReturnDate());
        return dto;
    }

    public Loan toEntity(LoanDTO dto, User user, Book book) {
        Loan loan = new Loan();
        loan.setId(dto.getId());
        loan.setUser(user);
        loan.setBook(book);
        loan.setLoanDate(dto.getLoanDate());
        loan.setReturnDate(dto.getReturnDate());
        return loan;
    }

    public Loan toEntity(LoanDTO dto) {
        Loan loan = new Loan();
        loan.setId(dto.getId());
        loan.setLoanDate(dto.getLoanDate());
        loan.setReturnDate(dto.getReturnDate());
        return loan;
    }

    public List<LoanDTO> toDtoList(List<Loan> loans) {
        return loans.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
