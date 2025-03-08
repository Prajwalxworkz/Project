package com.xworkz.app.repo;

import com.xworkz.app.entity.UserEntity;
import org.hibernate.loader.collection.BasicCollectionJoinWalker;

import java.util.List;

public interface UserRepository {
    public Boolean save(UserEntity entity);

    List<UserEntity> getAllUserData();

    UserEntity getUserByEmail(String email);

    Boolean updateProfile(UserEntity entity);
}
