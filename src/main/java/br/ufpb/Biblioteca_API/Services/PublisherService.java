package br.ufpb.Biblioteca_API.Services;

import br.ufpb.Biblioteca_API.Exception.PublisherNotFoundException;
import br.ufpb.Biblioteca_API.Models.Publisher;
import br.ufpb.Biblioteca_API.Repositories.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PublisherService {

    @Autowired
    private PublisherRepository publisherRepository;

    public List<Publisher> getAllPublishers() {
        return publisherRepository.findAll();
    }

    public Publisher getPublisherById(Long id) {
        return publisherRepository.findById(id)
                .orElseThrow(() -> new PublisherNotFoundException("Publisher with ID " + id + " not found."));
    }

    public Publisher savePublisher(Publisher publisher) {
        return publisherRepository.save(publisher);
    }

    public Publisher updatePublisher(Long id, Publisher publisher) {
        if (!publisherRepository.existsById(id)) {
            throw new PublisherNotFoundException("Publisher with ID " + id + " not found.");
        }

        publisher.setId(id);
        return publisherRepository.save(publisher);
    }

    public void deletePublisher(Long id) {
        if (!publisherRepository.existsById(id)) {
            throw new PublisherNotFoundException("Publisher with ID " + id + " not found.");
        }

        publisherRepository.deleteById(id);
    }
}
