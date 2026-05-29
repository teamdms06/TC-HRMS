/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcInterviewform.TCInterviewForm.communication.SendMessage;

import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 *
 * @author SIF
 */
@Component
public class SendSMS {

    @Value("${spring.sms.api.url}")
    private String APIURL;

    @Value("${spring.sms.api.key}")
    private String apiKey;

    @Value("${spring.sms.api.sender}")
    private String sender;

//    @Autowired
//    messageStatusCheck msc;
    public SendSMS() {

    }

    public void init() {

    }

    public String SendSMS(String message, String mobNo) throws UnsupportedEncodingException {

        try {
            // Construct data
            String apiKey1 = "apikey=" + URLEncoder.encode(apiKey, "UTF-8");
            String text = "&message=" + URLEncoder.encode(message, "UTF-8");
            String sender1 = "&sender=" + URLEncoder.encode(sender, "UTF-8");
            String numbers = "&numbers=" + URLEncoder.encode(mobNo, "UTF-8");

            // Send data
            String data = APIURL + apiKey1 + numbers + text + sender1;
            System.out.println("URL FIRE=" + data);
            URL url = new URL(data);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            int responseCode = conn.getResponseCode();
            System.out.println("Response Code - " + responseCode);
            System.out.println("Response Message" + conn.getResponseMessage());

            conn.disconnect();

            // Get the response
//			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//			String line;
//			String sResult="";
//			while ((line = rd.readLine()) != null) {
//			// Process line...
//				sResult=sResult+line+" ";
//			}
//			rd.close();
//                            
//                        JSONObject Jobj = new JSONObject(sResult);
//                        JSONArray Jsobj = (JSONArray) Jobj.get("messages");   
//                        String status = "";
//                        for(int i=0;i<Jsobj.length();i++){
//                            JSONObject objct = Jsobj.getJSONObject(i);
//                            String idIs = objct.getString("id");
//                            System.out.println("idIs="+idIs);
//                            String respo = msc.messageStatusCheck(idIs);
//                            System.out.println("responce="+respo);
//                            
//                            JSONObject jobj = new JSONObject(respo);
//
//                            JSONObject jarr = (JSONObject) jobj.get("message");
//                            System.out.println( "=JSONObject="+jarr.getString("status"));
//                            status = jarr.getString("status")+","+idIs;
//                            System.out.println("status="+status);
//    
//                        }
//                        
            return conn.getResponseMessage();
        } catch (Exception e) {
            System.out.println("Error SMS " + e);
            return "Error " + e;
        }

    }

}
