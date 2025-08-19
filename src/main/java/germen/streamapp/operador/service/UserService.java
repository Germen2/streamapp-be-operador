package germen.streamapp.operador.service;

import germen.streamapp.operador.DTO.SignUpDTO;
import germen.streamapp.operador.mapper.UserMapper;
import germen.streamapp.operador.model.User;
import germen.streamapp.operador.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    public List<User> getUsers(){
        return userRepository.findAll();
    }

    public Optional<User> getUser(Long id){
        return userRepository.findById(id);
    }

    public Optional<User> getUserByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public User saveUser(User user){
        return userRepository.save(user);
    }

    public User updateUser(Long id, User user){
        return userRepository.findById(id).map(existingUser -> {
            userMapper.updateUserFromSource(user, existingUser);
            return userRepository.save(existingUser);
        }).orElseThrow(() -> new RuntimeException("Usuario no encontrado con id " + id));
    }

    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }
}
