package com.example.javaqualifier.service;

import com.example.javaqualifier.model.GenerateWebhookResponse;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class WebhookService {

    private final RestTemplate restTemplate = new RestTemplate();

    public void executeFlow() {

        String url = "https://bfhldevapigw.healthrx.co.in/hiring/generateWebhook/JAVA";

        Map<String, Object> body = new HashMap<>();
        body.put("name", "Aakanksha Gupta");
        body.put("regNo", "22bec1260");  // <-- your reg no
        body.put("email", "gupta.aakanksha2003@gmail.com");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        ResponseEntity<GenerateWebhookResponse> response =
                restTemplate.postForEntity(url, request, GenerateWebhookResponse.class);

        if (response.getBody() == null) {
            System.out.println("❌ Failed to generate webhook");
            return;
        }

        String webhookUrl = response.getBody().getWebhookUrl();
        String accessToken = response.getBody().getAccessToken();

        System.out.println("✅ Webhook: " + webhookUrl);
        System.out.println("✅ Token: " + accessToken);


        String finalQuery = "SELECT e1.EMP_ID, e1.FIRST_NAME, e1.LAST_NAME, d.DEPARTMENT_NAME, COUNT(e2.EMP_ID) AS YOUNGER_EMPLOYEES_COUNT FROM EMPLOYEE e1 JOIN DEPARTMENT d ON e1.DEPARTMENT = d.DEPARTMENT_ID LEFT JOIN EMPLOYEE e2 ON e1.DEPARTMENT = e2.DEPARTMENT AND e2.DOB > e1.DOB GROUP BY e1.EMP_ID, e1.FIRST_NAME, e1.LAST_NAME, d.DEPARTMENT_NAME ORDER BY e1.EMP_ID DESC;";


        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", accessToken);

        Map<String, String> ansBody = new HashMap<>();
        ansBody.put("finalQuery", finalQuery);

        HttpEntity<Map<String, String>> submitRequest = new HttpEntity<>(ansBody, headers);

        ResponseEntity<String> submitResponse =
                restTemplate.postForEntity(webhookUrl, submitRequest, String.class);

        System.out.println("📩 Submission Response: " + submitResponse.getBody());
    }
}
