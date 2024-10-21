package br.ufpb.Biblioteca_API.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name = "tb_book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "The book title is required.")
    @Size(max = 200)
    @Column(name = "title", nullable = false)
    private String title;

    @Size(max = 1000)
    @Column(name = "summary")
    private String summary;

    @Min(value = 1)
    @Column(name = "number_of_pages")
    private int numberOfPages;

    @NotBlank(message = "The language is required.")
    @Size(max = 50)
    @Column(name = "language")
    private String language;

    @NotBlank(message = "The literary genre is required.")
    @Size(max = 100)
    @Column(name = "literary_genre")
    private String literaryGenre;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    @ManyToOne
    @JoinColumn(name = "publisher_id")
    private Publisher publisher;

    @Column(name = "available", nullable = false)
    private boolean available = true;

}
