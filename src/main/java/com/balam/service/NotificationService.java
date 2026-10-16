package com.balam.service;

import org.springframework.stereotype.Service;
import java.net.URL;
import java.net.URLEncoder;
import java.net.HttpURLConnection;

@Service
public class NotificationService {

    // ADMIN NUMBERS
    private static final String ADMIN1 = "919666667667";
    private static final String ADMIN2 = "917671975794";
    
    // CALLMEBOT API KEYS - IKKADA MEE API KEYS PETTALI - WhatsApp nundi vachina keys
    private static final String API_KEY_1 = "PUT_API_KEY_FOR_9666667667"; // 9666667667 ki vachina key
    private static final String API_KEY_2 = "PUT_API_KEY_FOR_7671975794"; // 7671975794 ki vachina key

    public void sendInstantNotification(String customerPhone, String from, String to, String source) {
        try {
            String message = "🔥 NEW LEAD - BALAM PACKERS\n" +
                    "Customer: " + customerPhone + "\n" +
                    "From: " + (from != null ? from : "Website") + "\n" +
                    "To: " + (to != null ? to : "Not Given") + "\n" +
                    "Source: " + source + "\n" +
                    "Time: " + java.time.LocalDateTime.now().toString() + "\n\n" +
                    "CALL NOW: wa.me/91" + customerPhone;

            System.out.println("📩 Sending WhatsApp to 9666667667 & 7671975794 - Customer: " + customerPhone);
            
            // Send WhatsApp to BOTH admins
            sendWhatsApp(ADMIN1, API_KEY_1, message);
            Thread.sleep(1500); // 1.5 sec gap - Spam avoid
            sendWhatsApp(ADMIN2, API_KEY_2, message);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendWhatsApp(String phone, String apiKey, String message) {
        try {
            if (apiKey.contains("PUT_API_KEY")) {
                System.out.println("⚠️ API Key not set for " + phone + " - Please set CallMeBot API Key!");
                System.out.println("Message: " + message);
                return;
            }
            
            String encodedMsg = URLEncoder.encode(message, "UTF-8");
            String urlString = "https://api.callmebot.com/whatsapp.php?phone=" + phone + "&text=" + encodedMsg + "&apikey=" + apiKey;
            
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(10000);
            
            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                System.out.println("✅ WhatsApp Sent to " + phone + " - Code: " + responseCode);
            } else {
                System.out.println("❌ Failed to send to " + phone + " - Code: " + responseCode);
            }
            
            conn.disconnect();
            
        } catch (Exception e) {
            System.out.println("Error sending WhatsApp to " + phone + ": " + e.getMessage());
            e.printStackTrace();
        }
    }
}