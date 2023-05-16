package org.example;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class Main {
    public static final String URL = "http://94.198.50.185:7081/api/users";

    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(URL, String.class);
        HttpHeaders headers = responseEntity.getHeaders();
        String sessionId = headers.getFirst(HttpHeaders.SET_COOKIE);

        User user = new User(3L, "James", "Brown", (byte) 20);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.COOKIE, sessionId);
        HttpEntity<User> requestEntity = new HttpEntity<>(user, httpHeaders);

        ResponseEntity<String> postResponse = restTemplate.postForEntity(URL, requestEntity, String.class);
        String responseText = postResponse.getBody();

        System.out.println(responseText);

        user.setName("Thomas");
        user.setLastName("Shelby");

        HttpEntity<User> putEntity = new HttpEntity<>(user, httpHeaders);
        ResponseEntity<String> putResponse = restTemplate.exchange(URL, HttpMethod.PUT, putEntity, String.class);
        String putResponseText = putResponse.getBody();
        System.out.println(putResponseText);


        HttpEntity<User> deleteEntity = new HttpEntity<>(httpHeaders);
        ResponseEntity<String> deleteResponse = restTemplate.exchange(URL + "/3", HttpMethod.DELETE, deleteEntity, String.class);
        String deleteResponseText = deleteResponse.getBody();
        System.out.println(deleteResponseText);


    }
}