package br.ufpb.Biblioteca_API.Services;

import br.ufpb.Biblioteca_API.Dto.UserDTO;
import br.ufpb.Biblioteca_API.Exception.UserNotFoundException;
import br.ufpb.Biblioteca_API.Mapper.UserMapper;
import br.ufpb.Biblioteca_API.Models.User;
import br.ufpb.Biblioteca_API.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + id + " not found."));
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(Long id, UserDTO userDTO) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("User with ID " + id + " not found.");
        }

        User user = UserMapper.toEntity(userDTO);
        user.setId(id);
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("User with ID " + id + " not found.");
        }
        userRepository.deleteById(id);
    }
}
