package com.example.clothdonationsystem.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PayPalService {

    @Value("${paypal.client.id}")
    private String clientId;

    @Value("${paypal.client.secret}")
    private String clientSecret;

    @Value("${paypal.mode}")
    private String mode;

    private final RestTemplate restTemplate = new RestTemplate();

    // Method to get the base URL based on the mode (sandbox or live)
    private String getPayPalBaseUrl() {
        return "live".equalsIgnoreCase(mode) ?
                "https://api-m.paypal.com" :
                "https://api-m.sandbox.paypal.com";
    }

    // Method to generate access token
    public String getAccessToken() {
        String url = getPayPalBaseUrl() + "/v1/oauth2/token";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBasicAuth(clientId, clientSecret);

        HttpEntity<String> request = new HttpEntity<>("grant_type=client_credentials", headers);

        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, request, Map.class);
        Map<String, Object> responseBody = response.getBody();

        return (String) responseBody.get("access_token");
    }

    // Method to create a payment
    public Map<String, Object> createPayment(Double total, String currency, String description, String cancelUrl, String successUrl) {
        String url = getPayPalBaseUrl() + "/v1/payments/payment";

        String accessToken = getAccessToken();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(accessToken);

        Map<String, Object> paymentMap = new HashMap<>();
        paymentMap.put("intent", "sale");

        Map<String, Object> payerMap = new HashMap<>();
        payerMap.put("payment_method", "paypal");

        Map<String, Object> redirectUrlsMap = new HashMap<>();
        redirectUrlsMap.put("cancel_url", cancelUrl);
        redirectUrlsMap.put("return_url", successUrl);

        Map<String, Object> amountMap = new HashMap<>();
        amountMap.put("total", String.format("%.2f", total)); // Ensure the amount is formatted to two decimal places
        amountMap.put("currency", currency);

        Map<String, Object> transactionMap = new HashMap<>();
        transactionMap.put("amount", amountMap);
        transactionMap.put("description", description);

        paymentMap.put("payer", payerMap);
        paymentMap.put("transactions", Collections.singletonList(transactionMap));
        paymentMap.put("redirect_urls", redirectUrlsMap);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(paymentMap, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);

        // Extract the approval URL from the response
        Map<String, Object> responseBody = response.getBody();
        List<Map<String, String>> links = (List<Map<String, String>>) responseBody.get("links");
        String approvalUrl = links.stream()
                .filter(link -> "approval_url".equals(link.get("rel")))
                .map(link -> link.get("href"))
                .findFirst()
                .orElse(null);

        Map<String, Object> result = new HashMap<>();
        result.put("paymentId", responseBody.get("id"));
        result.put("approvalUrl", approvalUrl);
        return result;
    }

    // Method to execute a payment after payer approval
    public Map<String, Object> executePayment(String paymentId, String payerId) {
        String url = getPayPalBaseUrl() + "/v1/payments/payment/" + paymentId + "/execute";

        String accessToken = getAccessToken();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(accessToken);

        Map<String, Object> payerMap = new HashMap<>();
        payerMap.put("payer_id", payerId);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(payerMap, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);

        return response.getBody();
    }
}
