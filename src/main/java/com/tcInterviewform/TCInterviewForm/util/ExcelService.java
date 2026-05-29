/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcInterviewform.TCInterviewForm.util;

import com.tcInterviewform.TCInterviewForm.dto.UploadProgress;
import com.tcInterviewform.TCInterviewForm.model.HrmsEmployeeMaster;
import com.tcInterviewform.TCInterviewForm.model.HrmsEmployeeSalary;
//import com.tcInterviewform.TCInterviewForm.model.LetterIssue.IncrementPromotion;
//import com.tcInterviewform.TCInterviewForm.model.LetterIssue.OldEmployeeDetails;
import com.tcInterviewform.TCInterviewForm.repository.employeeMaster.HrmsEmployeeMasterRepository;
import com.tcInterviewform.TCInterviewForm.repository.employeeMaster.HrmsEmployeeSalaryRepository;
//import com.tcInterviewform.TCInterviewForm.repository.LetterIssue.IncrementPromotionRepository;
//import com.tcInterviewform.TCInterviewForm.repository.LetterIssue.OldEmployeeDetailsRepository;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import static org.hibernate.criterion.Projections.count;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ExcelService {

//    @Autowired
//    private OldEmployeeDetailsRepository repository;
    @PersistenceContext
    private EntityManager entityManager;
//    @Autowired
//    private IncrementPromotionRepository incrementPromotionRepository;
    @Autowired
    private HrmsEmployeeMasterRepository employeeRepo;

    @Autowired
    private HrmsEmployeeSalaryRepository salaryRepo;

//    @Transactional(rollbackFor = Exception.class)
//    public String saveExcelData(MultipartFile file) {
////        System.out.println("save excel");
//        try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {
//
//            Sheet sheet = workbook.getSheetAt(0);
//
//            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
//
//                Row row = sheet.getRow(i);
//                if (row == null) {
//                    continue;
//                }
//
//                OldEmployeeDetails emp = new OldEmployeeDetails();
//
//                // ✅ Basic fields
//                emp.setEmployeeId(getCellValue(row.getCell(1), i + 1, "Employee ID"));
//                emp.setEmployeeName(getCellValue(row.getCell(2), i + 1, "Employee Name"));
//                emp.setGender(getCellValue(row.getCell(3), i + 1, "Gender"));
//
//                // 🔥 Date validation (will STOP if invalid)
//                emp.setDateOfJoining(getDateCellValue(row.getCell(5), i + 1));
//                emp.setDateOfBirth(getDateCellValue(row.getCell(4), i + 1));
//
//                // ✅ Email validation
//                String email = getCellValue(row.getCell(10), i + 1, "Email");
//
//                emp.setEmailId(email);
//                emp.setLocation(getCellValue(row.getCell(6), i + 1, "Location"));
//                emp.setLevels(getCellValue(row.getCell(7), i + 1, "Level"));
//                emp.setDesignation(getCellValue(row.getCell(8), i + 1, "Designation"));
//                emp.setEducation(getCellValue(row.getCell(9), i + 1, "Education"));
//                emp.setEmployeeAddress(getCellValue(row.getCell(11), i + 1, "Address"));
//                emp.setContactNumber(getCellValue(row.getCell(14), i + 1, "Contact"));
//                emp.setOfferLetterStatus(getCellValue(row.getCell(15), i + 1, "Offer Status"));
//
//                emp.setCity(getCellValue(row.getCell(17), i + 1, "City"));
//                emp.setState(getCellValue(row.getCell(18), i + 1, "State"));
//                emp.setPincode(getCellValue(row.getCell(19), i + 1, "Pincode"));
//
//                emp.setCtc(parseDouble(row.getCell(20)));
//                emp.setBasic(parseDouble(row.getCell(21)));
//                emp.setHra(parseDouble(row.getCell(22)));
//                emp.setConveyance(parseDouble(row.getCell(23)));
//                emp.setMedical(parseDouble(row.getCell(24)));
//                emp.setSpecialAllowance(parseDouble(row.getCell(25)));
//                emp.setGrossSalary(parseDouble(row.getCell(26)));
//                emp.setEmployeePf(parseDouble(row.getCell(27)));
//                emp.setEmployerPf(parseDouble(row.getCell(28)));
//                emp.setProfessionalTax(parseDouble(row.getCell(29)));
//                emp.setInsuranceDeduction(parseDouble(row.getCell(30)));
//                emp.setTotalDeduction(parseDouble(row.getCell(31)));
//                emp.setNetSalary(parseDouble(row.getCell(32)));
//                repository.save(emp);
//
//                // 🔥 IMPORTANT → force DB validation immediately
//                entityManager.flush();
//            }
//
//            return "success";
//
//        } catch (Exception e) {
//            String rootMessage = getRootErrorMessage(e);
//            // 🔥 THROW TO CONTROLLER
//            throw new RuntimeException("Upload failed: " + rootMessage);
//        }
//    }
    public String getCellValue(Cell cell, int rowNum, String columnName) {

        DataFormatter formatter = new DataFormatter();

        if (cell == null) {
            return "";
        }

        try {
            return formatter.formatCellValue(cell).trim();

        } catch (Exception e) {
            throw new RuntimeException(
                    "Row " + rowNum + " → Invalid value in " + columnName
            );
        }
    }

    public Double parseDouble(Cell cell) {

        if (cell == null) {
            return 0.0;
        }

        try {
            return Double.parseDouble(cell.toString());

        } catch (Exception e) {
            return 0.0; // numeric optional
        }
    }

    private Long parseLong(Cell cell) {
        try {
            return (cell == null) ? 0L : Long.parseLong(cell.toString());
        } catch (Exception e) {
            return 0L;
        }
    }

    public LocalDate getDateCellValue(Cell cell, int rowNum) {

        if (cell == null) {
            return null;
        }

        try {
            // ✅ Case 1: Excel date (numeric)
            if (cell.getCellType() == CellType.NUMERIC) {
                return cell.getDateCellValue()
                        .toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();
            }

            // ✅ Case 2: String date
            String value = cell.toString().trim();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            return LocalDate.parse(value, formatter);

        } catch (Exception e) {
            throw new RuntimeException(
                    "Row " + rowNum + " Invalid date: " + cell.toString()
            );
        }
    }

    private String getRootErrorMessage(Exception e) {

        Throwable cause = e;

        while (cause.getCause() != null) {
            cause = cause.getCause();
        }

        return cause.getMessage();
    }

    @Transactional(rollbackFor = Exception.class)
    public int uploadDocumetWiseData(MultipartFile file, String documentType) throws Exception {

        int count = 0;
        DataFormatter formatter = new DataFormatter();

        try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {

            Sheet sheet = workbook.getSheetAt(0);

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {

                Row row = sheet.getRow(i);
//                System.out.println("row"+row);
                if (row == null) {
                    continue;
                }

                String employeeId = getString(row, 1, formatter);

                if (employeeId == null || employeeId.trim().isEmpty()) {
                    continue;
                }

                HrmsEmployeeMaster emp = employeeRepo.findByEmployeeId(employeeId)
                        .orElse(new HrmsEmployeeMaster());

                emp.setEmployeeName(getString(row, 0, formatter));
                emp.setEmployeeId(employeeId);
                emp.setGender(getString(row, 2, formatter));

                emp.setDateOfJoining(getDate(row, 3, formatter));
                emp.setDateOfBirth(getDate(row, 4, formatter));

                emp.setLocation(getString(row, 5, formatter));
                emp.setDateOfLeaving(getDate(row, 6, formatter));

                emp.setCurrentStatus(getString(row, 7, formatter));
                emp.setGrade(getString(row, 8, formatter));

                emp.setContactNumber(getString(row, 9, formatter));
                emp.setAlternateNumber(getString(row, 10, formatter));

                emp.setDesignation(getString(row, 11, formatter));
                emp.setDepartment(getString(row, 12, formatter));
                emp.setEducation(getString(row, 13, formatter));
                emp.setEmailId(getString(row, 14, formatter));

                emp.setAadhar(getString(row, 15, formatter));
                emp.setPan(getString(row, 16, formatter));

                emp.setEmployeeAddress(getString(row, 17, formatter));

                emp.setState(getString(row, 18, formatter));
                emp.setCity(getString(row, 19, formatter));
                emp.setPincode(getString(row, 20, formatter));

                emp.setPfStatus(getString(row, 21, formatter));
                emp.setUan(getString(row, 22, formatter));

                emp.setFinalSalary(getBigDecimal(row, 23, formatter));

                emp.setProcessName(getString(row, 37, formatter));
                emp.setOld_designation(getString(row, 39, formatter));
                emp.setOld_level(getString(row, 40, formatter));
                emp.setOld_salary(getBigDecimal(row, 41, formatter));

                emp = employeeRepo.save(emp);

                salaryRepo.updateOldSalaryAsInactive(emp.getId());

                HrmsEmployeeSalary salary = new HrmsEmployeeSalary();

                salary.setEmployeeMaster(emp);

                salary.setCtc(getBigDecimal(row, 23, formatter));

                salary.setBasic(getBigDecimal(row, 24, formatter));
                salary.setHra(getBigDecimal(row, 25, formatter));
                salary.setConveyance(getBigDecimal(row, 26, formatter));
                salary.setMedical(getBigDecimal(row, 27, formatter));
                salary.setSpecialAllowance(getBigDecimal(row, 28, formatter));

                salary.setGrossSalary(getBigDecimal(row, 29, formatter));

                salary.setEmployerPf(getBigDecimal(row, 30, formatter));
                salary.setEmployeePf(getBigDecimal(row, 31, formatter));

                salary.setProfessionalTax(getBigDecimal(row, 32, formatter));
                salary.setInsuranceDeduction(getBigDecimal(row, 33, formatter));
                salary.setEsicDeduction(getBigDecimal(row, 34, formatter));

                salary.setTotalDeduction(getBigDecimal(row, 35, formatter));
                salary.setNetSalary(getBigDecimal(row, 36, formatter));

                salary.setEffectiveFrom(getDate(row, 38, formatter));
                salary.setIsCurrent(1);

                salaryRepo.save(salary);

                count++;
            }
        }

        return count;
    }

    private String getString(Row row, int index, DataFormatter formatter) {
        Cell cell = row.getCell(index);

        if (cell == null) {
            return null;
        }

        String value = formatter.formatCellValue(cell).trim();

        if (value.isEmpty() || value.equals("-")) {
            return null;
        }

        return value;
    }

    private BigDecimal getBigDecimal(Row row, int index, DataFormatter formatter) {

        Cell cell = row.getCell(index);

        if (cell == null) {
            return BigDecimal.ZERO;
        }

        String value = formatter.formatCellValue(cell).trim();

        if (value.isEmpty() || value.equals("-")) {
            return BigDecimal.ZERO;
        }

        // remove commas
        value = value.replace(",", "");

        return new BigDecimal(value);
    }

    private LocalDate getDate(Row row, int index, DataFormatter formatter) {

        Cell cell = row.getCell(index);

        if (cell == null) {
            return null;
        }

        try {

            // REAL EXCEL DATE CELL
            if (cell.getCellType() == CellType.NUMERIC
                    && DateUtil.isCellDateFormatted(cell)) {

                return cell.getLocalDateTimeCellValue()
                        .toLocalDate();
            }

            // STRING DATE
            String value = formatter.formatCellValue(cell).trim();

            if (value.isEmpty() || value.equals("-")) {
                return null;
            }

            List<DateTimeFormatter> formats = Arrays.asList(
                    DateTimeFormatter.ofPattern("yyyy-MM-dd"),
                    DateTimeFormatter.ofPattern("dd-MM-yyyy"),
                    DateTimeFormatter.ofPattern("dd/MM/yyyy"),
                    DateTimeFormatter.ofPattern("dd-MMM-yyyy", Locale.ENGLISH),
                    DateTimeFormatter.ofPattern("d-MMM-yyyy", Locale.ENGLISH)
            );

            for (DateTimeFormatter f : formats) {
                try {
                    return LocalDate.parse(value, f);
                } catch (Exception ignored) {
                }
            }

            throw new RuntimeException("Unsupported date format: " + value);

        } catch (Exception e) {

            throw new RuntimeException(
                    "Invalid date at row "
                    + (row.getRowNum() + 1)
                    + " column "
                    + (index + 1)
                    + ". Date value should be like 2022-05-10"
            );
        }
    }

    private final Map<String, UploadProgress> progressMap = new ConcurrentHashMap<>();

    public UploadProgress getProgress(String jobId) {
        return progressMap.getOrDefault(
                jobId,
                new UploadProgress("UNKNOWN", 0, "Upload not found", 0, 0)
        );
    }

    @Async
    public void processUploadAsync(Path filePath, String documentType, String jobId) {

        progressMap.put(jobId,
                new UploadProgress("PROCESSING", 1, "Upload started", 0, 0));

        try {
            int count = uploadDocumetWiseData(filePath, documentType, jobId);

            progressMap.put(jobId,
                    new UploadProgress("COMPLETED", 100,
                            "Upload completed. Total uploaded: " + count, count, count));

        } catch (Exception e) {
            e.printStackTrace();

            progressMap.put(jobId,
                    new UploadProgress("FAILED", 0,
                            "Upload failed: " + e.getMessage(), 0, 0));
        } finally {
            try {
                Files.deleteIfExists(filePath);
            } catch (Exception ignored) {
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
public int uploadDocumetWiseData(Path filePath,
                                 String documentType,
                                 String jobId) throws Exception {

    int count = 0;

    DataFormatter formatter = new DataFormatter();

    try (Workbook workbook = WorkbookFactory.create(filePath.toFile())) {

        Sheet sheet = workbook.getSheetAt(0);

        // =========================
        // STEP 1 : Collect Employee IDs
        // =========================

        List<String> excelEmployeeIds = new ArrayList<>();

        Map<String, Row> rowMap = new LinkedHashMap<>();

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {

            Row row = sheet.getRow(i);

            if (row == null) {
                continue;
            }

            String employeeId = getString(row, 1, formatter);

            if (employeeId == null || employeeId.trim().isEmpty()) {
                continue;
            }

            employeeId = employeeId.trim();

            excelEmployeeIds.add(employeeId);

            rowMap.put(employeeId, row);
        }

        if (excelEmployeeIds.isEmpty()) {
            return 0;
        }

        // =========================
        // STEP 2 : Fetch Existing Employees
        // =========================

        List<HrmsEmployeeMaster> existingEmployees =
                employeeRepo.findByEmployeeIdIn(excelEmployeeIds);

        Map<String, HrmsEmployeeMaster> existingMap =
                existingEmployees.stream()
                        .collect(Collectors.toMap(
                                HrmsEmployeeMaster::getEmployeeId,
                                e -> e,
                                (a, b) -> a
                        ));

    
        List<Long> existingEmpIds =
                existingEmployees.stream()
                        .map(HrmsEmployeeMaster::getId)
                        .collect(Collectors.toList());

        if (!existingEmpIds.isEmpty()) {

            employeeRepo.backupEmployeeMasterBulk(
                    existingEmpIds,
                    "BULK_UPLOAD_" + documentType
            );
        }

        // =========================
        // STEP 4 : Save Employees
        // =========================

        List<HrmsEmployeeMaster> employeesToSave = new ArrayList<>();

        for (String employeeId : excelEmployeeIds) {

            Row row = rowMap.get(employeeId);

            HrmsEmployeeMaster emp =
                    existingMap.getOrDefault(
                            employeeId,
                            new HrmsEmployeeMaster()
                    );

            emp.setEmployeeName(getString(row, 0, formatter));
            emp.setEmployeeId(employeeId);
            emp.setGender(getString(row, 2, formatter));

            emp.setDateOfJoining(getDate(row, 3, formatter));
            emp.setDateOfBirth(getDate(row, 4, formatter));

            emp.setLocation(getString(row, 5, formatter));
            emp.setDateOfLeaving(getDate(row, 6, formatter));

            emp.setCurrentStatus(getString(row, 7, formatter));
            emp.setGrade(getString(row, 8, formatter));

            emp.setContactNumber(getString(row, 9, formatter));
            emp.setAlternateNumber(getString(row, 10, formatter));

            emp.setDesignation(getString(row, 11, formatter));
            emp.setDepartment(getString(row, 12, formatter));
            emp.setEducation(getString(row, 13, formatter));
            emp.setEmailId(getString(row, 14, formatter));

            emp.setAadhar(getString(row, 15, formatter));
            emp.setPan(getString(row, 16, formatter));

            emp.setEmployeeAddress(getString(row, 17, formatter));

            emp.setState(getString(row, 18, formatter));
            emp.setCity(getString(row, 19, formatter));
            emp.setPincode(getString(row, 20, formatter));

            emp.setPfStatus(getString(row, 21, formatter));
            emp.setUan(getString(row, 22, formatter));

            emp.setFinalSalary(getBigDecimal(row, 23, formatter));

            emp.setProcessName(getString(row, 37, formatter));
            emp.setOld_designation(getString(row, 39, formatter));
            emp.setOld_level(getString(row, 40, formatter));
            emp.setOld_salary(getBigDecimal(row, 41, formatter));

            employeesToSave.add(emp);
        }

        // BULK SAVE
        List<HrmsEmployeeMaster> savedEmployees =
                employeeRepo.saveAll(employeesToSave);

        employeeRepo.flush();

        // =========================
        // STEP 5 : Inactive Old Salaries
        // =========================

        List<Long> empIds =
                savedEmployees.stream()
                        .map(HrmsEmployeeMaster::getId)
                        .collect(Collectors.toList());

        if (!empIds.isEmpty()) {
            salaryRepo.inactiveOldSalaryBulk(empIds);
        }

        // =========================
        // STEP 6 : Save Salary
        // =========================

        List<HrmsEmployeeSalary> salaryList = new ArrayList<>();

        int totalRows = savedEmployees.size();
        int processed = 0;

        for (HrmsEmployeeMaster emp : savedEmployees) {

            Row row = rowMap.get(emp.getEmployeeId());

            HrmsEmployeeSalary salary = new HrmsEmployeeSalary();

            salary.setEmployeeMaster(emp);

            salary.setCtc(getBigDecimal(row, 23, formatter));

            salary.setBasic(getBigDecimal(row, 24, formatter));
            salary.setHra(getBigDecimal(row, 25, formatter));
            salary.setConveyance(getBigDecimal(row, 26, formatter));
            salary.setMedical(getBigDecimal(row, 27, formatter));
            salary.setSpecialAllowance(getBigDecimal(row, 28, formatter));

            salary.setGrossSalary(getBigDecimal(row, 29, formatter));

            salary.setEmployerPf(getBigDecimal(row, 30, formatter));
            salary.setEmployeePf(getBigDecimal(row, 31, formatter));

            salary.setProfessionalTax(getBigDecimal(row, 32, formatter));
            salary.setInsuranceDeduction(getBigDecimal(row, 33, formatter));
            salary.setEsicDeduction(getBigDecimal(row, 34, formatter));

            salary.setTotalDeduction(getBigDecimal(row, 35, formatter));
            salary.setNetSalary(getBigDecimal(row, 36, formatter));

            salary.setEffectiveFrom(getDate(row, 38, formatter));

            salary.setIsCurrent(1);

            salaryList.add(salary);

            processed++;

            int percent =
                    (int) ((processed * 100.0) / totalRows);

            progressMap.put(jobId,
                    new UploadProgress(
                            "PROCESSING",
                            percent,
                            "Processing row "
                                    + processed
                                    + " of "
                                    + totalRows,
                            totalRows,
                            processed
                    ));
        }

        // BULK SAVE SALARY
        salaryRepo.saveAll(salaryList);

        count = salaryList.size();
    }

    return count;
}
}
