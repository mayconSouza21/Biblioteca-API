package br.ufpb.Biblioteca_API.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;

@Data
@Entity
@Table(name = "tb_publisher")
@NoArgsConstructor
@AllArgsConstructor
public class Publisher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "publisher_id")
    private Long id;

    @NotBlank(message = "The publisher's name is required")
    @Size(min = 10, max = 50)
    @Column(nullable = false)
    private String name;

    @Min(value = 1440, message = "The foundation year must be greater than or equal to 1440")
    @Max(value = 2024, message = "The foundation year cannot be in the future")
    @Column(name = "foundation_year")
    private Integer foundationYear;

    @NotBlank(message = "The country of origin is required")
    @Size(max = 50)
    @Column(name = "country_of_origin")
    private String countryOfOrigin;

    @Pattern(regexp = "^(http(s)?://)?(www\\.)?[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}(/.*)?$",
            message = "The website URL must be valid")
    @Column
    private String website;

    @Pattern(regexp = "^\\+?[0-9. ()-]{7,25}$", message = "The phone number must be valid")
    @Column
    private String phone;

    @OneToMany(mappedBy = "publisher")
    private List<Book> books;
}
