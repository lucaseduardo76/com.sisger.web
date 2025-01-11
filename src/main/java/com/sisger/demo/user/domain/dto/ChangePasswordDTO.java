package com.sisger.demo.user.domain.dto;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordDTO {

    @NotNull
    private String oldPassword;
    @NotNull
    private String newPassword;

}
