/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcInterviewform.TCInterviewForm.repository.employeeMaster;

import com.tcInterviewform.TCInterviewForm.model.HrmsEmployeeSalary;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author teamd
 */
@Repository
public interface HrmsEmployeeSalaryRepository extends JpaRepository<HrmsEmployeeSalary, Long>{

    @Modifying
    @Transactional
    @Query("UPDATE HrmsEmployeeSalary s " +
           "SET s.isCurrent = 0 " +
           "WHERE s.employeeMaster.id = :empId")
    void updateOldSalaryAsInactive(@Param("empId") Long empId);

    Optional<HrmsEmployeeSalary> findTopByEmployeeMasterIdAndIsCurrentOrderByEffectiveFromDesc(Long employeeId, int i);
@Modifying
@Transactional
@Query(value =
        "UPDATE hrms_employee_salary " +
        "SET is_current = 0 " +
        "WHERE employee_master_id IN (:empIds) " +
        "AND is_current = 1",
        nativeQuery = true)
int inactiveOldSalaryBulk(@Param("empIds") List<Long> empIds);
    
}
