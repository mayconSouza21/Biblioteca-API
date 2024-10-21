package br.ufpb.Biblioteca_API.Service;

import br.ufpb.Biblioteca_API.Exception.PublisherNotFoundException;
import br.ufpb.Biblioteca_API.Models.Publisher;
import br.ufpb.Biblioteca_API.Repositories.PublisherRepository;
import br.ufpb.Biblioteca_API.Services.PublisherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PublisherServiceTest {

    @Mock
    private PublisherRepository publisherRepository;

    @InjectMocks
    private PublisherService publisherService;

    private Publisher publisher;

    @BeforeEach
    void setUp() {
        publisher = new Publisher();
        publisher.setId(1L);
        publisher.setName("Test Publisher");
    }

    @Test
    void testGetAllPublishers() {
        when(publisherRepository.findAll()).thenReturn(Arrays.asList(publisher));
        List<Publisher> publishers = publisherService.getAllPublishers();
        assertEquals(1, publishers.size());
        assertEquals(publisher.getName(), publishers.get(0).getName());
    }

    @Test
    void testGetPublisherById() {
        when(publisherRepository.findById(1L)).thenReturn(Optional.of(publisher));
        Publisher foundPublisher = publisherService.getPublisherById(1L);
        assertEquals(publisher, foundPublisher);
    }

    @Test
    void testGetPublisherByIdNotFound() {
        when(publisherRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(PublisherNotFoundException.class, () -> publisherService.getPublisherById(1L));
    }

    @Test
    void testSavePublisher() {
        when(publisherRepository.save(any(Publisher.class))).thenReturn(publisher);
        Publisher savedPublisher = publisherService.savePublisher(publisher);
        assertNotNull(savedPublisher);
        assertEquals(publisher.getName(), savedPublisher.getName());
    }

    @Test
    void testUpdatePublisher() {
        when(publisherRepository.existsById(1L)).thenReturn(true);
        when(publisherRepository.save(any(Publisher.class))).thenReturn(publisher);

        publisher.setName("Updated Publisher");
        Publisher updatedPublisher = publisherService.updatePublisher(1L, publisher);
        assertNotNull(updatedPublisher);
        assertEquals("Updated Publisher", updatedPublisher.getName());
    }

    @Test
    void testUpdatePublisherNotFound() {
        when(publisherRepository.existsById(1L)).thenReturn(false);
        assertThrows(PublisherNotFoundException.class, () -> publisherService.updatePublisher(1L, publisher));
    }

    @Test
    void testDeletePublisher() {
        when(publisherRepository.existsById(1L)).thenReturn(true);
        doNothing().when(publisherRepository).deleteById(1L);

        publisherService.deletePublisher(1L);
        verify(publisherRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeletePublisherNotFound() {
        when(publisherRepository.existsById(1L)).thenReturn(false);
        assertThrows(PublisherNotFoundException.class, () -> publisherService.deletePublisher(1L));
    }
}
