package com.xworkz.app.service;

import com.mewebstudio.captcha.Captcha;
import com.mewebstudio.captcha.Config;
import com.mewebstudio.captcha.GeneratedCaptcha;
import com.xworkz.app.config.SpringConfiguration;
import com.xworkz.app.constants.Location;
import com.xworkz.app.dto.UserDto;
import com.xworkz.app.entity.UserEntity;
import com.xworkz.app.repo.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;
import java.util.Properties;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private static final String emailRegex = "^[a-zA-Z0-9._%+-]+@gmail\\.com$";
    private final static String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[@#$%^&+=!])\\S{8,}$";

/*    @Override
    public Boolean validateAndSave(UserDto dto) {
        log.info("validateAndSave() in service started");
        Boolean isSaved=false;
        ValidatorFactory validatorFactory =Validation.buildDefaultValidatorFactory();
        Validator validator =validatorFactory.getValidator();
        Set<ConstraintViolation<UserDto>> validate = validator.validate(dto);
       if (!validate.isEmpty()){
            validate.forEach(error-> log.info(error.getMessage()));
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
                           log.info("email already existsq");
                              return isSaved;
                           }
                   }
                   dto.setPassword(encryption(dto.getPassword()));
                   log.info(dto.getPassword());
                   UserEntity entity = new UserEntity();
                   BeanUtils.copyProperties(entity, dto);
                   log.info("Moving to repo");
                   isSaved = repository.save(entity);
                   log.info("is the data saved?: " + isSaved);
               }else{
                   log.info(" error");
                   return isSaved;
               }
           }catch(IllegalAccessException | InvocationTargetException e){
               log.info(e.getMessage());
           }
       }
        log.info("validateAndSave() in service ended");
        return isSaved;
    }

 */


    @Autowired
    UserRepository repository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public String validateAndSave(UserDto dto) {
        log.info("validateAndSave() in service started");

        List<UserEntity> entityList = repository.getAllUserData();
        for (UserEntity entity : entityList) {
            if (dto.getFullName().equals(entity.getFullName())) return "Name exists";
            if (entity.getEmail().equals(dto.getEmail())) return "Email exists";
            if (dto.getPhoneNumber()==(entity.getPhoneNumber())) return "Phone number exists";
        }
        String name = dto.getFullName();
        String email = dto.getEmail();
        String dob = dto.getDob();
        Long phoneNumber = dto.getPhoneNumber();
        String gender = dto.getGender();
        Location location = dto.getLocation();
        String fileName=dto.getMultipartFile().getOriginalFilename();
        dto.setProfilePicture(fileName);
        try {
            if (name != null && !name.trim().isEmpty()) {
                if (name.matches("[A-Z][a-zA-Z ]*")) {
                    if (name.length() >= 3 && name.length() <= 50) {
                        if (email.matches("^[a-zA-Z0-9_.%+-]+@gmail\\.com$")) {
                            if (phoneNumber != null) {
                                if (phoneNumber.toString().matches("(\\+91)?[976]\\d{9}")) {
                                    String password = passwordGenerator();
                                    System.out.println("Password: " + password);
                                    log.info("Password: {}", password);
                                    dto.setPassword(passwordEncoder.encode(password));
                                    UserEntity entity = new UserEntity();
                                    BeanUtils.copyProperties(entity, dto);
                                    log.info("Saving -1 as the default value for invalidLoginCount");
                                    entity.setInvalidLogInCount(-1);
                                    entity.setCreatedBy(dto.getFullName());
                                    entity.setCreatedDate(LocalDateTime.now());
                                    Boolean isSaved = repository.save(entity);
                                    if (isSaved){
                                         sendEmail(dto.getFullName(),dto.getEmail(),password);
                                        return "saved";
                                    }
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
            log.info(e.getMessage());
        }
        log.info("validateAndSave() in service ended");
        return "saved";
    }

 /*   @Override
    public String validateAndSave(UserDto dto) {
        log.info("validateAndSave() in service started");
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<UserDto>> validate = validator.validate(dto);
        if (!validate.isEmpty()) {
            for (ConstraintViolation<UserDto> userDtoConstraintViolation : validate) {
                log.info(userDtoConstraintViolation.getMessage());
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
            log.info("Password: "+password);
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
                log.info(e.getMessage());
            }
        }
        log.info("validateAndSave() in service ended");
        return "saved";
    }

  */

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
        log.info("validateAndLogIn() in service started");
            List<UserEntity> entityList=repository.getAllUserData();
            for(UserEntity entity1:entityList){
                if(entity1.getEmail().equals(email)){
                    if(decryption(entity1.getPassword()).equals(password)){
                        returnedMessage= "isPresent";
                    }else returnedMessage= "Password is incorrect.";
                }else returnedMessage= "Email is incorrect.";
            }
//        log.info("Is email and password present?: "+isAvailable);
        log.info("ValidateAndLogIn() in service ended");
        return returnedMessage;
        }
 */


    @Override
    public String validateAndLogIn(String email, String password,HttpSession session, String enteredCaptcha) {

        log.info("validateAndLogIn() in service started");
        String captchaCode=(String)session.getAttribute("captchaText");
        if(!captchaCode.equals(enteredCaptcha)){
            return "Invalid CAPTCHA! Please try again.";
        }
        if (email.isEmpty()) return "Email can't be null";
        if (password.isEmpty()) return "Password can't be null";
        List<UserEntity> entityList = repository.getAllUserData();

        for (UserEntity entity : entityList) {
            if (entity.getEmail().equals(email)) {
                log.info(entity.getEmail());
                log.info(email);
                UserEntity entity1 = repository.getUserByEmail(email);
                System.out.println(passwordEncoder.matches(password,entity1.getPassword()));
                if (passwordEncoder.matches(password,entity1.getPassword())) {
                    int invalidLogInCount = entity1.getInvalidLogInCount();
                    if (invalidLogInCount == -1) {
                        entity1.setInvalidLogInCount(0);
                        log.info("During initial login time, the invalidLoginCount becomes: " + entity1.getInvalidLogInCount());
                        repository.updateProfile(entity1);
                        return "forward";

                    } else if (invalidLogInCount == 0) {
                        return "isPresent";

                    } else if (invalidLogInCount == 1 || invalidLogInCount == 2) {
                        log.info("invalidLogInCount==1 || invalidLogInCount==1");
                        entity1.setInvalidLogInCount(0);
                        entity1.setLastLogIn(null);
                        log.info(String.valueOf(entity1.getInvalidLogInCount()));
                        log.info(String.valueOf(entity1.getLastLogIn()));
                        repository.updateProfile(entity1);
                        return "isPresent";
                    } else if (invalidLogInCount == 3 && (Duration.between(entity1.getLastLogIn(), Instant.now())).toMillis() >= 60000) {
                        log.info("invalidLogInCount==3 && duration>=600000");
                        entity1.setInvalidLogInCount(0);
                        entity1.setLastLogIn(null);
                        log.info(String.valueOf(entity1.getInvalidLogInCount()));
                        log.info(String.valueOf(entity1.getLastLogIn()));
                        repository.updateProfile(entity1);
                        return "isPresent";

                    } else {
                        return "Try after 24 hours";
                    }
                } else {
                    int invalidLogInCount = entity1.getInvalidLogInCount();
                    log.info("invalid log in count before: " + invalidLogInCount);
                    Instant lastLogIn = entity1.getLastLogIn();
                    log.info("Last log in time: " + lastLogIn);
                    if (invalidLogInCount >-1 && invalidLogInCount<3) {
                        invalidLogInCount = invalidLogInCount + 1;
                        entity1.setInvalidLogInCount(invalidLogInCount);
                        log.info("invalid log in count after: " + entity1.getInvalidLogInCount());
                        lastLogIn = Instant.now();
                        entity1.setLastLogIn(lastLogIn);
                        log.info("present log in time: " + entity1.getLastLogIn());
                        repository.updateProfile(entity1);
                        if (invalidLogInCount == 1) return "Password is incorrect.Two more attempts left.";
                        else if (invalidLogInCount == 2) return "Password is incorrect.Ome attempt left.";
                        else return "Account is locked for 24 hours";
                    } else if (invalidLogInCount==-1) {
                        return "Invalid password";
                    } else {
                        return "Account is locked for 24 hours";
                    }
                }
            }
        }
        log.info("ValidateAndLogIn() in service ended");
        return "Invalid email";
    }

    @Override
    public UserDto getUserByEmail(String email) {
        log.info("getUserByEmail() in service started");
        UserEntity entity = repository.getUserByEmail(email);

        try {
            UserDto dto = new UserDto();
            BeanUtils.copyProperties(dto, entity);
            log.info("getUserByEmail() in service ended");
            return dto;
        } catch (IllegalAccessException | InvocationTargetException e) {
            log.info(e.getMessage());
        }
        log.info("getUserByEmail() in service ended");
        return null;
    }

 /*   @Override
    public Boolean updateProfile(UserDto dto) {
        log.info("updateProfile() in service  started");
        Boolean isUpdated=false;
        ValidatorFactory validatorFactory =Validation.buildDefaultValidatorFactory();
        Validator validator =validatorFactory.getValidator();
        Set<ConstraintViolation<UserDto>> validate = validator.validate(dto);
        if (!validate.isEmpty()){
            validate.forEach(error-> log.info(error.getMessage()));
        }else{
            try{
                String name=dto.getFullName();
                Long phoneNumber=dto.getPhoneNumber();
                String email=dto.getEmail();
                String password=dto.getPassword();
                String confirmPassword=dto.getConfirmPassword();
                if(validateName(name) && validatePhoneNumber(phoneNumber) && validateEmail(email) && validatePassword(password) && password.equals(confirmPassword)) {
                    dto.setPassword(encryption(dto.getPassword()));
                    log.info(dto.getPassword());
                    UserEntity entity = new UserEntity();
                    BeanUtils.copyProperties(entity, dto);
                    log.info("Moving to repo");
                    isUpdated = repository.updateProfile(entity);
                    log.info("is the data saved?: " + isUpdated);
                }else{
                    log.info(" error");
                }
            }catch(IllegalAccessException | InvocationTargetException e){
                log.info(e.getMessage());
            }
        }
        log.info("updateProfile() in service ended");
        return isUpdated;
    }

  */

    @Override
    public String updateProfile(UserDto dto) {
        log.info("updateProfile() in service started");
        String name = dto.getFullName();
        String email = dto.getEmail();
        String dob = dto.getDob();
        Long phoneNumber = dto.getPhoneNumber();
        String gender = dto.getGender();
        Location location = dto.getLocation();
        String password = dto.getPassword();
        String confirmPassword = dto.getConfirmPassword();
        try {
            if (name != null && !name.trim().isEmpty()) {
                if (name.matches("^[A-Z][a-zA-Z]*( [A-Z][a-zA-Z]*)*$")) {
                    if (name.length() >= 3 && name.length() <= 50) {
                        if (email.matches("^[a-zA-Z0-9_.%+-]+@gmail\\.com$")) {
                            if (phoneNumber != null) {
                                if (phoneNumber.toString().matches("(\\+91)?[976]\\d{9}")) {

                                            UserEntity entity = repository.getUserByEmail(email);
                                            BeanUtils.copyProperties(entity, dto);
                                            entity.setUpdatedBy(dto.getFullName());
                                            entity.setUpdatedDate(LocalDateTime.now());
                                            System.out.println(entity);
                                            log.info(String.valueOf(entity));
                                            repository.updateProfile(entity);
                                            return "updated";
                                } else
                                    return "Invalid: Phone number must start with 9, 7, or 6 and must contain exactly 10 digits.";
                            } else return "Invalid: Phone number cannot null.";
                        } else
                            return "Invalid: domain should be gmail.com, no spaces in between, and only '_', '.', '%', '+', and '-' are allowed.";
                    } else return "Invalid: Name should be of length min of 3 and max of 50";
                } else
                    return "Invalid:Name must start with an uppercase letter, each word must begin with an uppercase letter, and it should contain only alphabets and spaces.";
            } else return "Invalid: Name cannot be empty or null.";
        } catch (IllegalAccessException | InvocationTargetException e) {
            log.info(e.getMessage());
        }
        log.info("updateProfile() in service ended");
        return "updated";
    }

/*  @Override
    public String updateProfile(UserDto dto) {
        log.info("updateProfile() in service started");
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<UserDto>> validate = validator.validate(dto);
        if (!validate.isEmpty()) {
            for (ConstraintViolation<UserDto> userDtoConstraintViolation : validate) {
                log.info(userDtoConstraintViolation.getMessage());
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
                                                log.info("Before encryption: " + dto.getPassword());
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
                log.info(e.getMessage());
            }
        }
        log.info("updateProfile() in service ended");
        return "updated";
    }

 */

    @Override
    public String resetPassword(String email, String password, String confirmPassword) {
        log.info("resetPassword() in service started");
        List<UserEntity> entityList = repository.getAllUserData();
        for (UserEntity entity : entityList) {
            if (email.equals(entity.getEmail())) {
                if (password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*-+=_])\\S{8,}$")) {
                    if (password.equals(confirmPassword)) {
//                        UserEntity entity1=repository.getUserByEmail(email);
                        entity.setPassword(passwordEncoder.encode(password));
                        entity.setInvalidLogInCount(0);
                        entity.setLastLogIn(null);
                        repository.updateProfile(entity);
                        return "done";
                    } else return "Passwords must match";
                } else
                    return "Invalid: Password must have at least one uppercase letter, one lowercase letter, one digit, one special character, no spaces, not null, not empty and be at least 8 characters long.";
            }
        }
        log.info("resetPassword() in service ended");
        return "Recheck the entered email";
    }

    @Override
    public String deleteProfile(UserDto dto) {
        try{
            UserEntity entity= new UserEntity();
            BeanUtils.copyProperties(entity,dto);
            boolean isDeleted=repository.deleteProfile(entity);
            if(isDeleted==true) return "deleted";
        }catch (IllegalAccessException | InvocationTargetException e){
            log.info(e.getMessage());
        }
        return "Error while executing the request";
    }

    public Boolean validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            log.info("Invalid: Name cannot be empty or null.");
            return false;
        }
        if (!name.matches("[A-Z][a-zA-Z ]*")) {
            log.info("Invalid: Name must start with an uppercase letter and contain only alphabets and spaces.");
            return false;
        }
        if ((name.length() < 3)) {
            log.info("Invalid: Length should be >3 .");
            return false;
        }
        if ((name.length() > 50)) {
            log.info("Invalid: Length should be <50 .");
            return false;
        }
        return true;
    }

    public Boolean validatePhoneNumber(Long phoneNumber) {
        if (phoneNumber == null || Long.toString(phoneNumber).trim().isEmpty()) {
            log.info("Invalid: Phone number cannot be empty or null.");
            return false;
        }
        if (!Long.toString(phoneNumber).matches("[976][0-9]{9}")) {
            log.info("Invalid: Phone number must start with 9, 7, or 6 and contain exactly 10 digits.");
            return false;
        }
        return true;
    }

    public Boolean validateEmail(String email) {
        if (!Pattern.matches(emailRegex, email)) {
            log.info("Invalid: domain should be gmail.com, no spaces in between, and only '_', '.', '%', '+', and '-' are allowed.");
            return false;
        }
        return true;
    }

    public Boolean validatePassword(String password) {
        if (!Pattern.matches(passwordRegex, password)) {
            log.info("Invalid: Password must have at least one uppercase letter, one lowercase letter, one digit, one special character, no spaces, and be at least 8 characters long.");
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

    public String passwordGenerator() {
        String validCharacters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%^&*-=_+";
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();

        while (stringBuilder.length() < 13) {
            int index = random.nextInt(validCharacters.length());
            stringBuilder.append(validCharacters.charAt(index));
        }
        return stringBuilder.toString();
    }

    public void  sendEmail(String name, String email, String generatedPassword){

        final String username = "prajwal.xworkz@gmail.com";//your email
        final String password = "uhvk fzkx chqt zweg";// your app password

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS

        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(email)
            );
            message.setSubject("Autogenerated password");
            message.setText("Hello "+ name +","
                    + "\n\nThis is your auto-generated password: " +generatedPassword
                    + "\nSignIn with the above password and reset your new password."
                    +"\n\nThanks,"+"\n The Xworkz Team"
            );


            Transport.send(message);

            log.info("Done");

        } catch (MessagingException e) {
            log.info(e.getMessage());
        }
    }

    public ResponseEntity<byte[]>  captchaImage(HttpSession session){

        Captcha captcha = new Captcha();
        GeneratedCaptcha generatedCaptcha = captcha.generate();

        session.setAttribute("captchaText", generatedCaptcha.getCode());

        BufferedImage image = generatedCaptcha.getImage();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "png", baos);
        }catch (IOException e){
            log.info(e.getMessage());
        }
        byte[] imageBytes = baos.toByteArray();

        HttpHeaders headers = new HttpHeaders();
        headers.setCacheControl("no-store, no-cache, must-revalidate, max-age=0");
        headers.setContentType(MediaType.IMAGE_PNG);

        return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
    }

}


