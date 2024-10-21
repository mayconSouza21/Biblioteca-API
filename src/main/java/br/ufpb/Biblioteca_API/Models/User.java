package br.ufpb.Biblioteca_API.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@Entity
@Table(name = "tb_user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @NotBlank(message = "The user's name is required.")
    @Size(min = 4)
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "The email is required.")
    @Email(message = "The email must be valid.")
    @Column(nullable = false, unique = true)
    private String email;

    @NotNull(message = "The age is required.")
    @Column(nullable = false)
    private Integer age;

    @NotBlank(message = "The CPF is required.")
    @Pattern(regexp = "\\d{11}", message = "The CPF must contain exactly 11 digits and cannot have letters.")
    @Column(nullable = false, unique = true)
    private String cpf;

    @NotBlank(message = "The address is required.")
    @Column(nullable = false)
    private String address;

    @NotBlank(message = "The city is required.")
    @Column(nullable = false)
    private String city;

    @NotBlank(message = "The username is required.")
    @Size(min = 5, max = 20)
    @Column(nullable = false, unique = true)
    private String username;

    @NotBlank(message = "The password is required.")
    @Size(min = 8)
    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "user")
    private List<Loan> loans;
}
