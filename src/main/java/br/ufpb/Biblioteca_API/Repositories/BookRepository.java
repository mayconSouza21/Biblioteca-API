package br.ufpb.Biblioteca_API.Repositories;

import br.ufpb.Biblioteca_API.Models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
}
