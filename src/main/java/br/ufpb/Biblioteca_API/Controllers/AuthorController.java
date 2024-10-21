package br.ufpb.Biblioteca_API.Controllers;

import br.ufpb.Biblioteca_API.Dto.AuthorDTO;
import br.ufpb.Biblioteca_API.Mapper.AuthorMapper;
import br.ufpb.Biblioteca_API.Models.Author;
import br.ufpb.Biblioteca_API.Services.AuthorService;
import br.ufpb.Biblioteca_API.Exception.AuthorNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/author")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @GetMapping
    public ResponseEntity<List<AuthorDTO>> getAllAuthors() {
        List<Author> authors = authorService.getAllAuthors();
        List<AuthorDTO> authorDTOs = authors.stream()
                .map(AuthorMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(authorDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorDTO> getAuthorById(@PathVariable Long id) {
        Author author = authorService.getAuthorById(id);
        return ResponseEntity.ok(AuthorMapper.toDto(author));
    }

    @PostMapping
    public ResponseEntity<AuthorDTO> saveAuthor(@Valid @RequestBody AuthorDTO authorDTO) {
        Author author = AuthorMapper.toEntity(authorDTO);
        Author savedAuthor = authorService.saveAuthor(author);
        return ResponseEntity.ok(AuthorMapper.toDto(savedAuthor));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuthorDTO> updateAuthor(@PathVariable Long id, @Valid @RequestBody AuthorDTO authorDTO) {
        Author author = AuthorMapper.toEntity(authorDTO);
        authorService.getAuthorById(id);

        author.setId(id);
        Author updatedAuthor = authorService.updateAuthor(id, author);
        return ResponseEntity.ok(AuthorMapper.toDto(updatedAuthor));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) {
        authorService.getAuthorById(id);
        authorService.deleteAuthor(id);
        return ResponseEntity.noContent().build();
    }
}
