package com.aa.cdc.server.api;

import com.aa.cdc.server.dto.User;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UsrServiceApi {

    public List<User> getAll() {
        RestTemplate restTemplate = new RestTemplateBuilder().build();
        return restTemplate.exchange("http://localhost:3001/users", HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<User>>() {
                }).getBody();
    }

    public User find(String queryStr) {
        RestTemplate restTemplate = new RestTemplateBuilder().build();
        Map<String, String> params = new HashMap<>();
        params.put("query", queryStr);
        return restTemplate.getForObject("http://localhost:3001/find?query={query}", User.class, params);
    }
}
