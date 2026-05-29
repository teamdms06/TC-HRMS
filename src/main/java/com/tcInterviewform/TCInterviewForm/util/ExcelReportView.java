/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcInterviewform.TCInterviewForm.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsxStreamingView;

@Component
public class ExcelReportView extends AbstractXlsxStreamingView {
    @Override
    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setHeader("Content-Disposition", "attachment;filename=\"ApplicantList.xlsx\"");
        @SuppressWarnings("unchecked")
        List<ReportEntity> list = (List<ReportEntity>) model.get("exportReport");
        System.out.println("ExcelListReportView Called -----");
        Sheet sheet = workbook.createSheet("REPORTS");
        sheet.setDefaultColumnWidth(30);
        Font font = workbook.createFont();
        CellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor(IndexedColors.SKY_BLUE.index);
        style.setFont(font);
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("ID");
        header.getCell(0).setCellStyle(style);
        header.createCell(1).setCellValue("Applied_ON");
        header.getCell(1).setCellStyle(style);
        header.createCell(2).setCellValue("APPLICANT_NAME");
        header.getCell(2).setCellStyle(style);
        header.createCell(3).setCellValue("PHONE_NO");
        header.getCell(3).setCellStyle(style);
        header.createCell(4).setCellValue("DATE_OF_BIRTH");
        header.getCell(4).setCellStyle(style);
        header.createCell(5).setCellValue("TYPE");
        header.getCell(5).setCellStyle(style);
        header.createCell(6).setCellValue("CITY");
        header.getCell(6).setCellStyle(style);
        header.createCell(7).setCellValue("ADDRESS");
        header.getCell(7).setCellStyle(style);
        header.createCell(8).setCellValue("REFFERED_BY");
        header.getCell(8).setCellStyle(style);
        header.createCell(9).setCellValue("JOIN_DATE_IF_SELECTED");
        header.getCell(9).setCellStyle(style);
        header.createCell(10).setCellValue("QUALIFICATION");
        header.getCell(10).setCellStyle(style);
        header.createCell(11).setCellValue("COLLEGE_NAME");
        header.getCell(11).setCellStyle(style);
        header.createCell(12).setCellValue("EXAMINATION");
        header.getCell(12).setCellStyle(style);
        header.createCell(13).setCellValue("PASSING_YEAR");
        header.getCell(13).setCellStyle(style);
        header.createCell(14).setCellValue("RESUME_PATH");
        header.getCell(14).setCellStyle(style);
        header.createCell(15).setCellValue("APPLICATION_STATUS");
        header.getCell(15).setCellStyle(style);
        header.createCell(16).setCellValue("FeedBack 1 Date");
        header.getCell(16).setCellStyle(style);
        header.createCell(17).setCellValue("FeedBack 1 By");
        header.getCell(17).setCellStyle(style);
        header.createCell(18).setCellValue("FeedBack 1 avg Count");
        header.getCell(18).setCellStyle(style);
        header.createCell(19).setCellValue("FeedBack 1 Final Status");
        header.getCell(19).setCellStyle(style);
        header.createCell(20).setCellValue("FeedBack 1 remark");
        header.getCell(20).setCellStyle(style);
        header.createCell(21).setCellValue("FeedBack 1 Offered Sal");
        header.getCell(21).setCellStyle(style);
        header.createCell(22).setCellValue("FeedBack 1 InterViewer Name");
        header.getCell(22).setCellStyle(style);
        header.createCell(23).setCellValue("FeedBack 2 Date");
        header.getCell(23).setCellStyle(style);
        header.createCell(24).setCellValue("FeedBack 2 By");
        header.getCell(24).setCellStyle(style);
        header.createCell(25).setCellValue("FeedBack 2 avg Count");
        header.getCell(25).setCellStyle(style);
        header.createCell(26).setCellValue("FeedBack 2 Final Status");
        header.getCell(26).setCellStyle(style);
        header.createCell(27).setCellValue("FeedBack 2 remark");
        header.getCell(27).setCellStyle(style);
        header.createCell(28).setCellValue("FeedBack 2 Offered Sal");
        header.getCell(28).setCellStyle(style);
        header.createCell(29).setCellValue("FeedBack 2 InterViewer Name");
        header.getCell(29).setCellStyle(style);
        header.createCell(30).setCellValue("FeedBack 3 Date");
        header.getCell(30).setCellStyle(style);
        header.createCell(31).setCellValue("FeedBack 3 By");
        header.getCell(31).setCellStyle(style);
        header.createCell(32).setCellValue("FeedBack 3 avg Count");
        header.getCell(32).setCellStyle(style);
        header.createCell(33).setCellValue("FeedBack 3 Final Status");
        header.getCell(33).setCellStyle(style);
        header.createCell(34).setCellValue("FeedBack 3 remark");
        header.getCell(34).setCellStyle(style);
        header.createCell(35).setCellValue("FeedBack 3 Offered Sal");
        header.getCell(35).setCellStyle(style);
        header.createCell(36).setCellValue("FeedBack 3 InterViewer Name");
        header.getCell(36).setCellStyle(style);
        header.setHeight((short) 500);
        int rowNum = 1;
        for (ReportEntity entList : list) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(rowNum - 1);
            row.createCell(1).setCellValue(entList.getCreatedAt());
            row.createCell(2).setCellValue(entList.getJobapp_interviewer_name());
            row.createCell(3).setCellValue(entList.getJobapp_phone());
            row.createCell(4).setCellValue(entList.getJobapp_birthdate());
            row.createCell(5).setCellValue(entList.getExperience());
            row.createCell(6).setCellValue(entList.getJobapp_city());
            row.createCell(7).setCellValue(entList.getJobapp_address_for_correspondance1());
            row.createCell(8).setCellValue(entList.getJobapp_reffered_by_name());
            row.createCell(9).setCellValue(entList.getJobapp_time_required_for_joining_if_select());
            row.createCell(10).setCellValue(entList.getSkill_qualification());
            row.createCell(11).setCellValue(entList.getSkill_college_uni_institute_name());
            row.createCell(12).setCellValue(entList.getSkill_examination());
            row.createCell(13).setCellValue(entList.getSkill_year_of_passing());
            row.createCell(14).setCellValue(entList.getJobapp_resume_file_path());
            row.createCell(15).setCellValue(entList.getStatus());
            row.createCell(16).setCellValue(fixedNUll(entList.getFeedbackcreatedAt()));
            row.createCell(17).setCellValue(fixedNUll(entList.getFeedbackby()));
            row.createCell(18).setCellValue(fixedNUll(entList.getFeedbackAvgCount()));
            row.createCell(19).setCellValue(fixedNUll(entList.getFeedbackFinalStatus()));
            row.createCell(20).setCellValue(fixedNUll(entList.getFeedbackremark()));
            row.createCell(21).setCellValue(fixedNUll(entList.getFeedbacksalOffered()));
            row.createCell(22).setCellValue(fixedNUll(entList.getFeedbackTakerName()));
            row.createCell(23).setCellValue(fixedNUll(entList.getFeedbackcreatedAt1()));
            row.createCell(24).setCellValue(fixedNUll(entList.getFeedbackby1()));
            row.createCell(25).setCellValue(fixedNUll(entList.getFeedbackAvgCount1()));
            row.createCell(26).setCellValue(fixedNUll(entList.getFeedbackFinalStatus1()));
            row.createCell(27).setCellValue(fixedNUll(entList.getFeedbackremark1()));
            row.createCell(28).setCellValue(fixedNUll(entList.getFeedbacksalOffered1()));
            row.createCell(29).setCellValue(fixedNUll(entList.getFeedbackTakerName1()));
            row.createCell(30).setCellValue(fixedNUll(entList.getFeedbackcreatedAt2()));
            row.createCell(31).setCellValue(fixedNUll(entList.getFeedbackby2()));
            row.createCell(32).setCellValue(fixedNUll(entList.getFeedbackAvgCount2()));
            row.createCell(33).setCellValue(fixedNUll(entList.getFeedbackFinalStatus2()));
            row.createCell(34).setCellValue(fixedNUll(entList.getFeedbackremark2()));
            row.createCell(35).setCellValue(fixedNUll(entList.getFeedbacksalOffered2()));
            row.createCell(36).setCellValue(fixedNUll(entList.getFeedbackTakerName2()));
        }
    }

    private String TAT(String cdate, String uDate) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
        Date creDate = format.parse(cdate);
        Date upDate = format.parse(uDate);
        long difference_In_Time
                = upDate.getTime() - creDate.getTime();
        long difference_In_Days
                = (difference_In_Time
                / (1000 * 60 * 60 * 24))
                % 365;
        long difference_In_Seconds
                = TimeUnit.MILLISECONDS
                        .toSeconds(difference_In_Time)
                % 60;
        long difference_In_Minutes
                = TimeUnit.MILLISECONDS
                        .toMinutes(difference_In_Time)
                % 60;
        long difference_In_Hours
                = TimeUnit.MILLISECONDS
                        .toHours(difference_In_Time)
                % 24;
        String TotMin = String.valueOf(((difference_In_Days * 24 * 60) + (difference_In_Hours * 60) + difference_In_Minutes));
        return TotMin;
    }

    public String fixedNUll(String toString) {
        String foo = null;
        try {
            if (toString.equals(foo)) {
                toString = "NA";
            }
        } catch (NullPointerException e) {
            toString = "NA";
        }
        return toString;
    }
}
