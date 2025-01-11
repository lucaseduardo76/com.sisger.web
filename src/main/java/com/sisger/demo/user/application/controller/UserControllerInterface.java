package com.sisger.demo.user.application.controller;


import com.sisger.demo.user.domain.dto.ChangePasswordDTO;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public interface UserControllerInterface {

    @PutMapping(value = "change-password")
    public void changePassword(@RequestHeader(name = "Authorization", required = true) String token,
                               @RequestBody ChangePasswordDTO changePasswordDTO);

}
