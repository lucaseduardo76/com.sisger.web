package com.sisger.demo.section.domain.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestUpdateSectionDTO {
    @NotEmpty(message = "Id cannot be Empty")
    private String id;
    @NotEmpty(message = "Name cannot be Empty")
    private String name;
    @NotEmpty(message = "Password cannot be Empty")
    private String passwordAuthorization;
}
