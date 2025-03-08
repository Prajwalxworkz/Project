package com.xworkz.app.repo;

import com.xworkz.app.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository{
    @Autowired
    EntityManagerFactory emf;

    @Override
    public Boolean save(UserEntity entity) {
        System.out.println("------------------------------------");
        System.out.println("save() in repo started");
        Boolean isSaved=false;
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            System.out.println(entity.getFullName());
            System.out.println(entity.getEmail());
            System.out.println(entity.getGender());
            System.out.println(entity.getDob());
            System.out.println(entity.getPhoneNumber());
            System.out.println(entity.getLocation());
            System.out.println(entity.getPassword());
            System.out.println("Moving to db");
            em.persist(entity);
            isSaved=true;
            System.out.println("Is data saved?: "+isSaved);
            em.getTransaction().commit();
        }catch (Exception e){
            em.getTransaction().rollback();
            System.out.println(e.getMessage());
        }finally {
            em.close();
        }
        System.out.println("save() in repo ended");
        System.out.println("------------------------------------");
        return isSaved;
    }

    @Override
    public List<UserEntity> getAllUserData() {
        System.out.println("------------------------------------");
        System.out.println("getAllUserData() in repo started");
        Boolean isMatching=false;
        EntityManager em = emf.createEntityManager();
        List<UserEntity> entityList= em.createNamedQuery("getAllUserData").getResultList();
        System.out.println("is resultSet empty: "+entityList.isEmpty());
        em.close();
        System.out.println("getAllUserData() in repo ended");
        System.out.println("------------------------------------");
        return entityList;
    }

    @Override
    public UserEntity getUserByEmail(String email) {
        System.out.println("------------------------------------");
        System.out.println("getUserByEmail() in repo started");
        EntityManager em = emf.createEntityManager();
        UserEntity entity=new UserEntity();
        try {
             entity = (UserEntity) em.createNamedQuery("getUserByEmail").setParameter("emailId", email).getSingleResult();
            System.out.println("getUserByEmail() in repo ended");
            System.out.println("------------------------------------");
            return entity;
        }catch (NoResultException e){
            System.out.println(e.getMessage());
        }finally {
            em.close();
        }
        return entity;
    }

    @Override
    public Boolean updateProfile(UserEntity entity) {
        Boolean isUpdated=false;
        System.out.println("------------------------------------");
        System.out.println("updateProfile() in repo  started");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Query query=em.createNamedQuery("updateProfile");
        query.setParameter("fullName",entity.getFullName());
        query.setParameter("email",entity.getEmail());
        query.setParameter("dob",entity.getDob());
        query.setParameter("gender",entity.getGender());
        query.setParameter("location",entity.getLocation()  );
        query.setParameter("phoneNumber",entity.getPhoneNumber());
        query.setParameter("password",entity.getPassword());
        query.setParameter("lastLogIn",entity.getLastLogIn());
        query.setParameter("invalidLogInCount",entity.getInvalidLogInCount());
        query.executeUpdate();
//        em.merge(entity);
        isUpdated=true;
        em.getTransaction().commit();
        em.close();
        System.out.println("updateProfile() in repo ended");
        System.out.println("------------------------------------");
        return isUpdated;
    }

}
