package com.sisger.demo.company.domain.dto;

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
    @Size(min = 11, max = 50)
    private Integer cnpj;
    private Integer cpf;


}
