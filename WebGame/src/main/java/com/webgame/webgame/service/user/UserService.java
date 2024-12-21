package com.webgame.webgame.service.user;

import com.webgame.webgame.dto.UserDto;
import com.webgame.webgame.dto.UserLoginDto;
import com.webgame.webgame.dto.gameDto.GameFormDto;
import com.webgame.webgame.model.User;

import java.io.IOException;
import java.util.List;

public interface UserService {
    User getUserById(Long id);
    User getUserByEmail(String email);
    void updateUser(Long id, UserDto userDto) throws IOException;
}
