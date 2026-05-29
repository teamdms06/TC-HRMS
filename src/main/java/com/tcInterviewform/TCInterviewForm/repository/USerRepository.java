/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcInterviewform.TCInterviewForm.repository;

import com.tcInterviewform.TCInterviewForm.model.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author SIF
 */
@Repository
public interface USerRepository extends JpaRepository<User, Integer> {

    @Query(value = "SELECT * FROM `user` WHERE username=?1 AND password=?2", nativeQuery = true)
    public User findByUserName(String username, String password);

    @Query(value = "SELECT * FROM `user` WHERE username=?1", nativeQuery = true)
    public User findByUsername(String username);

    @Query(value = "SELECT * FROM `user` WHERE email=?1", nativeQuery = true)
    public List<User> findByEmail(String email);

//    @Query(value = "SELECT * FROM user where department=?1", nativeQuery = true)
//    public List<User> getTrainerList(String department);
//
//    @Query(value = "SELECT name FROM user where id=?1", nativeQuery = true)
//    public String getTrainerNameById(String trainerName);
   @Query(value = "SELECT * FROM user", nativeQuery = true)
    public List<User> getTLList();

    @Query(value = "SELECT email FROM `user` WHERE department='HR' and  Status='ACTIVE'", nativeQuery = true)
    public String getUserIdEmail(String jobappInteviewVenue);
    @Query(value = "SELECT DISTINCT email FROM `user` WHERE department = 'HR' AND STATUS = 'ACTIVE' AND email IS NOT NULL", nativeQuery = true)
    public List<String> getHrEmail();
    @Query(value = "SELECT id FROM `user` WHERE department='HR' and  location=?1", nativeQuery = true)
    public Integer findIdByVenue(String jobappInteviewVenue);
    
    @Query(value = "SELECT email FROM `user` WHERE id=?1", nativeQuery = true)
    public String getUserEmailByID(Integer userId);

}
