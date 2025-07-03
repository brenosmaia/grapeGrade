package com.brenosmaia.grapegrade.utils;

import com.brenosmaia.grapegrade.entity.User;

import java.util.Random;
import java.util.UUID;

import static java.lang.String.format;

public class TestHelper {
    public static User buildUser() {
        String uuid = UUID.randomUUID().toString();
        return User.builder()
                .name("name-"+uuid)
                .username("username-"+uuid)
                .email(format("someone-%s@gmail.com", uuid))
                .password("password123")
                .build();
    }

    public static User buildUserWithId() {
        Random random = new Random();
        String uuid = UUID.randomUUID().toString();
        return User.builder()
                .id(String.valueOf(random.nextLong()))
                .name("name-"+uuid)
                .username("username-"+uuid)
                .email(format("someone-%s@gmail.com", uuid))
                .password("password123")
                .build();
    }
}
