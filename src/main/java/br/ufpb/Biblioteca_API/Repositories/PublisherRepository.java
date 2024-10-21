package br.ufpb.Biblioteca_API.Repositories;

import br.ufpb.Biblioteca_API.Models.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PublisherRepository extends JpaRepository<Publisher, Long> {
}
