package br.ufpb.Biblioteca_API.Controller;

import br.ufpb.Biblioteca_API.Controllers.PublisherController;
import br.ufpb.Biblioteca_API.Dto.PublisherDTO;
import br.ufpb.Biblioteca_API.Mapper.PublisherMapper;
import br.ufpb.Biblioteca_API.Models.Publisher;
import br.ufpb.Biblioteca_API.Services.PublisherService;
import br.ufpb.Biblioteca_API.Exception.PublisherNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class PublisherControllerTest {

    @InjectMocks
    private PublisherController publisherController;

    @Mock
    private PublisherService publisherService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllPublishers() {

        Publisher publisher1 = new Publisher(1L, "Publisher A", 2000, "Brazil", null, null, null);
        Publisher publisher2 = new Publisher(2L, "Publisher B", 1995, "Brazil", null, null, null);
        when(publisherService.getAllPublishers()).thenReturn(Arrays.asList(publisher1, publisher2));

        ResponseEntity<List<PublisherDTO>> response = publisherController.getAllPublishers();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().size());
        assertEquals("Publisher A", response.getBody().get(0).getName());
    }

    @Test
    void testGetPublisherById() {

        Publisher publisher = new Publisher(1L, "Publisher A", 2000, "Brazil", null, null, null);
        when(publisherService.getPublisherById(anyLong())).thenReturn(publisher);

        ResponseEntity<PublisherDTO> response = publisherController.getPublisherById(1L);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Publisher A", response.getBody().getName());
    }

    @Test
    void testSavePublisher() {

        PublisherDTO publisherDTO = new PublisherDTO();
        publisherDTO.setName("Publisher A");
        publisherDTO.setFoundationYear(2000);
        publisherDTO.setCountryOfOrigin("Brazil");

        Publisher publisher = new Publisher(1L, "Publisher A", 2000, "Brazil", null, null, null);
        when(publisherService.savePublisher(any(Publisher.class))).thenReturn(publisher);

        ResponseEntity<PublisherDTO> response = publisherController.savePublisher(publisherDTO);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Publisher A", response.getBody().getName());
    }

    @Test
    void testUpdatePublisher() {

        PublisherDTO publisherDTO = new PublisherDTO();
        publisherDTO.setName("Updated Publisher");
        publisherDTO.setFoundationYear(2020);
        publisherDTO.setCountryOfOrigin("Brazil");

        Publisher publisher = new Publisher(1L, "Updated Publisher", 2020, "Brazil", null, null, null);
        when(publisherService.updatePublisher(anyLong(), any(Publisher.class))).thenReturn(publisher);

        ResponseEntity<PublisherDTO> response = publisherController.updatePublisher(1L, publisherDTO);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Updated Publisher", response.getBody().getName());
    }

    @Test
    void testDeletePublisher() {

        doNothing().when(publisherService).deletePublisher(anyLong());
        ResponseEntity<Void> response = publisherController.deletePublisher(1L);

        assertEquals(204, response.getStatusCodeValue());
        verify(publisherService, times(1)).deletePublisher(1L);
    }
}
