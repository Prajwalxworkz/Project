package com.xworkz.app.controller;

import com.sun.org.apache.xpath.internal.operations.Bool;
import com.xworkz.app.dto.UserDto;
import com.xworkz.app.service.UserService;
import lombok.extern.slf4j.Slf4j;
//import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
//@Slf4j
@RequestMapping("/")
@Controller
public class UserController {
    @Autowired
    UserService service;

    @PostMapping("signUp")
    public String saveProfile(UserDto dto, Model model) {
        System.out.println("signUp() in controller started");

        System.out.println(dto.getFullName());
        System.out.println(dto.getEmail());
        System.out.println(dto.getGender());
        System.out.println(dto.getDob());
        System.out.println(dto.getPhoneNumber());
        System.out.println(dto.getLocation());
        System.out.println(dto.getPassword());
        System.out.println(dto.getConfirmPassword());
        String returnedMessage = service.validateAndSave(dto);
        if (returnedMessage.equals("saved")) {
           model.addAttribute("successMessage","Saved Successfully!!");
        }else {
            model.addAttribute("errorMessage",returnedMessage);
        }
        System.out.println("signUp() in controller ended");
        System.out.println("******************0********************0**************0**************0********************");
        return "signUp.jsp";
    }

    @PostMapping("signIn")
    public String logIn(@RequestParam("email") String email, @RequestParam("password") String password,  Model model){
//        log.info("signIn() in controller started");
        System.out.println("signIn() in controller started");
        System.out.println(email);
        System.out.println(password);
        String returnedMessage=service.validateAndLogIn(email, password);
        System.out.println("The returned message is: "+returnedMessage);
        if(returnedMessage.equals("isPresent")){
            System.out.println("setting the scope");
            model.addAttribute("email",email);
            System.out.println("signIn() in controller ended");
            System.out.println("******************0********************0**************0**************0********************");
            return "myAccountPage.jsp";
        } else if (returnedMessage.equals("forward")) {
            model.addAttribute("email",email);
            model.addAttribute("logInCount","0");
            return "resetPassword.jsp";
        } else{
            model.addAttribute("Message",returnedMessage);
        }
        System.out.println("signIn() in controller ended ");
        System.out.println("******************0********************0**************0**************0********************");
        return "signIn.jsp";
    }

    @GetMapping("getUserByEmail")
    public String getUserDetails(@RequestParam("email") String email, Model model){
        System.out.println("getUserDetails() in controller started");
        UserDto dto=service.getUserByEmail(email);
        model.addAttribute("dto",dto);
        System.out.println("getUserDetails() in controller ended");
        System.out.println("******************0********************0**************0**************0********************");
        return "updateProfile.jsp";
    }

    @PostMapping("updateProfile")
    public String updateProfile(UserDto dto, Model model){
        System.out.println("updateProfile() in controller started");
        String returnedMessage=service.updateProfile(dto);
        if(returnedMessage.equals("updated")){
            model.addAttribute("successMessage","Profile updated successfully");
            System.out.println("updateProfile() in controller ended");
            System.out.println("******************0********************0**************0**************0********************");
            return "myAccountPage.jsp";
        }
        else{
            model.addAttribute("errorMessage",returnedMessage);
            System.out.println("updateProfile() in controller ended");
            System.out.println("******************0********************0**************0**************0********************");
            return "updateProfile.jsp" ;
        }
    }
@PostMapping("resetPassword")
    public String resetPassword(@RequestParam("email") String email,@RequestParam("password") String password,@RequestParam("confirmPassword") String confirmPassword, Model model ){
        System.out.println("resetPassword() in controller started");
        String returnedMessage=service.resetPassword(email, password, confirmPassword);
        if(returnedMessage.equals("done")){
            model.addAttribute("successMessage","Password reset successfully done.");
            System.out.println("resetPassword() in controller ended");
            System.out.println("******************0********************0**************0**************0********************");
            return "signIn.jsp";
        }else{
            model.addAttribute("errorMessage",returnedMessage);
            System.out.println("resetPassword() in controller ended");
            System.out.println("******************0********************0**************0**************0********************");
            return "resetPassword.jsp";
        }
    }
}

