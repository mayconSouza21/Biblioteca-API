package br.ufpb.Biblioteca_API.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "tb_loan")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "loan_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull(message = "The user cannot be null.")
    private User user;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    @NotNull(message = "The book cannot be null.")
    private Book book;

    @Column(nullable = false)
    @NotNull(message = "The loan date cannot be null.")
    @FutureOrPresent(message = "The loan date must be in the present or future.")
    private LocalDate loanDate;

    @Column(nullable = false)
    @NotNull(message = "The return date cannot be null.")
    @FutureOrPresent(message = "The return date must be in the present or future.")
    private LocalDate returnDate;
}
