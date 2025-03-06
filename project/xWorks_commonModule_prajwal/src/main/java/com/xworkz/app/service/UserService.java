package com.xworkz.app.service;

import com.xworkz.app.dto.UserDto;

public interface UserService {
    public Boolean validateAndSave(UserDto dto);

    Boolean validateAndLogIn(String email, String password);

    UserDto getUserByEmail(String email);

    Boolean updateProfile(UserDto dto);
}
