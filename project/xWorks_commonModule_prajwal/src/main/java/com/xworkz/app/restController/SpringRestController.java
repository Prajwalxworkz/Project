package com.xworkz.app.restController;


import com.xworkz.app.entity.UserEntity;
import com.xworkz.app.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/")
public class SpringRestController {

    @Autowired
    UserRepository repository;

    @GetMapping(value = "/fullName/{fullName}",produces = MediaType.APPLICATION_JSON_VALUE)
    public String validateName(@PathVariable String fullName){
       List<UserEntity> entityList=repository.getAllUserData();
       for(UserEntity entity: entityList){
           if(entity.getFullName().equals(fullName)){
              return "Name exists";
           }
       }
       /*
       if(fullName.isEmpty()){
           return "Name can't be null or empty";
       }
       else if (!fullName.trim().matches("^[A-Z][a-zA-Z]*( [A-Z][a-zA-Z]*)*$")) {
           return "Name must start with an uppercase letter, each word must begin with an uppercase letter, and it should contain only alphabets and spaces.";
       }
         else if (fullName.length() < 3 || fullName.length() > 50) {
           return "Name length should be >=3 and <=50";
       }else {
           return "";
       }

        */
        return "";
    }

    @GetMapping(value = "email/{email}")
    public  String validateEmail(@PathVariable String email){
        List<UserEntity> entityList=repository.getAllUserData();
        for(UserEntity entity: entityList){
            if(entity.getEmail().equals(email)){
                return "Email exists";
            }
        }
        return "";
    }
    @GetMapping(value = "phoneNumber/{phoneNumber}")
    public  String validatePhoneNumber(@PathVariable Long phoneNumber){
        List<UserEntity> entityList=repository.getAllUserData();
        for(UserEntity entity: entityList){
            if(entity.getPhoneNumber().equals(phoneNumber)){
                return "Phone Number exists";
            }
        }
        return "";
    }

}

