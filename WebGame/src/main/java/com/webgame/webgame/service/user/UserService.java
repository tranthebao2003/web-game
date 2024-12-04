package com.webgame.webgame.service.user;

import com.webgame.webgame.model.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
//    User getUserById(Long id);
    User getUserByEmail(String email);
}
