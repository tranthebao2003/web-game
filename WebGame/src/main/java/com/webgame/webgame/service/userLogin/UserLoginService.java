package com.webgame.webgame.service.userLogin;

import com.webgame.webgame.dto.UserLoginDto;
import org.springframework.stereotype.Service;

@Service
public interface UserLoginService {

    void save(UserLoginDto userLoginDto);

}
