package br.ufpb.Biblioteca_API.Mapper;

import br.ufpb.Biblioteca_API.Dto.PublisherDTO;
import br.ufpb.Biblioteca_API.Models.Publisher;

public class PublisherMapper {
    public static PublisherDTO toDto(Publisher publisher) {
        PublisherDTO dto = new PublisherDTO();
        dto.setId(publisher.getId());
        dto.setName(publisher.getName());
        dto.setFoundationYear(publisher.getFoundationYear());
        dto.setCountryOfOrigin(publisher.getCountryOfOrigin());
        dto.setWebsite(publisher.getWebsite());
        dto.setPhone(publisher.getPhone());
        return dto;
    }

    public static Publisher toEntity(PublisherDTO dto) {
        Publisher publisher = new Publisher();
        publisher.setId(dto.getId());
        publisher.setName(dto.getName());
        publisher.setFoundationYear(dto.getFoundationYear());
        publisher.setCountryOfOrigin(dto.getCountryOfOrigin());
        publisher.setWebsite(dto.getWebsite());
        publisher.setPhone(dto.getPhone());
        return publisher;
    }
}
