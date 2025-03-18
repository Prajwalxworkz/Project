package com.xworkz.app.controller;

import com.xworkz.app.constants.Location;
import com.xworkz.app.dto.UserDto;
import com.xworkz.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//@Slf4j
@RequestMapping("/")
@Controller
public class UserController {
    @Autowired
    UserService service;

    @GetMapping("signUpPage")
    public String signUpPage(Model model) {
        List<Location> locationList = new ArrayList<>(Arrays.asList(Location.values()));
        for (Location loc : locationList) {
            System.out.println(loc);
        }
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
        System.out.println("signUp() in controller started");
/*
        System.out.println(dto.getFullName());
        System.out.println(dto.getEmail());
        System.out.println(dto.getGender());
        System.out.println(dto.getDob());
        System.out.println(dto.getPhoneNumber());
        System.out.println(dto.getLocation());
        System.out.println(dto.getPassword());
        System.out.println(dto.getConfirmPassword());
 */

        String returnedMessage = service.validateAndSave(dto);
        if (returnedMessage.equals("saved")) {
            model.addAttribute("successMessage", "Saved Successfully!!");
        } else {
            model.addAttribute("errorMessage", returnedMessage);
        }
        System.out.println("signUp() in controller ended");
        System.out.println("******************0********************0**************0**************0********************");
        return "signUp.jsp";
    }

    @PostMapping("signIn")
    public String logIn(@RequestParam("email") String email, @RequestParam("password") String password, String enteredCaptcha, HttpSession session, Model model) {
//        log.info("signIn() in controller started");
        System.out.println("signIn() in controller started");
        System.out.println(email);
        System.out.println(password);
        System.out.println("Given captcha " + (String) session.getAttribute("captchaText"));
        System.out.println("Entered captcha " + enteredCaptcha);
        String returnedMessage = service.validateAndLogIn(email, password, session, enteredCaptcha);
        System.out.println("The returned message is: " + returnedMessage);
        if (returnedMessage.equals("isPresent")) {
            System.out.println("setting the scope");
            model.addAttribute("email", email);
            System.out.println("signIn() in controller ended");
            System.out.println("******************0********************0**************0**************0********************");
            return "myAccountPage.jsp";
        } else if (returnedMessage.equals("forward")) {
            model.addAttribute("email", email);
            model.addAttribute("logInCount", "0");
            return "resetPassword.jsp";
        } else {
            model.addAttribute("Message", returnedMessage);
        }
        System.out.println("signIn() in controller ended ");
        System.out.println("******************0********************0**************0**************0********************");
        return "signIn.jsp";
    }

    @GetMapping("getUserByEmail")
    public String getUserDetails(@RequestParam("email") String email, String event, Model model,HttpSession session) {
        System.out.println("getUserDetails() in controller started");
        System.out.println(email);
        UserDto dto = service.getUserByEmail(email);
        if (dto != null) {
            if (event.equals("update")) {
                List<Location> locationList = new ArrayList<>(Arrays.asList(Location.values()));
                model.addAttribute("location", locationList);
                session.setAttribute("dto", dto);
                System.out.println("getUserDetails() in controller ended");
                System.out.println("******************0********************0**************0**************0********************");
                return "updateProfile.jsp";
            } else {
                String returnedMessage = service.deleteProfile(dto);
                if (returnedMessage.equals("deleted")) {
                    model.addAttribute("successMessage", "Profile deleted successfully!!");
                    System.out.println("getUserDetails() in controller ended");
                    System.out.println("******************0********************0**************0**************0********************");
                    return "index.jsp";
                } else {
                    model.addAttribute("errorMessage", returnedMessage);
                    System.out.println("getUserDetails() in controller ended");
                    System.out.println("******************0********************0**************0**************0********************");
                    return "myAccountPage.jsp";
                }

            }
        } else {
            model.addAttribute("errorMessage", "Could not execute the request.\n\n Please try after some time!!");
            System.out.println("getUserDetails() in controller ended");
            System.out.println("******************0********************0**************0**************0********************");
            return "myAccountPage.jsp";
        }
    }

    @PostMapping("updateProfile")
    public String updateProfile(UserDto dto, Model model) {
        System.out.println("updateProfile() in controller started");
        String returnedMessage = service.updateProfile(dto);
        if (returnedMessage.equals("updated")) {
            model.addAttribute("successMessage", "Profile updated successfully");
            model.addAttribute("email", dto.getEmail());
            System.out.println("updateProfile() in controller ended");
            System.out.println("******************0********************0**************0**************0********************");
            return "myAccountPage.jsp";
        } else {
            model.addAttribute("errorMessage", returnedMessage);
            System.out.println("updateProfile() in controller ended");
            System.out.println("******************0********************0**************0**************0********************");
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
}

