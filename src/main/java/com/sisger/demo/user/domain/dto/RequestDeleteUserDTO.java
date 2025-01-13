package com.sisger.demo.user.domain.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestDeleteUserDTO {

    private String id;
    @NotNull(message = "Password cannot be null")
    @NotEmpty(message = "Nao pode ser vazio")
    private String passwordAuthorization;

}
