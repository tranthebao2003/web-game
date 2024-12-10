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
    @PersistenceContext
    private EntityManager entityManagerUser;

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

    // Chuyển dữ liệu từ GameFromDto mà nó đã nhận trước đó vào cho game
//    public User getUserInfo(User user, UserLoginDto userLoginDto) throws IOException {
//        user.setFullName(userLoginDto.getUsername());
//        user.setEmail(userLoginDto.getEmail());
//        user.setPhone(userLoginDto.getPhone());
//
//        return user;
//    }
    @Override
    public void updateUser(String email, UserDto userDto) throws IOException {
        // Tìm người dùng bằng email
        User user = getUserByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("Không tìm thấy người dùng với email: " + email);
        }

        System.out.println("Người dùng trước cập nhật: " + user);
        // Cập nhật các thông tin từ DTO vào entity
//        user.setFullName(userLoginDto.getUsername());
//        user.setPhone(userLoginDto.getPhone());
//        user.setEmail(userLoginDto.getEmail());

        user.setFullName(userDto.getUsername());
        user.setPhone(userDto.getPhone());
        user.setEmail(userDto.getEmail());
        System.out.println("Người dùng sau cập nhật: " + user);

        // Lưu lại thay đổi
        userRepository.save(user);
        System.out.println("Đã lưu người dùng vào cơ sở dữ liệu.");
    }
}
