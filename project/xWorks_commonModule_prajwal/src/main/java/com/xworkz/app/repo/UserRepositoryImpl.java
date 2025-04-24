package com.xworkz.app.repo;

import com.xworkz.app.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
public class UserRepositoryImpl implements UserRepository{
    @Autowired
    EntityManagerFactory emf;

    @Override
    public Boolean save(UserEntity entity) {
        log.info("------------------------------------");
        log.info("save() in repo started");
        Boolean isSaved=false;
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            log.info(String.valueOf(entity));
//            log.info("Saving -1 as the default value for invalidLoginCount");
//            entity.setInvalidLogInCount(-1);
            log.info("Moving to db");
            em.persist(entity);
            isSaved=true;
            log.info("Is data saved?: "+isSaved);
            em.getTransaction().commit();
        }catch (Exception e){
            em.getTransaction().rollback();
            log.info(e.getMessage());
        }finally {
            em.close();
        }
        log.info("save() in repo ended");
        log.info("------------------------------------");
        return isSaved;
    }

    @Override
    public List<UserEntity> getAllUserData() {
        log.info("------------------------------------");
        log.info("getAllUserData() in repo started");
        EntityManager em = emf.createEntityManager();
        List<UserEntity> entityList=new ArrayList<>();
        try {
            entityList = em.createNamedQuery("getAllUserData").getResultList();
            log.info("is resultSet empty: " + entityList.isEmpty());
        }catch (Exception e){
            log.info(e.getMessage());
        }finally {
        em.close();
        }
        log.info("getAllUserData() in repo ended");
        log.info("------------------------------------");
        return entityList;
    }

    @Override
    public UserEntity getUserByEmail(String email) {
        log.info("------------------------------------");
        log.info("getUserByEmail() in repo started");
        EntityManager em = emf.createEntityManager();
        UserEntity entity=new UserEntity();
        try {
             entity = (UserEntity) em.createNamedQuery("getUserByEmail").setParameter("emailId", email).getSingleResult();
            log.info(String.valueOf(entity));
            log.info("getUserByEmail() in repo ended");
            log.info("------------------------------------");
            return entity;
        }catch (NoResultException e){
            log.info("catch block  of getUserByEmail()");
            log.info(e.getMessage());
        }finally {
            em.close();
        }
        return entity;
    }

    @Override
    public Boolean updateProfile(UserEntity entity) {
        Boolean isUpdated=false;
        log.info("------------------------------------");
        log.info("updateProfile() in repo  started");
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            log.info(entity.getFullName());
            log.info(entity.getEmail());
            log.info(entity.getGender());
            log.info(entity.getDob());
            log.info(String.valueOf(entity.getPhoneNumber()));
            log.info(String.valueOf(entity.getLocation()));
            log.info(entity.getPassword());
            log.info(String.valueOf(entity.getLastLogIn()));
            log.info(String.valueOf(entity.getInvalidLogInCount()));
            Query query = em.createNamedQuery("updateProfile");
            query.setParameter("fullName", entity.getFullName());
            query.setParameter("email", entity.getEmail());
            query.setParameter("dob", entity.getDob());
            query.setParameter("gender", entity.getGender());
            query.setParameter("location", entity.getLocation());
            query.setParameter("phoneNumber", entity.getPhoneNumber());
            query.setParameter("password", entity.getPassword());
            query.setParameter("lastLogIn", entity.getLastLogIn());
            query.setParameter("invalidLogInCount", entity.getInvalidLogInCount());
            query.setParameter("updatedBy", entity.getUpdatedBy());
            query.setParameter("updatedDate", entity.getUpdatedDate());
            query.setParameter("profilePicture", entity.getProfilePicture());
            query.executeUpdate();
//        em.merge(entity);
            isUpdated = true;
            em.getTransaction().commit();
        }catch (Exception e){
            em.getTransaction().rollback();
            log.info(e.getMessage());
        }finally {
            em.close();
        }
        log.info("updateProfile() in repo ended");
        log.info("------------------------------------");
        return isUpdated;
    }

    @Override
    public boolean deleteProfile(UserEntity entity) {
        boolean isDeleted=false;
        log.info("------------------------------------");
        log.info("deleteProfile() in repo started");
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            log.info(String.valueOf(entity));
            UserEntity entity1=em.find(UserEntity.class,entity.getId());
            log.info(String.valueOf(entity1));
            if(entity1!=null) {
                em.remove(entity1);
            }
            isDeleted = true;
            em.getTransaction().commit();
        }catch (Exception e){
            em.getTransaction().rollback();
            log.info(e.getMessage());
        }finally {
            em.close();
        }
        log.info("deleteProfile() in repo ended");
        log.info("------------------------------------");
        return isDeleted;
    }

}
