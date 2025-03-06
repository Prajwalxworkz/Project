package com.xworkz.app.dto;

import com.xworkz.app.constants.Location;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class  UserDto {
    private Integer id;
    private String fullName;
    private String email;
    private String dob;
    private Long phoneNumber;
    private String gender;
    private String location;
    private String password;
    private String confirmPassword;
}
