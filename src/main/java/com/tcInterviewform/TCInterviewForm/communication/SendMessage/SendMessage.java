/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcInterviewform.TCInterviewForm.communication.SendMessage;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

/**
 *
 * @author SIF
 */
@Service
@PropertySource("classpath:mail/sms.properties")
public class SendMessage {
   @Value(value = "${smsSigma.text.url}")
    private String textURL;
    
    @Value(value = "${smsSigma.text.username}")
    private String textUsername;
    
    @Value(value = "${smsSigma.text.password}")
    private String textPassword;
    
    @Value(value = "${smsSigma.text.senderId}")
    private String textSenderId;
    
    @Value(value = "${smsSigma.text.routeId}")
    private String textRouteId;
    
    @Value(value = "${smsSigma.text.reqId}")
    private String textReqId;
    
    @Value(value = "${smsSigma.text.format}")
    private String textFormat;
       @Value(value = "${smsSigma.text.status}")
    private String textStatus;
    
    
     public String HttpApplier(String urlToHit)
    {
		System.out.println("==================Inside Http Applier===========");		
		try
		{
                    URL url = new URL(urlToHit);
                    System.out.println("==================url===========" + url);
                    HttpURLConnection http = (HttpURLConnection) url.openConnection();
                    http.setRequestMethod("GET");
                    int responseCode = http.getResponseCode();
                    System.out.println("Response Code - "+responseCode);
                    System.out.println("Response Message" + http.getResponseMessage());
                    http.disconnect();
            		}
		catch(IOException e)
		{
			System.out.println("Exception Caught..!!!"+e);
			return "-2";
		}
    return "-1";
    }
    
    
    
    public void sendText(String phone,String message) throws UnsupportedEncodingException {
    //    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        System.out.println("Inside Send Text Message => Phone "+phone+" Message "+message);
    String requestURL= textURL +
            "username=" + URLEncoder.encode(textUsername, "UTF-8") +
            "&password=" + URLEncoder.encode(textPassword, "UTF-8") +
            "&sender=" + URLEncoder.encode(textSenderId, "UTF-8") +
            "&to=" + URLEncoder.encode(phone, "UTF-8") +
            "&message=" + URLEncoder.encode(message, "UTF-8") +
            "&reqid=" + URLEncoder.encode(textReqId, "UTF-8") +
            "&format=" + URLEncoder.encode(textFormat, "UTF-8") +
            "&route_id=" + URLEncoder.encode(textRouteId, "UTF-8")
            ;
        
        System.out.println("requestURL "+requestURL);
        String status = null;
         System.out.println("SMS Feature Is Status  "+textStatus);
        if (textStatus.equalsIgnoreCase("true")) {
            status = HttpApplier(requestURL);
            System.out.println("After Sending SMS  "+status);
        } else {
            System.out.println("SMS Feature Is Disabled  "+status);
        }
    }
}
