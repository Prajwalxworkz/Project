package com.xworkz.app.entity;

import com.xworkz.app.constants.Location;
import lombok.Data;

import javax.persistence.*;
@Data
@Entity
@Table
@NamedQuery(name="logInCredentials",query="select user from UserEntity user")
@NamedQuery(name="getUserByEmail",query="select user from UserEntity user where user.email=:emailId")
//@NamedQuery(name = "updateProfile",query="update UserEntity user set user.fullName=:fullName where user.email=:email")
@NamedQuery(name = "updateProfile",query="update UserEntity user set user.fullName=:fullName, user.dob=:dob, user.phoneNumber=:phoneNumber, user.gender=:gender, user.location=:location, user.password=:password where user.email=:email")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String fullName;
    private String email;
    private String dob;
    private Long phoneNumber;
    private String gender;
    private String location;
    private String password;
}
