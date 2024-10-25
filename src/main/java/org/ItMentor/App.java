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
        String code2 = updateUser();
        String code3 = deleteUser();
        String result = code1 + code2 + code3;
        System.out.println(result);
        System.out.println(result.length());
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
    private static String updateUser() {
        User user = new User();
        user.setId(3L);
        user.setName("Thomas");
        user.setLastName("Shelby");
        user.setAge((byte) 25);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(HttpHeaders.COOKIE, sessionId);

        HttpEntity<User> request = new HttpEntity<>(user, headers);
        ResponseEntity<String> response = restTemplate.exchange(URL, HttpMethod.PUT, request, String.class);

        return response.getBody();
    }

    private static String deleteUser() {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.COOKIE, sessionId);

        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(URL + "/3", HttpMethod.DELETE, request, String.class);

        return response.getBody();
    }
}
