package br.ufpb.Biblioteca_API.Repositories;

import br.ufpb.Biblioteca_API.Models.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
}
