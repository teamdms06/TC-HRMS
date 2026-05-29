/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcInterviewform.TCInterviewForm.repository;

import com.tcInterviewform.TCInterviewForm.model.City;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author SIF
 */
@Repository
public interface CityRepository extends JpaRepository<City, Integer>{

    @Query(value="SELECT * FROM city",nativeQuery=true)
    public List<City> getCitys();
    
     @Query(value="SELECT * FROM city where state_id=?1",nativeQuery=true)
    public List<City> getCityListById(int stateId);
    
}
