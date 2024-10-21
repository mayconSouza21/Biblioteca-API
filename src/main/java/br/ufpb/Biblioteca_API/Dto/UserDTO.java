package br.ufpb.Biblioteca_API.Dto;

import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private Integer age;
    private String cpf;
    private String address;
    private String city;
    private String username;
    private String password;
}
