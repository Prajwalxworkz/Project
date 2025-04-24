package com.xworkz.app.entity;

import com.xworkz.app.constants.Location;
import lombok.Data;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name="userentity")
@NamedQuery(name="getAllUserData",query="select user from UserEntity user")
@NamedQuery(name="getUserByEmail",query="select user from UserEntity user where user.email=:emailId")
@NamedQuery(name = "updateProfile",query="update UserEntity user set user.fullName=:fullName, user.dob=:dob, user.phoneNumber=:phoneNumber, user.gender=:gender, user.location=:location, user.password=:password, user.invalidLogInCount=:invalidLogInCount, user.lastLogIn=:lastLogIn, user.updatedBy=:updatedBy, user.updatedDate=:updatedDate, user.profilePicture=:profilePicture where user.email=:email")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String createdBy;
    private LocalDateTime createdDate;
    private String updatedBy;
    private LocalDateTime updatedDate;
    private String fullName;
    private String profilePicture;
    private String email;
    private String dob;
    private Long phoneNumber;
    private String gender;
    @Enumerated(EnumType.STRING)
    private Location location;
    private String password;
    private int invalidLogInCount;
    @Column(name = "lastLogIn", columnDefinition = "TIMESTAMP(6)")
    private Instant lastLogIn;
}
