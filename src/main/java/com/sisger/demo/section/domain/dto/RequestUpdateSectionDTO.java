package com.sisger.demo.section.domain.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestUpdateSectionDTO {
    @NotNull
    private String id;
    @NotNull
    private String name;
    @NotNull
    private String passwordAuthorization;
}
