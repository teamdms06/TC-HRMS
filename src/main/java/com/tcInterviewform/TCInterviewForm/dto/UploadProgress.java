/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcInterviewform.TCInterviewForm.dto;

import lombok.Data;

/**
 *
 * @author teamd
 */
@Data
public class UploadProgress {
    
     private String status;
    private int percent;
    private String message;
    private int totalRows;
    private int processedRows;

    public UploadProgress() {}

    public UploadProgress(String status, int percent, String message, int totalRows, int processedRows) {
        this.status = status;
        this.percent = percent;
        this.message = message;
        this.totalRows = totalRows;
        this.processedRows = processedRows;
    }
}
