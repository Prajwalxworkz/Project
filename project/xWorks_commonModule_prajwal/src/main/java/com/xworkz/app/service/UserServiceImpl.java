package com.xworkz.app.service;

import com.xworkz.app.dto.UserDto;
import com.xworkz.app.entity.UserEntity;
import com.xworkz.app.repo.UserRepository;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.enterprise.inject.spi.Bean;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository repository;

    @Override
    public Boolean validateAndSave(UserDto dto) {
        System.out.println("validateAndSave() in service started");
        Boolean isSaved=false;
        ValidatorFactory validatorFactory =Validation.buildDefaultValidatorFactory();
        Validator validator =validatorFactory.getValidator();
        Set<ConstraintViolation<UserDto>> validate = validator.validate(dto);
       if (!validate.isEmpty()){
            validate.forEach(error-> System.out.println(error.getMessage()));
       }else{
           try{
               String name=dto.getFullName();
               Long phoneNumber=dto.getPhoneNumber();
               String email=dto.getEmail();
               String password=dto.getPassword();
               String confirmPassword=dto.getConfirmPassword();
               if(validateName(name) && validatePhoneNumber(phoneNumber) && validateEmail(email) && validatePassword(password) && password.equals(confirmPassword)) {
                   List<UserEntity> entityList=repository.logInCredentials();
                   for(UserEntity entity1:entityList){
                       if(entity1.getEmail().equals(email)){
                           System.out.println("email already exists");
                              return isSaved;
                           }
                   }
                   UserEntity entity = new UserEntity();
                   BeanUtils.copyProperties(entity, dto);
                   System.out.println("Moving to repo");
                   isSaved = repository.save(entity);
                   System.out.println("is the data saved?: " + isSaved);
               }else{
                   System.out.println(" error");
                   return isSaved;
               }
           }catch(IllegalAccessException | InvocationTargetException e){
               System.out.println(e.getMessage());
           }
       }
        System.out.println("validateAndSave() in service ended");
        return isSaved;
    }

    @Override
    public Boolean validateAndLogIn(String email, String password) {
        System.out.println("validateAndLogIn() in service started");
        Boolean isAvailable=false;
            List<UserEntity> entityList=repository.logInCredentials();
            for(UserEntity entity1:entityList){
                if(entity1.getEmail().equals(email)){
                    if(decryption(entity1.getPassword()).equals(password)){
                       isAvailable= true;
                    }
                }
            }
        System.out.println("Is email and password present?: "+isAvailable);
        System.out.println("ValidateAndLogIn() in service ended");
        return isAvailable;
    }

    @Override
    public UserDto getUserByEmail(String email) {
        UserEntity entity= repository.getUserByEmail(email);
        entity.setPassword( decryption(entity.getPassword()));
        try{
            UserDto dto=new UserDto();
            BeanUtils.copyProperties(dto,entity);
            return dto;
        }catch (IllegalAccessException | InvocationTargetException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public Boolean updateProfile(UserDto dto) {
        System.out.println("updateProfile() in service  started");
        Boolean isUpdated=false;
        ValidatorFactory validatorFactory =Validation.buildDefaultValidatorFactory();
        Validator validator =validatorFactory.getValidator();
        Set<ConstraintViolation<UserDto>> validate = validator.validate(dto);
        if (!validate.isEmpty()){
            validate.forEach(error-> System.out.println(error.getMessage()));
        }else{
            try{
                String name=dto.getFullName();
                Long phoneNumber=dto.getPhoneNumber();
                String email=dto.getEmail();
                String password=dto.getPassword();
                String confirmPassword=dto.getConfirmPassword();
                if(validateName(name) && validatePhoneNumber(phoneNumber) && validateEmail(email) && validatePassword(password) && password.equals(confirmPassword)) {
                    UserEntity entity = new UserEntity();
                    BeanUtils.copyProperties(entity, dto);
                    System.out.println("Moving to repo");
                    isUpdated = repository.updateProfile(entity);
                    System.out.println("is the data saved?: " + isUpdated);
                }else{
                    System.out.println(" error");
                }
            }catch(IllegalAccessException | InvocationTargetException e){
                System.out.println(e.getMessage());
            }
        }
        System.out.println("updateProfile() in service ended");
        return isUpdated;
    }


    public Boolean validateName(String name){
        if(name==null || name.trim().isEmpty()){
            System.out.println("Invalid: Name cannot be empty or null.");

            return false;
        }
        if(!name.matches("[A-Z][a-zA-Z ]*")){
            System.out.println("Invalid: Name must start with an uppercase letter and contain only alphabets and spaces.");
            return false;
        }
        if((name.length() < 3)){
            System.out.println("Invalid: Length should be >3 .");
            return false;
        }if( (name.length() >50)){
            System.out.println("Invalid: Length should be <50 .");
            return false;
        }
        return true;
    }

    public Boolean validatePhoneNumber(Long phoneNumber) {
        if (phoneNumber == null || Long.toString(phoneNumber).trim().isEmpty()) {
            System.out.println("Invalid: Phone number cannot be empty or null.");
            return false;
        }
        if (!Long.toString(phoneNumber).matches("[976][0-9]{9}")) {
            System.out.println("Invalid: Phone number must start with 9, 7, or 6 and contain exactly 10 digits.");
            return false;
        }
        return true;
    }
        private static final String emailRegex="^[a-zA-Z0-9._%+-]+@gmail\\.com$";
        public Boolean validateEmail(String email) {
        if (!Pattern.matches(emailRegex, email)) {  // Correct way to use regex
            System.out.println("Invalid: domain should be gmail.com, no spaces in between, and only '_', '.', '%', '+', and '-' are allowed.");
            return false;
        }
        return true;
    }

    private  final static String passwordRegex="^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[@#$%^&+=!])\\S{8,}$";
        public Boolean validatePassword(String password){
            if(!Pattern.matches(passwordRegex, password)){
                System.out.println("Invalid: Password must have at least one uppercase letter, one lowercase letter, one digit, one special character, no spaces, and be at least 8 characters long.");
                return false;
            }
            return true;
        }

        public String encryption(String password){
            StringBuilder encrypt= new StringBuilder();
            for(char ch:password.toCharArray()){
                encrypt.append((char)(ch+3));
            }
            return encrypt.toString();
        }
        public String decryption(String encryptedPassword){
            StringBuilder decrypt= new StringBuilder();
            for(char ch:encryptedPassword.toCharArray()){
                decrypt.append((char)(ch-3));
            }
            return decrypt.toString();
        }

}


