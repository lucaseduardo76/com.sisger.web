package com.sisger.demo.user.domain.dto;

import com.sisger.demo.user.domain.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestUserDTO {
    @NotEmpty(message = "Name cannot be empty")
    private String name;
    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Email must be valid")
    private String email;
    @NotEmpty(message = "Password cannot be null")
    @Size(min = 8, message = "Password must have at least 8 characters")
    private String password;
    @NotEmpty(message = "Cpf cannot be null")
    @Size(min = 11, message = "CPF must have at least 11 characters")
    private String cpf;

    private Role role;

}
