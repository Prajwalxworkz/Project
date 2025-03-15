package com.xworkz.app.service;

import com.mewebstudio.captcha.GeneratedCaptcha;
import com.xworkz.app.dto.UserDto;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;

public interface UserService {
    public String validateAndSave(UserDto dto);

    String validateAndLogIn(String email, String password,HttpSession session,String captcha);

    UserDto getUserByEmail(String email);

    String updateProfile(UserDto dto);

    String resetPassword(String email, String password, String confirmPassword);

    String deleteProfile(UserDto dto);

    ResponseEntity<byte[]> captchaImage(HttpSession session);
}
