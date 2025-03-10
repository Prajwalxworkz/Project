package com.xworkz.app.service;

import com.xworkz.app.dto.UserDto;
import com.xworkz.app.entity.UserEntity;
import com.xworkz.app.repo.UserRepository;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLOutput;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService {

    private static final String emailRegex = "^[a-zA-Z0-9._%+-]+@gmail\\.com$";
    private final static String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[@#$%^&+=!])\\S{8,}$";

/*    @Override
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
                           System.out.println("email already existsq");
                              return isSaved;
                           }
                   }
                   dto.setPassword(encryption(dto.getPassword()));
                   System.out.println(dto.getPassword());
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

 */


    @Autowired
    UserRepository repository;

    @Override
    public String validateAndSave(UserDto dto) {
        System.out.println("validateAndSave() in service started");
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<UserDto>> validate = validator.validate(dto);
        if (!validate.isEmpty()) {
            for (ConstraintViolation<UserDto> userDtoConstraintViolation : validate) {
                System.out.println(userDtoConstraintViolation.getMessage());
            }
        } else {
            List<UserEntity> entityList = repository.getAllUserData();
            for (UserEntity entity : entityList) {
                if (entity.getEmail().equals(dto.getEmail())) {
                    return "Email exists";
                }
            }
            String name = dto.getFullName();
            String email = dto.getEmail();
            String dob = dto.getDob();
            Long phoneNumber = dto.getPhoneNumber();
            String gender = dto.getGender();
            String location = dto.getLocation();
            String password = passwordGenerator();
            System.out.println("Password: "+password);
            try {
                if (name != null && !name.trim().isEmpty()) {
                    if (name.matches("[A-Z][a-zA-Z ]*")) {
                        if (name.length() >= 3 && name.length() <= 50) {
                            if (email.matches("^[a-zA-Z0-9_.%+-]+@gmail\\.com$")) {
                                if (phoneNumber != null) {
                                    if (phoneNumber.toString().matches("(\\+91)?[976]\\d{9}")) {
                                        dto.setPassword(encryption(password));
                                        UserEntity entity = new UserEntity();
                                        BeanUtils.copyProperties(entity, dto);
                                        Boolean isSaved = repository.save(entity);
                                        if (isSaved) return "saved";
                                        else return "ConstraintViolationException: could not execute statement";
                                         } else
                                        return "Invalid: Phone number must start with 9, 7, or 6 and must contain exactly 10 digits.";
                                } else return "Invalid: Phone number cannot null.";
                            } else
                                return "Invalid: domain should be gmail.com, no spaces in between, and only '_', '.', '%', '+', and '-' are allowed.";
                        } else return "Invalid: Name should be of length min of 3 and max of 50";
                    } else
                        return "Invalid: Name must start with an uppercase letter and must contain only alphabets and spaces.";
                } else return "Invalid: Name cannot be empty or null.";
            } catch (IllegalAccessException | InvocationTargetException e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println("validateAndSave() in service ended");
        return "saved";
    }

    /*
     if (password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*-+])\\S{8,}$")) {
                                            if (password.equals(confirmPassword)) {
                                                dto.setPassword(encryption(dto.getPassword()));
                                                UserEntity entity = new UserEntity();
                                                BeanUtils.copyProperties(entity, dto);
                                                Boolean isSaved = repository.save(entity);
                                                if (isSaved) return "saved";
                                                else return "ConstraintViolationException: could not execute statement";
                                            } else return "Invalid: Password and ConfirmPassword does not match";
                                        } else
                                            return "Invalid: Password must have at least one uppercase letter, one lowercase letter, one digit, one special character, no spaces, not null, not empty and be at least 8 characters long.";

     */


/*        @Override
    public String validateAndLogIn(String email, String password) {
        String returnedMessage="";
        System.out.println("validateAndLogIn() in service started");
            List<UserEntity> entityList=repository.getAllUserData();
            for(UserEntity entity1:entityList){
                if(entity1.getEmail().equals(email)){
                    if(decryption(entity1.getPassword()).equals(password)){
                        returnedMessage= "isPresent";
                    }else returnedMessage= "Password is incorrect.";
                }else returnedMessage= "Email is incorrect.";
            }
//        System.out.println("Is email and password present?: "+isAvailable);
        System.out.println("ValidateAndLogIn() in service ended");
        return returnedMessage;
        }
 */


    @Override
    public String validateAndLogIn(String email, String password) {

        System.out.println("validateAndLogIn() in service started");
        List<UserEntity> entityList = repository.getAllUserData();
        for (UserEntity entity : entityList) {
            if (entity.getEmail().equals(email)) {
                System.out.println(entity.getEmail());
                System.out.println(email);
                UserEntity entity1 = repository.getUserByEmail(email);
                if (decryption(entity.getPassword()).equals(password)) {
                    int invalidLogInCount = entity1.getInvalidLogInCount();
                    if (invalidLogInCount==-1){
                        entity1.setInvalidLogInCount(0);
                        System.out.println("During initial login time, the invalidLoginCount is: "+entity1.getInvalidLogInCount());
                        repository.updateProfile(entity1);
                        return "forward";

                    } else if (invalidLogInCount == 0) {
                        return "isPresent";

                    } else if (invalidLogInCount==1 || invalidLogInCount==2) {
                        System.out.println("invalidLogInCount==1 || invalidLogInCount==1");
                        entity1.setInvalidLogInCount(0);
                        entity1.setLastLogIn(null);
                        System.out.println(entity1.getInvalidLogInCount());
                        System.out.println(entity1.getLastLogIn());
                        repository.updateProfile(entity1);
                        return "isPresent";
                    }  else if (invalidLogInCount == 3 && (Duration.between(entity1.getLastLogIn(), Instant.now())).toMillis() >= 60000) {
                        System.out.println("invalidLogInCount==3 && duration>=600000");
                        entity1.setInvalidLogInCount(0);
                        entity1.setLastLogIn(null);
                        System.out.println(entity1.getInvalidLogInCount());
                        System.out.println(entity1.getLastLogIn());
                        repository.updateProfile(entity1);
                        return "isPresent";

                    } else{
                        return "Try after 24 hours";

                    }
                } else {
                    int invalidLogInCount = entity1.getInvalidLogInCount();
                    System.out.println("invalid log in count before: " + invalidLogInCount);
                    Instant lastLogIn = entity1.getLastLogIn();
                    System.out.println("Last log in time: " + lastLogIn);
                    if (invalidLogInCount <3) {
                        invalidLogInCount = invalidLogInCount + 1;
                        entity1.setInvalidLogInCount(invalidLogInCount);
                        System.out.println("invalid log in count after: " + entity1.getInvalidLogInCount());
                        lastLogIn = Instant.now();
                        entity1.setLastLogIn(lastLogIn);
                        System.out.println("present log in time: " + entity1.getLastLogIn());
                        repository.updateProfile(entity1);
                         if(invalidLogInCount==1) return "Password is incorrect.Two more attempts left.";
                         else if(invalidLogInCount==2)  return "Password is incorrect.Ome attempt left.";
                         else return "Account is locked for 24 hours";
                    } else {
                         return "Account is locked for 24 hours";
                    }
                }
            }
        }
        System.out.println("ValidateAndLogIn() in service ended");
        return "Invalid email";
    }

    @Override
    public UserDto getUserByEmail(String email) {
        UserEntity entity = repository.getUserByEmail(email);
        entity.setPassword(decryption(entity.getPassword()));
        try {
            UserDto dto = new UserDto();
            BeanUtils.copyProperties(dto, entity);
            return dto;
        } catch (IllegalAccessException | InvocationTargetException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

 /*   @Override
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
                    dto.setPassword(encryption(dto.getPassword()));
                    System.out.println(dto.getPassword());
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

  */

    @Override
    public String updateProfile(UserDto dto) {
        System.out.println("updateProfile() in service started");
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<UserDto>> validate = validator.validate(dto);
        if (!validate.isEmpty()) {
            for (ConstraintViolation<UserDto> userDtoConstraintViolation : validate) {
                System.out.println(userDtoConstraintViolation.getMessage());
            }
        } else {
            String name = dto.getFullName();
            String email = dto.getEmail();
            String dob = dto.getDob();
            Long phoneNumber = dto.getPhoneNumber();
            String gender = dto.getGender();
            String location = dto.getLocation();
            String password = dto.getPassword();
            String confirmPassword = dto.getConfirmPassword();
            try {
                if (name != null && !name.trim().isEmpty()) {
                    if (name.matches("[A-Z][a-zA-Z ]*")) {
                        if (name.length() >= 3 && name.length() <= 50) {
                            if (email.matches("^[a-zA-Z0-9_.%+-]+@gmail\\.com$")) {
                                if (phoneNumber != null) {
                                    if (phoneNumber.toString().matches("(\\+91)?[976]\\d{9}")) {
                                        if (password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*-+_=])\\S{8,}$")) {
                                            if (password.equals(confirmPassword)) {
                                                System.out.println("Before encryption: " + dto.getPassword());
                                                dto.setPassword(encryption(dto.getPassword()));
                                                UserEntity entity = repository.getUserByEmail(email);
                                                BeanUtils.copyProperties(entity, dto);
                                                repository.updateProfile(entity);
                                                return "updated";
                                            } else return "Invalid: Passwords must match";
                                        } else
                                            return "Invalid: Password must have at least one uppercase letter, one lowercase letter, one digit, one special character, no spaces, not null, not empty and be at least 8 characters long.";
                                    } else
                                        return "Invalid: Phone number must start with 9, 7, or 6 and must contain exactly 10 digits.";
                                } else return "Invalid: Phone number cannot null.";
                            } else
                                return "Invalid: domain should be gmail.com, no spaces in between, and only '_', '.', '%', '+', and '-' are allowed.";
                        } else return "Invalid: Name should be of length min of 3 and max of 50";
                    } else
                        return "Invalid: Name must start with an uppercase letter and must contain only alphabets and spaces.";
                } else return "Invalid: Name cannot be empty or null.";
            } catch (IllegalAccessException | InvocationTargetException e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println("updateProfile() in service ended");
        return "updated";
    }

    @Override
    public String resetPassword(String email, String password, String confirmPassword) {
        System.out.println("resetPassword() in service started");
        List<UserEntity> entityList = repository.getAllUserData();
        for (UserEntity entity : entityList) {
            if (email.equals(entity.getEmail())) {
                if (password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*-+=_])\\S{8,}$")) {
                    if (password.equals(confirmPassword)) {
//                        UserEntity entity1=repository.getUserByEmail(email);
                        entity.setPassword(encryption(password));
                        entity.setInvalidLogInCount(0);
                        entity.setLastLogIn(null);
                        repository.updateProfile(entity);
                        return "done";
                    } else return "Passwords must match";
                } else
                    return "Invalid: Password must have at least one uppercase letter, one lowercase letter, one digit, one special character, no spaces, not null, not empty and be at least 8 characters long.";
            }
        }
        System.out.println("resetPassword() in service ended");
         return "Recheck the entered email";
    }

    public Boolean validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            System.out.println("Invalid: Name cannot be empty or null.");
            return false;
        }
        if (!name.matches("[A-Z][a-zA-Z ]*")) {
            System.out.println("Invalid: Name must start with an uppercase letter and contain only alphabets and spaces.");
            return false;
        }
        if ((name.length() < 3)) {
            System.out.println("Invalid: Length should be >3 .");
            return false;
        }
        if ((name.length() > 50)) {
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

    public Boolean validateEmail(String email) {
        if (!Pattern.matches(emailRegex, email)) {  // Correct way to use regex
            System.out.println("Invalid: domain should be gmail.com, no spaces in between, and only '_', '.', '%', '+', and '-' are allowed.");
            return false;
        }
        return true;
    }

    public Boolean validatePassword(String password) {
        if (!Pattern.matches(passwordRegex, password)) {
            System.out.println("Invalid: Password must have at least one uppercase letter, one lowercase letter, one digit, one special character, no spaces, and be at least 8 characters long.");
            return false;
        }
        return true;
    }

    public String encryption(String password) {
        StringBuilder encrypt = new StringBuilder();
        for (char ch : password.toCharArray()) {
            encrypt.append((char) (ch + 3));
        }
        return encrypt.toString();
    }

    public String decryption(String encryptedPassword) {
        StringBuilder decrypt = new StringBuilder();
        for (char ch : encryptedPassword.toCharArray()) {
            decrypt.append((char) (ch - 3));
        }
        return decrypt.toString();
    }

  public String passwordGenerator(){
      Random random= new Random();
      StringBuilder stringBuilder=new StringBuilder();
      while(stringBuilder.length()<13){
          char character=(char) random.nextInt(256);
          if(String.valueOf(character).matches("[a-zA-Z0-9!@#$%^&*-=_+]")){
              stringBuilder.append(character);
          }
      }
     return stringBuilder.toString();
  }

}


