package com.sisger.demo.user.domain.dto;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordDTO {

    @NotEmpty( message = "Old password cannot be empty")
    private String oldPassword;
    @NotEmpty( message = "New password cannot be empty")
    private String newPassword;

}
