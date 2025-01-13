package com.sisger.demo.section.domain.dto;

import jakarta.validation.constraints.NotNull;

public class RequestUpdateSectionDTO {
    @NotNull
    private String id;
    @NotNull
    private String name;
    @NotNull
    private String passwordAuthorization;
}
