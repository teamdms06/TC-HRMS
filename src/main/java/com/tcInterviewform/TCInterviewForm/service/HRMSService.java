/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcInterviewform.TCInterviewForm.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tcInterviewform.TCInterviewForm.communication.EmailCollection;
import com.tcInterviewform.TCInterviewForm.communication.service.EmailSender;
import com.tcInterviewform.TCInterviewForm.dto.ExperienceRequest;
import com.tcInterviewform.TCInterviewForm.dto.FamilyDetailRequest;
import com.tcInterviewform.TCInterviewForm.dto.JobApplicantRequest;
import com.tcInterviewform.TCInterviewForm.dto.SkillRequest;
import com.tcInterviewform.TCInterviewForm.model.EmpFamilyDetails;
import com.tcInterviewform.TCInterviewForm.model.EmpPreviousExperice;
import com.tcInterviewform.TCInterviewForm.model.EmpSkills;
import com.tcInterviewform.TCInterviewForm.model.HrFeedback;
import com.tcInterviewform.TCInterviewForm.model.JobApplicants;
//import com.tcInterviewform.TCInterviewForm.model.LetterIssue.Loi;
import com.tcInterviewform.TCInterviewForm.model.ResumesMaster;
import com.tcInterviewform.TCInterviewForm.repository.EmpFamilyDetailsRepository;
import com.tcInterviewform.TCInterviewForm.repository.EmpPreviousExpericeRepository;
import com.tcInterviewform.TCInterviewForm.repository.EmpSkillRepository;
import com.tcInterviewform.TCInterviewForm.repository.HRFeedbackRepository;
import com.tcInterviewform.TCInterviewForm.repository.HRmsRepository;
import com.tcInterviewform.TCInterviewForm.repository.JobApplicantRepository;
//import com.tcInterviewform.TCInterviewForm.repository.LetterIssue.LoiRepository;
//import com.tcInterviewform.TCInterviewForm.repository.LetterIssue.OldEmployeeDetailsRepository;
import com.tcInterviewform.TCInterviewForm.repository.MetaAddsLeadRepository;
import com.tcInterviewform.TCInterviewForm.repository.ResumeMasterRepository;
//import com.tcInterviewform.TCInterviewForm.repository.TrainingBatchRepository;
import com.tcInterviewform.TCInterviewForm.repository.USerRepository;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author SIF
 */
@Service
@RequiredArgsConstructor
public class HRMSService {

    private final JobApplicantRepository applicantRepo;
    private final EmpSkillRepository skillRepo;
    private final EmpPreviousExpericeRepository experianceRepo;
    private final EmpFamilyDetailsRepository familyDtlRepo;
    private final HRmsRepository hrmsRepository;
    private final HRFeedbackRepository hrFeedbackRepo;
    private final EmailSender emailSender;
    private final ResumeMasterRepository resumeMasterRepo;
    private final MetaAddsLeadRepository metaAddsLeadRepo;
    private final USerRepository userRepo;
//    private final OldEmployeeDetailsRepository oldEmpDataRepo;
//    private final LoiRepository loiRepo;
    private final ObjectMapper objectMapper;
    private final S3Service s3Service;

    ;

    public List getNewApplications(String status, String role, Integer id) {
//        if(role.equalsIgnoreCase("Admin")){
        return hrmsRepository.getAllNewApplications(status, id);
//        }else{
//            return hrmsRepository.getNewApplications(status, id);
//        }

    }

    public List getStatusWiseApplications(String status) {
        return hrmsRepository.getStatusWiseApplications(status);
    }

    public List getAllNewApplications(Integer jAppId) {
        return hrmsRepository.getAllNewApplications(jAppId);
    }

    public List<HrFeedback> getHRFeedbacks(Integer jAppId) {
        return hrFeedbackRepo.getHrFeedbackbyJobAppId(jAppId);

    }

    public void saveFeedback(HrFeedback hrFeedback) {
        hrFeedbackRepo.save(hrFeedback);
    }

    public String getFilePath(Integer jAppId) {
        return hrmsRepository.getFilePath(jAppId);
    }

    public void updateApplicationStatus(Integer applicantId, String finalStatus, Integer feedCount, Integer nxtRound, String OffrSal) {
        hrmsRepository.updateStatus(applicantId, finalStatus, feedCount, nxtRound, OffrSal);
    }

    public void getHRDashboardCounts(Integer id, ModelMap model) {

        model.addAttribute("FRESH", hrmsRepository.HRCountFresh(id));
        model.addAttribute("SELECTED", hrmsRepository.HRCountSelected());
        model.addAttribute("REJECTED", hrmsRepository.HRCountRejected());
        model.addAttribute("HOLD", hrmsRepository.HRCountHold());
        model.addAttribute("NxtRound", hrmsRepository.CountNxtRound(id));
        model.addAttribute("ClientRound", hrmsRepository.CountClientRound(id));
//        model.addAttribute("all", itrRopository.adminCountall(cpId));
    }

    public void getAdminDashboardCounts(Integer userId, ModelMap model) {

        model.addAttribute("FRESH", hrmsRepository.adminCountFresh());
        model.addAttribute("SELECTED", hrmsRepository.HRCountSelected());
        model.addAttribute("REJECTED", hrmsRepository.HRCountRejected());
        model.addAttribute("HOLD", hrmsRepository.HRCountHold());
        model.addAttribute("NxtRound", hrmsRepository.adminCountNxtRound());
        model.addAttribute("ClientRound", hrmsRepository.adminCountClientRound());
//        model.addAttribute("all", itrRopository.adminCountall(cpId));
    }

    public void getTLDashboardCounts(Integer id, ModelMap model) {
        model.addAttribute("FRESH", hrmsRepository.TLCountFresh(id));
        model.addAttribute("SELECTED", hrmsRepository.HRCountSelected());
        model.addAttribute("REJECTED", hrmsRepository.HRCountRejected());
        model.addAttribute("HOLD", hrmsRepository.HRCountHold());
        model.addAttribute("NxtRound", hrmsRepository.CountNxtRound(id));
    }

//    public List[] getLast3Months() {
//       return hrmsRepository.getLast3Months();
//    }
    public ArrayList<String> getLast3Months() {
        return hrmsRepository.getLast3Months();
    }

//    public ArrayList<String> getAllLast3Months() {
//        return hrmsRepository.getAllLast3Months();
//    }
    public List<Object[]> getSelectedLast3Months() {
        return hrmsRepository.getSelectedLast3Months();
    }

    public List<Object[]> getRejectedLast3Months() {
        return hrmsRepository.getRejectedLast3Months();
    }

    public List<Object[]> getAllLast3Months() {
        // System.out.println("++"+hrmsRepository.getAllLast3Months());
        return hrmsRepository.getAllLast3Months();
    }

    public List<Object[]> getOverAllPerData() {
        // System.out.println("++"+hrmsRepository.getAllLast3Months());
        return hrmsRepository.getOverAllPerData();
    }

//    public List getBatchList() {
//         return trainingRepo.getBatchList();
//    }
//    public List getSelectedAgentList() {
//      return hrmsRepository.getSelectedAgentFortraining();
//    }
//    public String getTrainerNameById(String trainerName) {
//         return userRepo.getTrainerNameById(trainerName);
//    }
//    public List getAgentNameList(List<String> myList) {
//         return hrmsRepository.getAgentNamesList(myList);
//    }
//    public String getHREmailOnLocation(String jobappInteviewVenue) {
//      return  userRepo.getUserIdEmail(jobappInteviewVenue);
//    }
//    public Integer getHRId(String jobappInteviewVenue) {
//        return  userRepo.findIdByVenue(jobappInteviewVenue);
//    }
    @Transactional
    public void saveSkill(String l1, Integer jobappId) throws JSONException {
        try {
            List<SkillRequest> skillsList = objectMapper.readValue(
                    l1,
                    new TypeReference<List<SkillRequest>>() {
            }
            );

            List<EmpSkills> empSkillsList = skillsList.stream()
                    .map(req -> {
                        EmpSkills skill = new EmpSkills();
                        skill.setSkillJobappId(jobappId);
                        skill.setSkillQualification(req.getQualification());
                        skill.setSkillCollegeUniInstituteName(req.getInstitute());
                        skill.setSkillExamination(req.getExamination());
                        skill.setSkillDivisionPercentageOfMarks(req.getMarks());
                        skill.setSkillYearOfPassing(req.getPassingyear());
                        return skill;
                    })
                    .collect(Collectors.toList());

            skillRepo.saveAll(empSkillsList);

        } catch (Exception e) {
            throw new RuntimeException("Invalid skill JSON data", e);
        }
    }

    @Transactional
    public String saveExperiance(String l2, Integer jobappId) {

        try {
            List<ExperienceRequest> expList = objectMapper.readValue(
                    l2,
                    new TypeReference<List<ExperienceRequest>>() {
            }
            );

            String expLevel = expList.stream()
                    .anyMatch(e -> e.getCompany() != null && !e.getCompany().equalsIgnoreCase("NA"))
                    ? "Experienced"
                    : "Fresher";

            List<EmpPreviousExperice> experiences = expList.stream()
                    .map(req -> {
                        EmpPreviousExperice exp = new EmpPreviousExperice();

                        exp.setExpJobappId(jobappId);
                        exp.setExpPreviousCompanyName(req.getCompany());
                        exp.setExpDesignation(req.getDegsignation());
                        exp.setExpSalaryOffered(req.getPreSalary());
                        exp.setExpDuration(req.getDuration());
                        exp.setExpReasonToLeaveCompany(req.getLeavereason());

                        return exp;
                    })
                    .collect(Collectors.toList());

            experianceRepo.saveAll(experiences);

            return expLevel;

        } catch (Exception e) {
            throw new RuntimeException("Invalid experience JSON", e);
        }
    }

    @Transactional
    public void saveFamilyDtl(String l3, Integer jobappId) {

        try {

            List<FamilyDetailRequest> familyList = objectMapper.readValue(
                    l3,
                    new TypeReference<List<FamilyDetailRequest>>() {
            }
            );

            List<EmpFamilyDetails> familyDetails = familyList.stream()
                    .map(req -> {

                        EmpFamilyDetails family = new EmpFamilyDetails();

                        family.setFamilyJobappId(jobappId);
                        family.setFamilyFullName(req.getFullName());
                        family.setFamilyRealation(req.getRelation());
                        family.setFamilyOccupation(req.getOccupation());
                        family.setFamilyPhone(req.getContact());

                        return family;
                    })
                    .collect(Collectors.toList());

            familyDtlRepo.saveAll(familyDetails);

        } catch (Exception e) {
            throw new RuntimeException("Invalid family details JSON", e);
        }
    }

    @Transactional
    public void saveHrFeedback(Integer jobappId) {
        HrFeedback hrFeed = new HrFeedback();
        hrFeed.setApplicantId(jobappId);
        hrFeed.setFeedbackBy("Self");
        hrFeed.setTypingTest(0);
        hrFeed.setWorkExperience(0);
        hrFeed.setJobKnowledge(0);
        hrFeed.setCommunicationSkills(0);
        hrFeed.setMotivationInitiative(0);
        hrFeed.setEducationBackground("NA");
        hrFeed.setPersonality(0);
        hrFeed.setLanguage("Na");
        hrFeed.setOfferedSalary("0");
        hrFeed.setExpectedSalary("0");
        hrFeed.setRemark("NA");
        hrFeed.setCount(0);
        hrFeed.setFinalStatus("Fresh");
        hrFeed.setInterviewerName("NA");
        hrFeed.setFeedbackAttempt(0);
        hrFeedbackRepo.save(hrFeed);
//        System.out.println("");
    }

    @Transactional
    public JobApplicants saveJobDetails(String applic, ResumesMaster rMaster) throws JSONException, ParseException {
        try {
            List<JobApplicantRequest> applicants = objectMapper.readValue(
                    applic,
                    new TypeReference<List<JobApplicantRequest>>() {
            }
            );

            if (applicants.isEmpty()) {
                throw new RuntimeException("Applicant data is empty");
            }

            JobApplicantRequest req = applicants.get(0);

            JobApplicants jobApplicant = new JobApplicants();

            jobApplicant.setJobappJobId(1);
            jobApplicant.setJobappJtId(1);
            jobApplicant.setJobappOtpId(0);

            jobApplicant.setJobappConsultancyWalkinName(req.getJobappConsultancyWalkinName());
            jobApplicant.setJobappInteviewVenue(req.getJobappInteviewVenue());
            jobApplicant.setJobappRefferedByName(req.getJobappRefferedByName());
            jobApplicant.setJobappInterviewerName(req.getJobappInterviewerName());

            jobApplicant.setJobappBirthdate(
                    java.sql.Date.valueOf(LocalDate.parse(req.getJobappBirthdate()))
            );

            jobApplicant.setJobappBirthplace(req.getJobappBirthplace());
            jobApplicant.setJobappAge(req.getJobappAge());
            jobApplicant.setJobappGender(req.getJobappGender());
            jobApplicant.setJobappMaritalStatus(req.getJobappMaritalStatus());
            jobApplicant.setJobappEmail(req.getJobappEmail());
            jobApplicant.setJobappPhone(req.getJobappPhone());
            jobApplicant.setJobappAltPhone(req.getJobappAltPhone());
            jobApplicant.setJobappLanguagesKnown(
                    req.getJobappLanguagesKnown() == null
                    ? ""
                    : String.join(",", req.getJobappLanguagesKnown())
            );
            jobApplicant.setJobappNationality(req.getJobappNationality());
            jobApplicant.setJobappAddressForCorrespondance1(req.getJobappAddressForCorrespondance1());
            jobApplicant.setJobappAddressForCorrespondance2(req.getJobappAddressForCorrespondance2());
            jobApplicant.setJobappCity(req.getJobappCity());
            jobApplicant.setJobappState(req.getJobappState());
            jobApplicant.setJobappPostalCode(req.getJobappPostalCode());

            jobApplicant.setJobappTimeRequiredForJoiningIfSelect(
                    LocalDate.parse(req.getJobappTimeRequiredForJoiningIfSelect())
            );

            jobApplicant.setJobappResumeFilePath(rMaster.getFileName());
            jobApplicant.setStatus("FRESH");
            jobApplicant.setResumesMasterId(rMaster.getId());
            jobApplicant.setFeedbackCount(0);
            jobApplicant.setNextRound(0);

            return applicantRepo.save(jobApplicant);

        } catch (Exception e) {
            throw new RuntimeException("Invalid applicant JSON data", e);
        }
//        JobApplicants jobApplicant = new JobApplicants();
//        JSONObject Jobj = new JSONObject();
//        for (int i = 0; i < applic.length(); i++) {
//            Jobj = applic.getJSONObject(i);
//            //   System.out.println(Jobj.getString("jobappTimeRequiredForJoiningIfSelect"));
//            jobApplicant.setJobappJobId(1);
//
//            jobApplicant.setJobappJtId(1);
//            jobApplicant.setJobappOtpId(000);
//            jobApplicant.setJobappConsultancyWalkinName(Jobj.getString("jobappConsultancyWalkinName"));
//            jobApplicant.setJobappInteviewVenue(Jobj.getString("jobappInteviewVenue"));
//            jobApplicant.setJobappRefferedByName(Jobj.getString("jobappRefferedByName"));
//            jobApplicant.setJobappInterviewerName(Jobj.getString("jobappInterviewerName"));
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//            String dob = Jobj.getString("jobappBirthdate");
//            Date dt1 = sdf.parse(dob);
//            jobApplicant.setJobappBirthdate(dt1);
//            jobApplicant.setJobappBirthplace(Jobj.getString("jobappBirthplace"));
//            jobApplicant.setJobappAge(Jobj.getInt("jobappAge"));
//            jobApplicant.setJobappGender(Jobj.getString("jobappGender"));
//            jobApplicant.setJobappMaritalStatus(Jobj.getString("jobappMaritalStatus"));
//            jobApplicant.setJobappEmail(Jobj.getString("jobappEmail"));
//            jobApplicant.setJobappPhone(Jobj.getString("jobappPhone"));
//            jobApplicant.setJobappAltPhone(Jobj.getString("jobappAltPhone"));
//            jobApplicant.setJobappLanguagesKnown(Jobj.getString("jobappLanguagesKnown"));
//            jobApplicant.setJobappNationality(Jobj.getString("jobappNationality"));
//            jobApplicant.setJobappAddressForCorrespondance1(Jobj.getString("jobappAddressForCorrespondance1"));
//            jobApplicant.setJobappAddressForCorrespondance2(Jobj.getString("jobappAddressForCorrespondance2"));
//            jobApplicant.setJobappCity(Jobj.getString("jobappCity"));
//            jobApplicant.setJobappState(Jobj.getString("jobappState"));
//            jobApplicant.setJobappPostalCode(Jobj.getString("jobappPostalCode"));
//            String joiningDt = Jobj.getString("jobappTimeRequiredForJoiningIfSelect");
//            LocalDate dt2 = LocalDate.parse(joiningDt);
//            jobApplicant.setJobappTimeRequiredForJoiningIfSelect(dt2);
////                jobApplicant.setJobappResumeFilePath(fileName);
//            jobApplicant.setJobappResumeFilePath(rMaster.getFileName());
//            jobApplicant.setStatus("FRESH");
//            jobApplicant.setResumesMasterId(rMaster.getId());
//            jobApplicant.setFeedbackCount(0);
//            //aasing Location wise HR
////                jobApplicant.setNextRound(hrmsService.getHRId(Jobj.getString("jobappInteviewVenue")));
//            // comemt bcoz now we show to all hr 10042026
////            jobApplicant.setNextRound(userRepo.findIdByVenue(Jobj.getString("jobappInteviewVenue")));
//            jobApplicant.setNextRound(0);
//            // setResumeFilePath(resumeFilePath);
//        }
//        JobApplicants jobapplier = applicantRepo.save(jobApplicant);
//        System.out.println("main save sussfully");
////            LOGGER.info("main save sussfully");
//        return jobapplier;
    }

    @Transactional
    public boolean saveCompleteApplicant(
            String skillsJson,
            String experienceJson,
            String jobAppJson,
            MultipartFile file,
            Locale locale) {

        try {
            String fileUrl = s3Service.uploadFile(file);

            ResumesMaster fileDB = new ResumesMaster();
            fileDB.setFileName(file.getOriginalFilename());
            fileDB.setCvUrl(fileUrl);

            ResumesMaster rMaster = resumeMasterRepo.save(fileDB);

            JobApplicants jobApplicant = saveJobDetails(jobAppJson, rMaster);

            saveSkill(skillsJson, jobApplicant.getJobappId());

            String expLevel = saveExperiance(experienceJson, jobApplicant.getJobappId());

            saveHrFeedback(jobApplicant.getJobappId());

//        return mailSender(jobApplicant, expLevel, locale, file);
            return true;

        } catch (Exception e) {
            throw new RuntimeException("Failed to save applicant details", e);
        }
    }

    public boolean mailSender(JobApplicants jAppli, String explevel, Locale locale, MultipartFile file) {
        EmailCollection emailcollect = new EmailCollection(jAppli.getJobappCreatedAt(), "", "", "", jAppli.getJobappInterviewerName(), jAppli.getJobappPhone(),
                jAppli.getJobappEmail(), jAppli.getJobappGender(), jAppli.getJobappRefferedByName(), jAppli.getJobappAddressForCorrespondance1(), jAppli.getJobappCity(), jAppli.getJobappState(), explevel, jAppli.getJobappConsultancyWalkinName());
//        String toMail = hrmsService.getHREmailOnLocation(jAppli.getJobappInteviewVenue());
        List<String> toMail = userRepo.getHrEmail();
        String emails = String.join(",", toMail);
//        String toMail = "teamdms1@theconnectionsindia.com";
        emailcollect.setSUBJECT("Interview For Post -" + jAppli.getJobappConsultancyWalkinName() + " - " + jAppli.getJobappInterviewerName());
//        emailcollect.setTO(toMail);
        emailcollect.setTO(emails);
        try {
            this.emailSender.sendSimpleMail("hrMail", locale, emailcollect, file);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }

    }

    public JobApplicants getJobapplierByJobId(Integer applicantId) {
        return applicantRepo.findByappId(applicantId);
    }

    public boolean NextRoundmailSender(JobApplicants jAppli, String explevel, Locale locale) {
        EmailCollection emailcollect = new EmailCollection(jAppli.getJobappCreatedAt(), "", "", "", jAppli.getJobappInterviewerName(), jAppli.getJobappPhone(),
                jAppli.getJobappEmail(), jAppli.getJobappGender(), jAppli.getJobappRefferedByName(), jAppli.getJobappAddressForCorrespondance1(), jAppli.getJobappCity(), jAppli.getJobappState(), explevel, jAppli.getJobappConsultancyWalkinName());
//        String toMail = hrmsService.getHREmailOnLocation(jAppli.getJobappInteviewVenue());
        String toMail = userRepo.getUserEmailByID(jAppli.getNextRound());
        emailcollect.setSUBJECT("Round-" + (jAppli.getFeedbackCount() + 1) + " Interview For Post -" + jAppli.getJobappConsultancyWalkinName() + " - " + jAppli.getJobappInterviewerName());
        emailcollect.setTO(toMail);
        try {
//            this.emailSender.sendSimpleMail("nxtRoundMail", locale, emailcollect, null);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }

    }

    public List getNewMetaAddsLead(String status) {
        return metaAddsLeadRepo.GetAddsLeadData(status);
    }

//    public List getletterIssueOld(String status) {
//        return oldEmpDataRepo.getletterIssueOld(status);
//    }

    public void updateApplicant(JobApplicants req) {
        JobApplicants existing = applicantRepo.findById(req.getJobappId())
                .orElseThrow(() -> new RuntimeException("Not found"));

        // update only required fields
        existing.setJobappInterviewerName(req.getJobappInterviewerName());
        existing.setJobappEmail(req.getJobappEmail());
        existing.setJobappConsultancyWalkinName(req.getJobappConsultancyWalkinName());
        existing.setDepartment(req.getDepartment());
        existing.setJobappTimeRequiredForJoiningIfSelect(req.getJobappTimeRequiredForJoiningIfSelect());
        existing.setWorkLocation(req.getWorkLocation());

        JobApplicants resObj = applicantRepo.save(existing);

        //pass to Document center for loi
//        Loi loi = new Loi();
//        loi.setApplicantId(resObj.getJobappId());
//        loi.setEmployeeName(resObj.getJobappInterviewerName());
//        loi.setDesignation(resObj.getJobappConsultancyWalkinName());
//        loi.setDateOfJoining(resObj.getJobappTimeRequiredForJoiningIfSelect());
//        loi.setGender(resObj.getJobappGender());
//        loi.setEmailId(resObj.getJobappEmail());
//        loi.setWorkLocation(resObj.getJobappInteviewVenue());
//        loi.setDepartment(resObj.getDepartment());
//        loi.setOfferedSalaray(resObj.getOfferedSalary());
//        loi.setStatus("PENDING");
//        loiRepo.save(loi);

    }

}
