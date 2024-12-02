package com.webgame.webgame.sevice.user;

import dto.UserLoginDto;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    void save(UserLoginDto userLoginDto);
}
