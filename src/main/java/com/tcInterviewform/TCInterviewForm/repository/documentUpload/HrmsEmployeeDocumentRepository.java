/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcInterviewform.TCInterviewForm.repository.documentUpload;

import com.tcInterviewform.TCInterviewForm.model.documentUpload.HrmsEmployeeDocument;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author teamd
 */
@Repository
public interface HrmsEmployeeDocumentRepository
        extends JpaRepository<HrmsEmployeeDocument, Long> {

    @Query(value =
        "SELECT * FROM hrms_employee_documents d "
        + "WHERE d.id IN ( "
        + "SELECT MAX(id) "
        + "FROM hrms_employee_documents "
        + "WHERE employee_master_id = :employeeId "
        + "GROUP BY document_type "
        + ")",
        nativeQuery = true)
List<HrmsEmployeeDocument> getLatestDocumentsByEmployee(
        @Param("employeeId") Long employeeId
);

    List<HrmsEmployeeDocument> findByEmployeeMasterIdOrderByUploadedAtDesc(
            Long employeeId
    );

    List<HrmsEmployeeDocument> findByEmployeeMasterIdAndDocumentType(
            Long employeeId,
            String documentType
    );

    Optional<HrmsEmployeeDocument> findTopByEmployeeMasterIdAndDocumentTypeOrderByIdDesc(
            Long employeeId,
            String documentType
    );

    Long countByEmployeeMasterId(Long employeeId);

    Long countByEmployeeMasterIdAndDocumentType(
            Long employeeId,
            String documentType
    );
}
