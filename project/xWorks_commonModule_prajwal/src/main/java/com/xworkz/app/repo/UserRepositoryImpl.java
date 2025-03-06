package com.xworkz.app.repo;

import com.xworkz.app.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
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
            entity.setPassword(encryption(entity.getPassword()));
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
    public List<UserEntity> logInCredentials() {
        System.out.println("------------------------------------");
        System.out.println("logInCredentials() in repo started");
        Boolean isMatching=false;
        EntityManager em = emf.createEntityManager();
        List<UserEntity> entityList= em.createNamedQuery("logInCredentials").getResultList();
        System.out.println("is resultSet empty: "+entityList.isEmpty());
        em.close();
        System.out.println("logInCredentials() in repo ended");
        System.out.println("------------------------------------");
        return entityList;
    }

    @Override
    public UserEntity getUserByEmail(String email) {
        System.out.println("------------------------------------");
        System.out.println("getUserByEmail() in repo started");
        EntityManager em = emf.createEntityManager();
        UserEntity entity=(UserEntity) em.createNamedQuery("getUserByEmail").setParameter("emailId",email).getSingleResult();
        em.close();
        System.out.println("getUserByEmail() in repo ended");
        System.out.println("------------------------------------");
        return entity;
    }

    @Override
    public Boolean updateProfile(UserEntity entity) {
        Boolean isUpdated=false;
        System.out.println("------------------------------------");
        System.out.println("updateProfile() in repo  started");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(entity);
        isUpdated=true;
        em.getTransaction().commit();
        em.close();
        System.out.println("updateProfile() in repo ended");
        System.out.println("------------------------------------");
        return isUpdated;
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
