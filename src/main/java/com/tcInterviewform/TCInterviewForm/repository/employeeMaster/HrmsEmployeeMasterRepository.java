/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcInterviewform.TCInterviewForm.repository.employeeMaster;

import com.tcInterviewform.TCInterviewForm.model.HrmsEmployeeMaster;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author teamd
 */
@Repository
public interface HrmsEmployeeMasterRepository extends JpaRepository<HrmsEmployeeMaster, Long> {

    Optional<HrmsEmployeeMaster> findByEmployeeId(String employeeId);

    List<HrmsEmployeeMaster> findTop20ByEmployeeNameContainingIgnoreCaseOrEmployeeIdContainingIgnoreCase(
            String name,
            String employeeId
    );

//    @Transactional
//    @Modifying
//    @Query(value
//            = "INSERT INTO hrms_employee_master_backup "
//            + "SELECT NULL, e.*, e.id, :reason, NOW() "
//            + "FROM hrms_employee_master e "
//            + "WHERE e.id IN (:empIds)",
//            nativeQuery = true)
//    int backupEmployeeMasterBulk(@Param("empIds") List<Long> empIds,
//            @Param("reason") String reason);

@Transactional
@Modifying
@Query(value =
        "INSERT INTO hrms_employee_master_backup ( " +
        "employee_id, employee_name, gender, date_of_joining, date_of_birth, " +
        "location, department, designation, grade, process_name, email_id, " +
        "contact_number, alternate_number, education, aadhar, pan, employee_address, " +
        "state, city, pincode, final_salary, current_ctc, pf_status, uan, " +
        "current_status, live_status, date_of_leaving, created_at, updated_at, " +
        "old_designation, old_level, old_salary, original_employee_master_id, " +
        "backup_reason, backup_at " +
        ") " +
        "SELECT " +
        "e.employee_id, e.employee_name, e.gender, e.date_of_joining, e.date_of_birth, " +
        "e.location, e.department, e.designation, e.grade, e.process_name, e.email_id, " +
        "e.contact_number, e.alternate_number, e.education, e.aadhar, e.pan, e.employee_address, " +
        "e.state, e.city, e.pincode, e.final_salary, e.current_ctc, e.pf_status, e.uan, " +
        "e.current_status, e.live_status, e.date_of_leaving, e.created_at, e.updated_at, " +
        "e.old_designation, e.old_level, e.old_salary, e.id, " +
        ":reason, NOW() " +
        "FROM hrms_employee_master e " +
        "WHERE e.id IN (:empIds)",
        nativeQuery = true)
int backupEmployeeMasterBulk(@Param("empIds") List<Long> empIds,
                             @Param("reason") String reason);

    public List<HrmsEmployeeMaster> findByEmployeeIdIn(List<String> excelEmployeeIds);

}
