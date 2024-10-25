package org.ItMentor;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class App {

    private static final String URL = "http://94.198.50.185:7081/api/users";
    private static String sessionId;
    private static final RestTemplate restTemplate = new RestTemplate();

    public static void main(String[] args) {
        getSessionId();
        String code1 = addUser();

        System.out.println(code1);
    }


    public static void getSessionId() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        ResponseEntity<String> response = restTemplate.exchange(URL, HttpMethod.GET, new HttpEntity<>(headers), String.class);
        System.out.println(response);
        sessionId = response.getHeaders().getFirst(HttpHeaders.SET_COOKIE);
        System.out.println(sessionId);
        headers.set("Cookie", sessionId);
    }

    private static String addUser() {
        User user = new User();
        user.setId(3L);
        user.setName("James");
        user.setLastName("Brown");
        user.setAge((byte) 25);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(HttpHeaders.COOKIE, sessionId);

        HttpEntity<User> request = new HttpEntity<>(user, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(URL, request, String.class);
        return response.getBody();
    }
}
