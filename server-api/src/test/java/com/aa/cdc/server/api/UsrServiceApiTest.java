package com.aa.cdc.server.api;

import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.PactProviderRuleMk2;
import au.com.dius.pact.consumer.PactVerification;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.RequestResponsePact;
import com.aa.cdc.server.dto.User;
import com.google.common.io.Resources;
import kotlin.text.Charsets;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class UsrServiceApiTest {

    private final UsrServiceApi api = new UsrServiceApi();


    @Rule
    public PactProviderRuleMk2 mockProvider
            = new PactProviderRuleMk2("test_provider", "localhost", 3001, this);

    @Pact(consumer = "test_consumer")
    public RequestResponsePact createPact_GetAllUsers(PactDslWithProvider builder) throws IOException {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        return builder
                .given("test get all user")
                .uponReceiving("Get all users")
                .path("/users")
                .method("GET")
                .willRespondWith()
                .status(200)
                .headers(headers)
                .body(readFile("all-users.json"))
                .toPact();
    }

    @Pact(consumer = "test_consumer")
    public RequestResponsePact createPact_FindUser(PactDslWithProvider builder) throws IOException {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        return builder
                .given("test find users")
                .uponReceiving("Find user by query")
                .path("/find")
                .matchQuery("query", "0")
                .method("GET")
                .willRespondWith()
                .status(200)
                .headers(headers)
                .body(readFile("single-user.json"))
                .given("test find users")
                .uponReceiving("Find user by query")
                .path("/find")
                .matchQuery("query", "[0-9]")
                .method("GET")
                .willRespondWith()
                .status(200)
                .headers(headers)
                .body("")
                .toPact();
    }


    @Test
    @PactVerification(fragment = "createPact_GetAllUsers")
    public void getAllUsers() {
        List<User> users = api.getAll();
        System.out.println(users);
    }


    @Test
    @PactVerification(fragment = "createPact_FindUser")
    public void findUser() {
        User user = api.find("0");
        System.out.println(user);
        assertThat(user).isEqualToComparingFieldByField(new User(0, "Name-0"));
        User user2 = api.find("2");
        assertThat(user2).isNull();
        User user3 = api.find("3");
        assertThat(user3).isNull();
    }

    public String readFile(String resourceName) throws IOException {
        URL url = Resources.getResource(resourceName);
        return Resources.toString(url, Charsets.UTF_8);
    }
}