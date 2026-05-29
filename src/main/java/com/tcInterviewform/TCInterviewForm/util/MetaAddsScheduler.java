/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcInterviewform.TCInterviewForm.util;

import com.tcInterviewform.TCInterviewForm.service.MetaAddsLeadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 *
 * @author teamd
 */
@Component
public class MetaAddsScheduler {

    @Autowired
    MetaAddsLeadService service;

//    @Scheduled(fixedDelay = 600000)
//    @Scheduled(fixedDelay = 30000)
    public void autoSync() {
        try {
            service.syncData(); // ✅ transaction works
        } catch (Exception e) {
            System.out.println("Auto Sync Failed: " + e.getMessage());
        }
    }
}
