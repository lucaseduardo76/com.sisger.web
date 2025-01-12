package com.sisger.demo.user.domain.dto;

import com.sisger.demo.user.domain.Role;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RequestUpdateUserDTO {
    @NotNull(message = "Id cannot be null")
    private String id;
    @NotNull(message = "Password cannot be null")
    private String name;
    @NotNull(message = "Email cannot be null")
    private String email;
    @NotNull(message = "Password cannot be null")
    private String passwordAuthorization;
    @NotNull(message = "Cpf cannot be null")
    private String cpf;
    @NotNull(message = "Role cannot be null")
    private Role role;
}
