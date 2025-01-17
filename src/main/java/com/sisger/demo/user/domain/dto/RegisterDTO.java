package com.sisger.demo.user.domain.dto;

import com.sisger.demo.company.domain.dto.RequestCompanyDTO;
import com.sisger.demo.user.domain.Role;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDTO {

    @NotEmpty(message = "Name cannot be empty")
    private String name;
    @Email(message = "Email must be valid")
    @NotEmpty(message = "Email cannot be empty")
    private String email;
    @NotEmpty(message = "Password cannot be null")
    @Size(min = 8, message = "Password must have at least 8 characters")
    private String password;

    @Valid
    private RequestCompanyDTO company;

}
