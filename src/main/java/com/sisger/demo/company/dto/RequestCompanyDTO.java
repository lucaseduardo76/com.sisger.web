package com.sisger.demo.company.dto;

import jakarta.validation.constraints.NotNull;
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

    @NotNull
    private String razaoSocial;
    @Size(min = 14)
    private Integer cnpj;
    @Size(min = 14)
    private Integer cpf;


}
