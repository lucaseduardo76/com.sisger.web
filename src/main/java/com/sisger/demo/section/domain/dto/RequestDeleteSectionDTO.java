package com.sisger.demo.section.domain.dto;

import jakarta.validation.constraints.NotNull;

public class RequestDeleteSectionDTO {

    @NotNull
    private String id;
    @NotNull
    private String passwordAuthorization;
    @NotNull
    private String userToDeleteId;
}
