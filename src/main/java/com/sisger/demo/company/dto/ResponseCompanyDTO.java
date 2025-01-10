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
public class ResponseCompanyDTO {

    private String id;
    private String razaoSocial;
    private Integer cnpj;
    private Integer cpf;

}
