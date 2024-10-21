package br.ufpb.Biblioteca_API.Dto;

import lombok.Data;

@Data
public class PublisherDTO {
    private Long id;
    private String name;
    private Integer yearOfEstablishment;
    private String countryOfOrigin;
    private String website;
    private String phone;

    public Integer getFoundationYear() {
        return yearOfEstablishment;
    }

    public void setFoundationYear(Integer yearOfEstablishment) {
        this.yearOfEstablishment = yearOfEstablishment;
    }
}
