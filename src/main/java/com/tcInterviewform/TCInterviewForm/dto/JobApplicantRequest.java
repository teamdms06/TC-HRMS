/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcInterviewform.TCInterviewForm.dto;

import java.util.List;
import lombok.Data;

/**
 *
 * @author teamd
 */
@Data
public class JobApplicantRequest {
    private String jobappConsultancyWalkinName;
    private String jobappInteviewVenue;
    private String jobappRefferedByName;
    private String jobappInterviewerName;
    private String jobappBirthdate;
    private String jobappBirthplace;
    private Integer jobappAge;
    private String jobappGender;
    private String jobappMaritalStatus;
    private String jobappEmail;
    private String jobappPhone;
    private String jobappAltPhone;
    private List<String> jobappLanguagesKnown;
    private String jobappNationality;
    private String jobappAddressForCorrespondance1;
    private String jobappAddressForCorrespondance2;
    private String jobappCity;
    private String jobappState;
    private String jobappPostalCode;
    private String jobappTimeRequiredForJoiningIfSelect;
}
