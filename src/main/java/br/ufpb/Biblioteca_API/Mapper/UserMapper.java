package br.ufpb.Biblioteca_API.Mapper;

import br.ufpb.Biblioteca_API.Dto.UserDTO;
import br.ufpb.Biblioteca_API.Models.User;

public class UserMapper {
    public static UserDTO toDto(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setAge(user.getAge());
        dto.setCpf(user.getCpf());
        dto.setAddress(user.getAddress());
        dto.setCity(user.getCity());
        dto.setUsername(user.getUsername());
        dto.setPassword(user.getPassword());
        return dto;
    }

    public static User toEntity(UserDTO dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setAge(dto.getAge());
        user.setCpf(dto.getCpf());
        user.setAddress(dto.getAddress());
        user.setCity(dto.getCity());
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        return user;
    }
}
