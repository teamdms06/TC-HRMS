/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcInterviewform.TCInterviewForm.repository.documentUpload;

import com.tcInterviewform.TCInterviewForm.model.documentUpload.HrmsDocumentUploadLink;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author teamd
 */
@Repository
public interface HrmsDocumentUploadLinkRepository
        extends JpaRepository<HrmsDocumentUploadLink, Long> {

    Optional<HrmsDocumentUploadLink> findByToken(String token);

}
