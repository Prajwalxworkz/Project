package com.xworkz.app.dto;

import com.xworkz.app.constants.Location;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

import javax.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class  UserDto {
    private Integer id;
    @NotNull(message = "name should not be null")
    @Size(min=3,max=5,message = "Name name should be min 3 and max 5")
//    @Min(value =3, message = "characters should be >3")
//    @Max(value =5, message = "characters should be <5")
    private String fullName;
    private String email;
    private String dob;
    private Long phoneNumber;
    private String gender;
    private Location location;
    private String password;
    private String confirmPassword;
}
