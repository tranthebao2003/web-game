package com.webgame.webgame.sevice.user;

import com.webgame.webgame.model.User;
import com.webgame.webgame.repository.UserRepository;
import dto.UserLoginDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void save(UserLoginDto userLoginDto) {
        User user = new User(userLoginDto.getEmail(), userLoginDto.getPassword(), userLoginDto.getRole(), userLoginDto.getUsername(), userLoginDto.getPhone());
        userRepository.save(user);
    }
}
