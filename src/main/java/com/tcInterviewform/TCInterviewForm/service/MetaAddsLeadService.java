/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcInterviewform.TCInterviewForm.service;

import com.tcInterviewform.TCInterviewForm.communication.EmailCollection;
import com.tcInterviewform.TCInterviewForm.communication.service.EmailSender;
import com.tcInterviewform.TCInterviewForm.model.MetaAddsFollowup;
import com.tcInterviewform.TCInterviewForm.model.MetaAddsLead;
import com.tcInterviewform.TCInterviewForm.repository.MetaAddsLeadFollowupRepository;
import com.tcInterviewform.TCInterviewForm.repository.MetaAddsLeadRepository;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;
//import static javax.persistence.EnumType.STRING;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author teamd
 */
@Service
public class MetaAddsLeadService {

    @Autowired
    MetaAddsLeadRepository metaAddsRepo;
    @Autowired
    MetaAddsLeadFollowupRepository metaAddsFollowupRepo;

    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    EmailSender emailSender;

    @Transactional
    public void syncData() throws Exception {
        Map<String, List<String>> sheetMap = new HashMap<>();

        
        sheetMap.put(
                "https://docs.google.com/spreadsheets/d/1vXUtGbHHyra9WFrMho_Vet1CK-Eu8Dx4SgN6BTUfNDo/export?format=xlsx",
                Arrays.asList("Business Process Associate", "Education counsellor ","Team leader Pune","BPA Mumbai")
        );

        sheetMap.put(
                "https://docs.google.com/spreadsheets/d/108LmWt5uqf-xP3agAhsxwFk6mQ8FDPQ_2LwhxZWIQtc/export?format=xlsx",
                Arrays.asList("Sheet1")
        );

        for (Map.Entry<String, List<String>> entry : sheetMap.entrySet()) {

            String url = entry.getKey();
            List<String> sheetNames = entry.getValue();

            Workbook workbook = WorkbookFactory.create(new URL(url).openStream());

            for (String sheetName : sheetNames) {

                Sheet sheet = workbook.getSheet(sheetName);

                if (sheet == null) {
                    System.out.println("❌ Sheet not found: " + sheetName);
                    continue;
                }

                processSheet(sheet);
            }

            workbook.close();
        }

    }

    // ✅ PROCESS SHEET
    private void processSheet(Sheet sheet) {

        Iterator<Row> rows = sheet.iterator();

        Row headerRow = rows.next();

        Map<String, Integer> headerMap = new HashMap<>();

        for (Cell cell : headerRow) {
            String header = getString(cell);
            if (header != null) {
                headerMap.put(header.trim().toLowerCase(), cell.getColumnIndex());
            }
        }

        // ✅ COLUMN MAP
        Map<String, Integer> col = new HashMap<>();
        col.put("email", headerMap.get("email"));
        col.put("name", headerMap.get("full_name"));
    
        col.put("phone", ((headerMap.containsKey("phone")) ? headerMap.get("phone") : headerMap.get("phone_number")));
        col.put("location", headerMap.get("city"));
        col.put("adsetName", headerMap.get("adset_name"));
        col.put("campaignName", headerMap.get("campaign_name"));
        col.put("formName", headerMap.get("form_name"));
        col.put("language", headerMap.get("are_you_proficient_in_english_?"));
        col.put("experience", headerMap.get("how_many_year_of_experience_you_have_?"));

        int batchSize = 500;

        List<MetaAddsLead> excelBatch = new ArrayList<>();

        while (rows.hasNext()) {

            Row row = rows.next();

            MetaAddsLead c = mapRow(row, col);
//                System.out.println("c.getEmail()"+c.getEmail());
            if (c == null) {
                
                continue;
            }

            excelBatch.add(c);

            if (excelBatch.size() >= batchSize) {
                processBatch(excelBatch);
            }
        }

        if (!excelBatch.isEmpty()) {
            processBatch(excelBatch);
        }

        System.out.println("Sync completed!");
    }

    @Transactional
    private void processBatch(List<MetaAddsLead> excelBatch) {

        List<String> phoneNo = excelBatch.stream()
                .map(MetaAddsLead::getPhoneNumber)
                .collect(Collectors.toList());
//        List<String> emails = excelBatch.stream()
//                .map(MetaAddsLead::getEmail)
//                .collect(Collectors.toList());

        // ✅ SINGLE DB CALL
        List<String> existingPhoneNo = metaAddsRepo.findExistingPhoneNo(phoneNo);

        Set<String> existingSet = new HashSet<>(existingPhoneNo);

        List<MetaAddsLead> toSave = new ArrayList<>();
         Set<String> batchUniqueSet = new HashSet<>();
        
        for (MetaAddsLead lead : excelBatch) {
             String phone = lead.getPhoneNumber();
            if (!existingSet.contains(lead.getPhoneNumber())  && batchUniqueSet.add(phone)) {
                toSave.add(lead);
            }
        }

        if (!toSave.isEmpty()) {
            metaAddsRepo.saveAll(toSave);
            entityManager.flush();
            entityManager.clear();
        }

        excelBatch.clear();
    }

    // ✅ UPDATE METHOD
//    private void saveBatch(Map<String, MetaAddsLead> batchMap) {
//
//        metaAddsRepo.saveAll(batchMap.values());
//        batchMap.clear();
//
//        entityManager.flush();   // ✅ important
//        entityManager.clear();   // ✅ fixes duplicate ID issue
//    }
    // ✅ MAP ROW
    private MetaAddsLead mapRow(Row row, Map<String, Integer> col) {

        String email = getCell(row, col.get("email"));
        String phone = getCell(row, col.get("phone"));
//        System.out.println("email-"+email+"-phone-"+phone);
       
        if (phone == null || phone.isEmpty()) {
//             System.out.println("in null");
            return null;
        }

        MetaAddsLead lead = new MetaAddsLead();

        lead.setEmail(email);
        lead.setName(getCell(row, col.get("name")));
        lead.setPhoneNumber(normalizePhone(phone));
        lead.setLocation(getCell(row, col.get("location")));
        lead.setStatus("NEW");

        // Meta Ads
        lead.setAdsetName(getCell(row, col.get("adsetName")));
        lead.setCampaignName(getCell(row, col.get("campaignName")));
        lead.setFormName(getCell(row, col.get("formName")));

        // Extra
        lead.setLanguage(getCell(row, col.get("language")));
        lead.setExperience(getCell(row, col.get("experience")));

        return lead;
    }

    // ✅ SAFE CELL READ
    private String getCell(Row row, Integer index) {
        if (index == null) {
            return null;
        }
        return getString(row.getCell(index));
    }

    // ✅ CELL → STRING
    private String getString(Cell cell) {

        if (cell == null) {
            return null;
        }

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim();
            case NUMERIC:
                return String.valueOf((long) cell.getNumericCellValue());
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            default:
                return null;
        }
    }

    // ✅ PHONE NORMALIZE
    private String normalizePhone(String raw) {

        if (raw == null || raw.trim().isEmpty()) {
            return null;
        }

        String phone = raw.replaceAll("(?i)p:\\+?", "");
        phone = phone.replaceAll("[^0-9]", "");

        if (phone.length() == 10) {
            phone = "91" + phone;
        }

        return phone;
    }

    public List getmetaAddsLEadsDetails(Integer leadId) {
        return metaAddsRepo.getMetaAddsLeadDetails(leadId);
    }

    public List<MetaAddsFollowup> getAddsLeadFeedbacks(Integer leadId) {
        return metaAddsFollowupRepo.findByLeadId(leadId);
    }

    public MetaAddsFollowup saveAddsFollowup(MetaAddsFollowup hrFeedback) {
        return metaAddsFollowupRepo.save(hrFeedback);
    }

    public int updateCallStatus(Integer leadId, String status) {
        return metaAddsRepo.updateCallStatus(leadId, status);
    }

    public String getApplicantMailId(Integer leadId) {
        return metaAddsRepo.getApplicantMailIDByLeadId(leadId);
    }

    public Boolean mailSender(String applicantMailId, Locale locale) {
        String toMail = applicantMailId;
        EmailCollection emailcollect = new EmailCollection();
        emailcollect.setSUBJECT("Complete Your Interview Form – The-Connections ITES");
        emailcollect.setTO(toMail);
        try {
            this.emailSender.sendMail("formLinkMail", locale, emailcollect);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public Integer findNextLeadId(Integer leadId) {
       return metaAddsRepo.findNextLeadId(leadId);
    }

   

}
