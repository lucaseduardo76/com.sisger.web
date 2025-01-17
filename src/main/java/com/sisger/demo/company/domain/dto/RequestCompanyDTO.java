package com.sisger.demo.company.domain.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestCompanyDTO {

    @NotEmpty(message = "Name of Company cannot be empty")
    private String name;
    @NotEmpty(message = "CNPJ cannot be empty")
    @Size(min = 14, message = "CNPJ must have at least 14 characters")
    private String cnpj;


}
