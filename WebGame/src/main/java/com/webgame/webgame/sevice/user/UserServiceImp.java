package com.webgame.webgame.sevice.user;

import com.webgame.webgame.model.User;
import com.webgame.webgame.repository.UserRepository;
import com.webgame.webgame.dto.UserLoginDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void save(UserLoginDto userLoginDto) {
        User user = new User(userLoginDto.getEmail(), passwordEncoder.encode(userLoginDto.getPassword()), userLoginDto.getRole(), userLoginDto.getUsername(), userLoginDto.getPhone());
        userRepository.save(user);
    }
}
