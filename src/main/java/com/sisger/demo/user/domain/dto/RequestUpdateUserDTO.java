package com.sisger.demo.user.domain.dto;

import com.sisger.demo.user.domain.Role;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RequestUpdateUserDTO {
    @NotEmpty(message = "Id cannot be null")
    private String id;
    @NotEmpty(message = "Password cannot be null")
    private String name;
    @NotEmpty(message = "Email cannot be null")
    private String email;
    @NotEmpty(message = "Password cannot be null")
    private String passwordAuthorization;
    @NotEmpty(message = "Cpf cannot be null")
    @Size(min = 11, message = "CPF must have at least 11 characters")
    private String cpf;
    private Role role;
}
