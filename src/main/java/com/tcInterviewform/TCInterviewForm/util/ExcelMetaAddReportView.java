/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcInterviewform.TCInterviewForm.util;

import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsxStreamingView;

/**
 *
 * @author teamd
 */
public class ExcelMetaAddReportView extends AbstractXlsxStreamingView {

    @Override
    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setHeader("Content-Disposition", "attachment;filename=\"AddsLeadData"+new Date()+".xlsx\"");
        @SuppressWarnings("unchecked")
        List<ReportEntity> list = (List<ReportEntity>) model.get("metaAddsReport");
        System.out.println("ExcelListReportView Called -----");
        Sheet sheet = workbook.createSheet("Adds Lead");
        sheet.setDefaultColumnWidth(30);
        Font font = workbook.createFont();
        CellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor(IndexedColors.SKY_BLUE.index);
        style.setFont(font);
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Sr. No.");
        header.getCell(0).setCellStyle(style);
        header.createCell(1).setCellValue("FollowUp On");
        header.getCell(1).setCellStyle(style);
        header.createCell(2).setCellValue("Name");
        header.getCell(2).setCellStyle(style);
        header.createCell(3).setCellValue("Eamil");
        header.getCell(3).setCellStyle(style);
        header.createCell(4).setCellValue("Phone");
        header.getCell(4).setCellStyle(style);
        header.createCell(5).setCellValue("Loation");
        header.getCell(5).setCellStyle(style);
        header.createCell(6).setCellValue("Adset_name");
        header.getCell(6).setCellStyle(style);
        header.createCell(7).setCellValue("Campaign Name");
        header.getCell(7).setCellStyle(style);
        header.createCell(8).setCellValue("Form Name");
        header.getCell(8).setCellStyle(style);
        header.createCell(9).setCellValue("Status");
        header.getCell(9).setCellStyle(style);
        header.createCell(10).setCellValue("Total Attempts");
        header.getCell(10).setCellStyle(style);
        header.createCell(11).setCellValue("Call Status");
        header.getCell(11).setCellStyle(style);
        header.createCell(12).setCellValue("Followup By");
        header.getCell(12).setCellStyle(style);
        header.createCell(13).setCellValue("Language");
        header.getCell(13).setCellStyle(style);
        header.createCell(14).setCellValue("Salaray_expection");
        header.getCell(14).setCellStyle(style);
        header.createCell(15).setCellValue("Feedback");
        header.getCell(15).setCellStyle(style);
        header.setHeight((short) 500);
        int rowNum = 1;
        for (ReportEntity entList : list) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(rowNum - 1);
            row.createCell(1).setCellValue(entList.getCreatedAt());
            row.createCell(2).setCellValue(entList.getName());
            row.createCell(3).setCellValue(entList.getEmail());
            row.createCell(4).setCellValue(entList.getPhone());
            row.createCell(5).setCellValue(entList.getLocation());
            row.createCell(6).setCellValue(entList.getAddsetName());
            row.createCell(7).setCellValue(entList.getCampaignName());
            row.createCell(8).setCellValue(entList.getFormName());
            row.createCell(9).setCellValue(entList.getStatus());
            row.createCell(10).setCellValue(entList.getAttempts());
            row.createCell(11).setCellValue(entList.getCallStatus());
            row.createCell(12).setCellValue(entList.getActionBy());
            row.createCell(13).setCellValue(entList.getLanguage());
            row.createCell(14).setCellValue(entList.getSalaryExpection());
            row.createCell(15).setCellValue(entList.getFeedback());
        }
    }

}
