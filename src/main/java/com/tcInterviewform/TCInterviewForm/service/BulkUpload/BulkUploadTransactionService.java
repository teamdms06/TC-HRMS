/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcInterviewform.TCInterviewForm.service.BulkUpload;

import com.tcInterviewform.TCInterviewForm.dto.UploadProgress;
import com.tcInterviewform.TCInterviewForm.model.HrmsEmployeeMaster;
import com.tcInterviewform.TCInterviewForm.model.HrmsEmployeeSalary;
import com.tcInterviewform.TCInterviewForm.repository.employeeMaster.HrmsEmployeeMasterRepository;
import com.tcInterviewform.TCInterviewForm.repository.employeeMaster.HrmsEmployeeSalaryRepository;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author teamd
 */
@Service
public class BulkUploadTransactionService {

    @Autowired
    private HrmsEmployeeMasterRepository employeeRepo;

    @Autowired
    private HrmsEmployeeSalaryRepository salaryRepo;

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional(rollbackFor = Exception.class)
    public int uploadDocumetWiseData(Path filePath,
                                     String documentType,
                                     String jobId,
                                     Map<String, UploadProgress> progressMap) throws Exception {

        DataFormatter formatter = new DataFormatter();

        try (Workbook workbook = WorkbookFactory.create(filePath.toFile())) {

            Sheet sheet = workbook.getSheetAt(0);

            Set<String> excelEmployeeIds = new LinkedHashSet<>();
            Map<String, Row> rowMap = new LinkedHashMap<>();
            List<String> duplicateIds = new ArrayList<>();

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {

                Row row = sheet.getRow(i);
                if (row == null) continue;

                String employeeId = getString(row, 1, formatter);

                if (employeeId == null || employeeId.trim().isEmpty()) {
                    continue;
                }

                employeeId = employeeId.replaceAll("\\s+", "")
                        .trim()
                        .toUpperCase();

                if (excelEmployeeIds.contains(employeeId)) {
                    duplicateIds.add(employeeId);
                    continue;
                }

                excelEmployeeIds.add(employeeId);
                rowMap.put(employeeId, row);
            }

            if (!duplicateIds.isEmpty()) {
                throw new RuntimeException("Duplicate Employee IDs in Excel: " + duplicateIds);
            }

            if (excelEmployeeIds.isEmpty()) {
                return 0;
            }

            List<String> empIdList = new ArrayList<>(excelEmployeeIds);

            List<HrmsEmployeeMaster> existingEmployees =
                    employeeRepo.findByEmployeeIdIn(empIdList);

            Map<String, HrmsEmployeeMaster> existingMap =
                    existingEmployees.stream()
                            .collect(Collectors.toMap(
                                    e -> e.getEmployeeId().trim().toUpperCase(),
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

            List<HrmsEmployeeMaster> employeesToSave = new ArrayList<>();

            for (String employeeId : empIdList) {

                Row row = rowMap.get(employeeId);

                HrmsEmployeeMaster emp =
                        existingMap.getOrDefault(employeeId, new HrmsEmployeeMaster());

                emp.setEmployeeName(getString(row, 0, formatter));
                emp.setEmployeeId(employeeId);
//                System.out.println("employeeId"+employeeId);
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

            List<HrmsEmployeeMaster> savedEmployees = employeeRepo.saveAll(employeesToSave);
            employeeRepo.flush();

            List<Long> savedEmpIds =
                    savedEmployees.stream()
                            .map(HrmsEmployeeMaster::getId)
                            .collect(Collectors.toList());

            if (!savedEmpIds.isEmpty()) {
                salaryRepo.inactiveOldSalaryBulk(savedEmpIds);
            }

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

                int percent = (int) ((processed * 100.0) / totalRows);

                progressMap.put(jobId,
                        new UploadProgress(
                                "PROCESSING",
                                percent,
                                "Processing row " + processed + " of " + totalRows,
                                totalRows,
                                processed
                        ));
            }

            salaryRepo.saveAll(salaryList);
            salaryRepo.flush();

            return salaryList.size();
        }
    }
    private String getString(Row row, int cellIndex, DataFormatter formatter) {
    if (row == null) return "";

    Cell cell = row.getCell(cellIndex);
    if (cell == null) return "";

    return formatter.formatCellValue(cell).trim();
}

private LocalDate getDate(Row row, int cellIndex, DataFormatter formatter) {
    if (row == null) return null;

    Cell cell = row.getCell(cellIndex);
    if (cell == null) return null;

    try {
        if (cell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(cell)) {
            return cell.getLocalDateTimeCellValue().toLocalDate();
        }
//        System.out.println("formatter.formatCellValue(cell)"+formatter.formatCellValue(cell));
        String value = formatter.formatCellValue(cell).trim();
        if (value.isEmpty()) return null;

        return LocalDate.parse(value);
    } catch (Exception e) {
        throw new RuntimeException("Invalid date at column " + cellIndex + ": " 
                + formatter.formatCellValue(cell));
    }
}

private BigDecimal getBigDecimal(Row row, int cellIndex, DataFormatter formatter) {
    String value = getString(row, cellIndex, formatter);

    if (value == null || value.trim().isEmpty()) {
        return BigDecimal.ZERO;
    }

    value = value.replace(",", "").trim();

    return new BigDecimal(value);
}
}