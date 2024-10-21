package br.ufpb.Biblioteca_API.Controllers;

import br.ufpb.Biblioteca_API.Dto.PublisherDTO;
import br.ufpb.Biblioteca_API.Mapper.PublisherMapper;
import br.ufpb.Biblioteca_API.Models.Publisher;
import br.ufpb.Biblioteca_API.Services.PublisherService;
import br.ufpb.Biblioteca_API.Exception.PublisherNotFoundException;
import jakarta.validation.Valid; // Import for validation
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/publisher")
public class PublisherController {

    @Autowired
    private PublisherService publisherService;

    @GetMapping
    public ResponseEntity<List<PublisherDTO>> getAllPublishers() {
        List<Publisher> publishers = publisherService.getAllPublishers();
        List<PublisherDTO> publisherDTOs = publishers.stream()
                .map(PublisherMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(publisherDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PublisherDTO> getPublisherById(@PathVariable Long id) {
        Publisher publisher = publisherService.getPublisherById(id);
        return ResponseEntity.ok(PublisherMapper.toDto(publisher));
    }

    @PostMapping
    public ResponseEntity<PublisherDTO> savePublisher(@Valid @RequestBody PublisherDTO publisherDTO) {
        // Applies validation to the DTO
        Publisher publisher = PublisherMapper.toEntity(publisherDTO);
        Publisher savedPublisher = publisherService.savePublisher(publisher);
        return ResponseEntity.ok(PublisherMapper.toDto(savedPublisher));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PublisherDTO> updatePublisher(@PathVariable Long id, @Valid @RequestBody PublisherDTO publisherDTO) {
        // Applies validation to the DTO
        Publisher updatedPublisher = PublisherMapper.toEntity(publisherDTO);
        updatedPublisher.setId(id);
        updatedPublisher = publisherService.updatePublisher(id, updatedPublisher);
        return ResponseEntity.ok(PublisherMapper.toDto(updatedPublisher));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePublisher(@PathVariable Long id) {
        publisherService.deletePublisher(id);
        return ResponseEntity.noContent().build();
    }
}
