package com.sisger.demo.user.controller;


import com.sisger.demo.user.domain.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public interface UserControllerInterface {

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user);

}
