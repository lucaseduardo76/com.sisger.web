package com.sisger.demo.section.domain.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestDeleteSectionDTO {

    @NotNull
    private String id;
    @NotEmpty(message = "Nao pode ser vazio")
    private String passwordAuthorization;
}
