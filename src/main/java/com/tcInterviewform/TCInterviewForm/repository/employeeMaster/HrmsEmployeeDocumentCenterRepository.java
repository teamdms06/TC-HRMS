/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcInterviewform.TCInterviewForm.repository.employeeMaster;

import com.tcInterviewform.TCInterviewForm.model.HrmsDocumentCenter;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author teamd
 */
@Repository
public interface HrmsEmployeeDocumentCenterRepository extends JpaRepository<HrmsDocumentCenter, Long>{
    
    Long countByEmployeeMasterIdAndDocumentTypeAndStatus(
        Long employeeId,
        String documentType,
        String status
);

    Optional<HrmsDocumentCenter>  findTopByEmployeeMasterIdAndDocumentTypeOrderByIdDesc(Long employeeId, String documentType);

    public List<HrmsDocumentCenter> findByEmployeeMasterIdAndDocumentTypeOrderByIdDesc(Long employeeId, String type);
}                                  