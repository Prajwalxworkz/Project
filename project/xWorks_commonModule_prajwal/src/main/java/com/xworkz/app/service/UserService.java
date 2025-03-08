package com.xworkz.app.service;

import com.xworkz.app.dto.UserDto;

public interface UserService {
    public String validateAndSave(UserDto dto);

    String validateAndLogIn(String email, String password);

    UserDto getUserByEmail(String email);

    String updateProfile(UserDto dto);

    String resetPassword(String email, String password, String confirmPassword);
}
