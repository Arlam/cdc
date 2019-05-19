package com.aa.cdc.server;

import com.aa.cdc.server.dto.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController("users")
public class UserController {
    private final Map<Integer, User> users;

    public UserController() {

        users = IntStream.range(0, 10)
                .boxed()
                .collect(Collectors.toMap(i -> i, i -> new User(i, "Name-" + i)));
    }

    @RequestMapping
    public List<User> getAll() {
        return new ArrayList<>(users.values());
    }

    @RequestMapping
    public User find(@RequestParam String query) {
        return users.values().stream()
                .filter(value -> value.getName().contains(query))
                .findFirst().orElse(null);
    }
}
