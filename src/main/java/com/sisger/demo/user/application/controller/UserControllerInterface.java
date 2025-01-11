package com.sisger.demo.user.application.controller;


import com.sisger.demo.user.domain.User;
import com.sisger.demo.user.domain.dto.ChangePasswordDTO;
import com.sisger.demo.user.domain.dto.RequestUserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user")
public interface UserControllerInterface {

    @PutMapping(value = "change-password")
    public void changePassword(@RequestHeader(name = "Authorization", required = true) String token,
                               @RequestBody ChangePasswordDTO changePasswordDTO);


    @GetMapping(value = "find-all")
    public ResponseEntity<List<User>> findAll(@RequestHeader(name = "Authorization", required = true) String token);

    @PostMapping(value = "new-user")
    public ResponseEntity<User> create(
            @RequestHeader(name = "Authorization", required = true) String token,
            @RequestBody RequestUserDTO requestUserDTO);
}
