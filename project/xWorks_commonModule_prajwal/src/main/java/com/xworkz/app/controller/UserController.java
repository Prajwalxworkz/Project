package com.xworkz.app.controller;

import com.xworkz.app.constants.Location;
import com.xworkz.app.dto.UserDto;
import com.xworkz.app.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@RequestMapping("/")
@Controller
public class UserController {
    @Autowired
    UserService service;

    public UserController() {
        log.info("UserController object created here");
    }

    @GetMapping("signUpPage")
    public String signUpPage(Model model) {
        List<Location> locationList = new ArrayList<>(Arrays.asList(Location.values()));
        log.info(locationList.toString());
        model.addAttribute("location", locationList);
        return "signUp.jsp";
    }

    @GetMapping("signInPage")
    public String signInPage() {
        return "signIn.jsp";
    }

    @GetMapping("/captchaImage")
    public ResponseEntity<byte[]> getCaptchaImage(HttpSession session) {
        return service.captchaImage(session);
    }

    @PostMapping("signUp")
    public String saveProfile(UserDto dto, Model model) {
        log.info("signUp() in controller started");

        log.info(dto.getFullName());

        MultipartFile file = dto.getMultipartFile();
        String fileName1 = file.getName();
        String fileName2 = file.getOriginalFilename();
        String contentType = file.getContentType();
        Resource resource = file.getResource();
        byte[] bytes;
        try {
            bytes = file.getBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        log.info(String.valueOf(file));
        log.info(fileName1);
        log.info(fileName2);
        log.info(contentType);
        log.info(String.valueOf(resource));
        log.info(dto.getProfilePicture());
        log.info(dto.getEmail());
        log.info(dto.getGender());
        log.info(dto.getDob());
        log.info(String.valueOf(dto.getPhoneNumber()));
        log.info(String.valueOf(dto.getLocation()));
        log.info(dto.getPassword());
        log.info(dto.getConfirmPassword());
        System.out.println(file+"     "+fileName1+"        "+fileName2+"            "+resource);
        System.out.println("---------------------------------------");
        System.out.println(dto);
        System.out.println("---------------------------------------");
        Path path= Paths.get("D:\\uploadedFiles\\commonModule\\"+fileName2);
        try {
            Files.write(path,bytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String returnedMessage = service.validateAndSave(dto);
        if (returnedMessage.equals("saved")) {
            model.addAttribute("successMessage", "Saved Successfully!!");
        } else {
            model.addAttribute("errorMessage", returnedMessage);
        }
        log.info("signUp() in controller ended");
        log.info("******************0********************0**************0**************0********************");
        return "signUp.jsp";
    }

    @PostMapping("signIn")
    public String logIn(@RequestParam("email") String email, @RequestParam("password") String password, String enteredCaptcha, HttpSession session, Model model) {
//        log.info("signIn() in controller started");
        log.info("signIn() in controller started");
        log.info(email);
        log.info(password);
        log.info("Given captcha " + (String) session.getAttribute("captchaText"));
        log.info("Entered captcha " + enteredCaptcha);
        String returnedMessage = service.validateAndLogIn(email, password, session, enteredCaptcha);
        log.info("The returned message is: " + returnedMessage);
        if (returnedMessage.equals("isPresent")) {
            log.info("setting the scope");
            model.addAttribute("email", email);
            log.info("signIn() in controller ended");
            log.info("******************0********************0**************0**************0********************");
            return "myAccountPage.jsp";
        } else if (returnedMessage.equals("forward")) {
            model.addAttribute("email", email);
            model.addAttribute("logInCount", "0");
            return "resetPassword.jsp";
        } else {
            model.addAttribute("Message", returnedMessage);
        }
        log.info("signIn() in controller ended ");
        log.info("******************0********************0**************0**************0********************");
        return "signIn.jsp";
    }

    @GetMapping("getUserByEmail")
    public String getUserDetails(@RequestParam("email") String email, String event, Model model,HttpSession session) {
        log.info("getUserDetails() in controller started");
        log.info(email);
        UserDto dto = service.getUserByEmail(email);
        if (dto != null) {
            if (event.equals("update")) {
                List<Location> locationList = new ArrayList<>(Arrays.asList(Location.values()));
                model.addAttribute("location", locationList);
                session.setAttribute("dto", dto);
                log.info("getUserDetails() in controller ended");
                log.info("******************0********************0**************0**************0********************");
                return "updateProfile.jsp";
            } else {
                String returnedMessage = service.deleteProfile(dto);
                if (returnedMessage.equals("deleted")) {
                    model.addAttribute("successMessage", "Profile deleted successfully!!");
                    log.info("getUserDetails() in controller ended");
                    log.info("******************0********************0**************0**************0********************");
                    return "index.jsp";
                } else {
                    model.addAttribute("errorMessage", returnedMessage);
                    log.info("getUserDetails() in controller ended");
                    log.info("******************0********************0**************0**************0********************");
                    return "myAccountPage.jsp";
                }

            }
        } else {
            model.addAttribute("errorMessage", "Could not execute the request.\n\n Please try after some time!!");
            log.info("getUserDetails() in controller ended");
            log.info("******************0********************0**************0**************0********************");
            return "myAccountPage.jsp";
        }
    }

    @PostMapping("updateProfile")
    public String updateProfile(UserDto dto, Model model) {
        log.info("updateProfile() in controller started");
        Path path=Paths.get("D:\\uploadedFiles\\commonModule\\"+dto.getMultipartFile().getOriginalFilename());
        try {
            Files.write(path, dto.getMultipartFile().getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        dto.setProfilePicture(dto.getMultipartFile().getOriginalFilename());
        String returnedMessage = service.updateProfile(dto);
        if (returnedMessage.equals("updated")) {
            model.addAttribute("successMessage", "Profile updated successfully");
            model.addAttribute("email", dto.getEmail());
            log.info("updateProfile() in controller ended");
            log.info("******************0********************0**************0**************0********************");
            return "myAccountPage.jsp";
        } else {
            model.addAttribute("errorMessage", returnedMessage);
            log.info("updateProfile() in controller ended");
            log.info("******************0********************0**************0**************0********************");
            return "updateProfile.jsp";
        }
    }

    @PostMapping("resetPassword")
    public String resetPassword(@RequestParam("email") String email, @RequestParam("password") String password, @RequestParam("confirmPassword") String confirmPassword, Model model) {
        System.out.println("resetPassword() in controller started");
        String returnedMessage = service.resetPassword(email, password, confirmPassword);
        if (returnedMessage.equals("done")) {
            model.addAttribute("successMessage", "Password reset successfully done.");
            System.out.println("resetPassword() in controller ended");
            System.out.println("******************0********************0**************0**************0********************");
            return "signIn.jsp";
        } else {
            model.addAttribute("errorMessage", returnedMessage);
            System.out.println("resetPassword() in controller ended");
            System.out.println("******************0********************0**************0**************0********************");
            return "resetPassword.jsp";
        }
    }

    @GetMapping("download")
    public void downloadImage(HttpServletResponse response, @RequestParam String profilePicture){
            response.setContentType("image/jpg");
            File file =new File("D:\\uploadedFiles\\commonModule\\"+profilePicture);
        try {
            InputStream inputStream=new BufferedInputStream(new FileInputStream(file));
            ServletOutputStream outputStream= response.getOutputStream();
            IOUtils.copy(inputStream, outputStream);
            response.flushBuffer();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}