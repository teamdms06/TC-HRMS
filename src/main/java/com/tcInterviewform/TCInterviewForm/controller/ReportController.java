/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcInterviewform.TCInterviewForm.controller;

import com.tcInterviewform.TCInterviewForm.model.HrFeedback;
import com.tcInterviewform.TCInterviewForm.repository.HRFeedbackRepository;
import com.tcInterviewform.TCInterviewForm.repository.HRmsRepository;
import com.tcInterviewform.TCInterviewForm.repository.MetaAddsLeadRepository;
import com.tcInterviewform.TCInterviewForm.util.ExcelMetaAddReportView;
import com.tcInterviewform.TCInterviewForm.util.ExcelReportView;
import com.tcInterviewform.TCInterviewForm.util.ReportEntity;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author SIF
 */
@Controller
public class ReportController {

    @Autowired
    HRmsRepository hrmsRepository;
    @Autowired
    HRFeedbackRepository hrfeedRepository;
    @Autowired
    MetaAddsLeadRepository metaAddRepository;

    @GetMapping("report")
    public ModelAndView report(HttpServletRequest request, HttpServletResponse response, ModelMap model) {

        HttpSession session = request.getSession();

        if (session.isNew()) {
            return new ModelAndView("Login", model);
        } else {
            String role = (String) session.getAttribute("actor_process");
//            String role = "HR";
            if (!role.equalsIgnoreCase("HR")) {
            }
            return new ModelAndView("segments/report", model);

        }

    }

    @RequestMapping(value = "getExcelReport", method = RequestMethod.GET)
    public ModelAndView getReport(@RequestParam(name = "filter") String filter, @RequestParam(name = "startDate") String toDate, @RequestParam(name = "endDate") String fromDate, @RequestParam(required = false) String status) {
        // System.out.println(" date " + toDate + " --EndDate" + fromDate);
        List<ReportEntity> entityList = new ArrayList();
        List<Object[]> exportList = new ArrayList();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
        if ("addsLead".equals(filter)) {
            System.out.println("Status: " + status);
            // filter by status + date
            if (status.equalsIgnoreCase("All")) {
                status = "";
            }
            exportList = metaAddRepository.getMetaAddsExport(toDate, fromDate, status);
            for (Object[] meta : exportList) {
                ReportEntity re = new ReportEntity();
                re.setCreatedAt(sdf.format(meta[1]));
                re.setName((String) meta[2]);
                re.setEmail((String) meta[3]);
                re.setPhone((String) meta[4]);
                re.setLocation((String) meta[5]);
                re.setAddsetName((String) meta[6]);
                re.setCampaignName((String) meta[7]);
                re.setFormName((String) meta[8]);
                re.setStatus((String) meta[9]);
                BigInteger count = (BigInteger) meta[15];
                re.setAttempts(count.intValue());
                re.setCallStatus((String) meta[10]);
                re.setActionBy((String) meta[11]);
                re.setLanguage((String) meta[12]);
                re.setSalaryExpection((String) meta[13]);
                re.setFeedback((String) meta[14]);
                entityList.add(re);

            }
            return new ModelAndView(new ExcelMetaAddReportView(), "metaAddsReport", entityList);
        } else {
            exportList = hrmsRepository.getExport(toDate, fromDate);
            //   System.out.println(" Size " + exportList.size());
            int hours = (int) 5.5;
            Calendar calendar = Calendar.getInstance();

            for (Object[] info : exportList) {
                ReportEntity re = new ReportEntity();
//             System.out.println("id  "+ info[0]);
                re.setId((Integer) info[0]);
                re.setJobapp_interviewer_name((String) info[1]);
                re.setJobapp_phone((String) info[2]);
                re.setJobapp_birthdate(sdf.format(info[3]));
                calendar.setTime((Date) info[4]);
                calendar.add(Calendar.HOUR_OF_DAY, hours);
                re.setCreatedAt(sdf.format(calendar.getTime()));
                re.setExperience((String) info[5]);
                re.setJobapp_address_for_correspondance1((String) info[6]);
                re.setJobapp_city((String) info[7]);
                re.setJobapp_reffered_by_name((String) info[8]);
                re.setJobapp_time_required_for_joining_if_select(sdf.format(info[9]));
                re.setSkill_qualification((String) info[10]);
                re.setSkill_college_uni_institute_name((String) info[11]);
                re.setSkill_examination((String) info[12]);
                re.setSkill_year_of_passing((String) info[13]);
                re.setJobapp_resume_file_path((String) info[14]);
                re.setStatus((String) info[15]);

                if (info[0] != null) {
                    List<HrFeedback> hrFeed = hrfeedRepository.getHrFeedbackbyJobAppId((Integer) info[0]);
                    // System.out.println("==" + hrFeed);
                    SimpleDateFormat sDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
                    for (HrFeedback hf : hrFeed) {
                        switch (hf.getFeedbackAttempt() != null ? hf.getFeedbackAttempt() : 0) {
                            case 1:
                                calendar.setTime(hf.getCreatedAt());
                                calendar.add(Calendar.HOUR_OF_DAY, hours);
                                re.setFeedbackcreatedAt(sDate.format(calendar.getTime()));
                                re.setFeedbackby(hf.getFeedbackBy());
                                re.setFeedbackAvgCount(Integer.toString(hf.getCount()));
                                re.setFeedbackFinalStatus(hf.getFinalStatus());
                                re.setFeedbackremark(hf.getRemark());
                                re.setFeedbacksalOffered(hf.getOfferedSalary());
                                re.setFeedbackTakerName(hf.getInterviewerName());
                                break;
                            case 2:
                                calendar.setTime(hf.getCreatedAt());
                                calendar.add(Calendar.HOUR_OF_DAY, hours);
                                re.setFeedbackcreatedAt1(sDate.format(calendar.getTime()));
                                re.setFeedbackby1(hf.getFeedbackBy());
                                re.setFeedbackAvgCount1(Integer.toString(hf.getCount()));
                                re.setFeedbackFinalStatus1(hf.getFinalStatus());
                                re.setFeedbackremark1(hf.getRemark());
                                re.setFeedbacksalOffered1(hf.getOfferedSalary());
                                re.setFeedbackTakerName1(hf.getInterviewerName());
                                break;
                            case 3:
                                calendar.setTime(hf.getCreatedAt());
                                calendar.add(Calendar.HOUR_OF_DAY, hours);
                                re.setFeedbackcreatedAt2(sDate.format(calendar.getTime()));
                                re.setFeedbackby2(hf.getFeedbackBy());
                                re.setFeedbackAvgCount2(Integer.toString(hf.getCount()));
                                re.setFeedbackFinalStatus2(hf.getFinalStatus());
                                re.setFeedbackremark2(hf.getRemark());
                                re.setFeedbacksalOffered2(hf.getOfferedSalary());
                                re.setFeedbackTakerName2(hf.getInterviewerName());
                                break;
                        }
                    }
                }
                entityList.add(re);
            }

            return new ModelAndView(new ExcelReportView(), "exportReport", entityList);
        }
    }

}
