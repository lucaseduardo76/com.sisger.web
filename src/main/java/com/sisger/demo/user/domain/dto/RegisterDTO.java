package com.sisger.demo.user.domain.dto;

import com.sisger.demo.company.domain.dto.RequestCompanyDTO;
import com.sisger.demo.user.domain.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDTO {

    @NotNull(message = "Name cannot be empty")
    private String name;
    @Email
    @NotNull(message = "Email cannot be empty")
    private String email;
    @NotNull(message = "Password cannot be null")
    @Size(min = 3)
    private String password;
    private RequestCompanyDTO company;

}
