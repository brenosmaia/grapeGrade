package com.brenosmaia.grapegrade.web.controller;

import static org.springframework.http.HttpStatus.CREATED;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.brenosmaia.grapegrade.entity.User;
import com.brenosmaia.grapegrade.service.UserService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class UserController {

	private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "/users")
    public ResponseEntity<List<User>> getUsers() {
        log.info("process=get-users");
        List<User> users = userService.getAllUsers();
        
        return users.isEmpty() ? ResponseEntity.notFound().build() 
        		: ResponseEntity.ok(users);
    }

    @GetMapping(path = "/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable String id) {
        log.info("process=get-user, user_id={}", id);
        User user = userService.getUserById(id);
        
        return user == null ? ResponseEntity.notFound().build() 
        		: ResponseEntity.ok(user);
    }

    @ResponseStatus(CREATED)
    @PostMapping(path = "/users/register")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        log.info("process=create-user, user_email={}", user.getEmail());
        user.setCreatedAt(LocalDateTime.now());
        return ResponseEntity.ok(userService.createUser(user));
    }

    @PutMapping(path = "/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable String id, @RequestBody User user) {
        log.info("process=update-user, user_id={}", id);
        user.setId(id);
        user.setUpdatedAt(LocalDateTime.now());
        return ResponseEntity.ok(userService.updateUser(user));
    }

    @DeleteMapping(path = "/users/{id}")
    public void deleteUser(@PathVariable String id) {
        log.info("process=delete-user, user_id={}", id);
        userService.deleteUser(id);
    }

}
