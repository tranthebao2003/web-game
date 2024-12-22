package com.webgame.webgame.service.user;

import com.webgame.webgame.dto.UserDto;
import com.webgame.webgame.dto.UserLoginDto;
import com.webgame.webgame.dto.gameDto.GameFormDto;
import com.webgame.webgame.model.Game;
import com.webgame.webgame.model.User;
import com.webgame.webgame.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class UserServiceImp implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void updateUser(Long id, UserDto userDto) throws IOException {
        User user = userRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Không tìm thấy người dùng với ID: " + id)
        );
        user.setFullName(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPhone(userDto.getPhone());
        userRepository.save(user);
    }
}
